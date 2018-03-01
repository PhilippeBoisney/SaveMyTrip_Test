package com.openclassrooms.savemytrip.trip_book;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.openclassrooms.savemytrip.R;
import com.openclassrooms.savemytrip.base.BaseActivity;
import com.openclassrooms.savemytrip.utils.StorageUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class TripBookActivity extends BaseActivity {

    //FOR DESIGN
    @BindView(R.id.trip_book_activity_external_choice) LinearLayout linearLayoutExternalChoice;
    @BindView(R.id.trip_book_activity_internal_choice) LinearLayout linearLayoutInternalChoice;
    @BindView(R.id.trip_book_activity_radio_external) RadioButton radioButtonExternalChoice;
    @BindView(R.id.trip_book_activity_radio_public) RadioButton radioButtonExternalPublicChoice;
    @BindView(R.id.trip_book_activity_radio_volatile) RadioButton radioButtonInternalVolatileChoice;
    @BindView(R.id.trip_book_activity_edit_text) EditText editText;

    //FILE PURPOSE
    private static final String FILENAME = "tripBook.txt";
    private static final String FOLDERNAME = "bookTrip";
    private static final String AUTHORITY="com.openclassrooms.savemytrip.fileprovider";

    // PERMISSION PURPOSE
    private static final int RC_STORAGE_WRITE_PERMS = 100;

    @Override
    public int getLayoutContentViewID() { return R.layout.activity_trip_book; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureToolbar();
        this.readFromStorage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                this.shareFile();
                return true;
            case R.id.action_save:
                this.saveOnStorage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // --------------------
    // ACTIONS
    // --------------------

    @OnCheckedChanged({R.id.trip_book_activity_radio_internal, R.id.trip_book_activity_radio_external,
                       R.id.trip_book_activity_radio_private, R.id.trip_book_activity_radio_public,
                       R.id.trip_book_activity_radio_normal, R.id.trip_book_activity_radio_volatile})
    public void onClickRadioButton(CompoundButton button, boolean isChecked){
        if (isChecked) {
            switch (button.getId()) {
                case R.id.trip_book_activity_radio_internal:
                    this.linearLayoutExternalChoice.setVisibility(View.GONE);
                    this.linearLayoutInternalChoice.setVisibility(View.VISIBLE);
                    break;
                case R.id.trip_book_activity_radio_external:
                    this.linearLayoutExternalChoice.setVisibility(View.VISIBLE);
                    this.linearLayoutInternalChoice.setVisibility(View.GONE);
                    break;
            }
        }
        this.readFromStorage();
    }

    // --------------------
    // UI
    // --------------------

    private void readFromStorage(){
        if (StorageUtils.isExternalStorageReadable()){
            if (this.radioButtonExternalChoice.isChecked()){
                // EXTERNAL
                if (radioButtonExternalPublicChoice.isChecked()){
                    // External - Public
                    this.editText.setText(StorageUtils.getTextFromExternalPublicStorage(this, FILENAME, FOLDERNAME));
                } else {
                    // External - Private
                    this.editText.setText(StorageUtils.getTextFromExternalPrivateStorage(this, FILENAME, FOLDERNAME));
                }
            } else {
                // INTERNAL
                if (radioButtonInternalVolatileChoice.isChecked()){
                    // Internal - Volatile
                    this.editText.setText(StorageUtils.getTextFromInternalVolatileStorage(this, FILENAME, FOLDERNAME));
                } else {
                    // Internal - Normal
                    this.editText.setText(StorageUtils.getTextFromInternalStorage(this, FILENAME, FOLDERNAME));
                }
            }
        }
    }

    // ----------------------------------
    // UTILS - WRITE ON STORAGE
    // ----------------------------------

    @AfterPermissionGranted(RC_STORAGE_WRITE_PERMS)
    private void saveOnStorage(){
        if (this.radioButtonExternalChoice.isChecked()){
            this.writeOnExternalStorage(); //Save on external storage
        } else {
            this.writeOnInternalStorage(); //Save on internal storage
        }
    }

    private void writeOnExternalStorage(){

        //CHECK PERMISSION
        if (!EasyPermissions.hasPermissions(this, WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.title_permission), RC_STORAGE_WRITE_PERMS, WRITE_EXTERNAL_STORAGE);
            return;
        }

        if (StorageUtils.isExternalStorageWritable()){
            if (radioButtonExternalPublicChoice.isChecked()){
                StorageUtils.writeOnExternalPublicStorage(this, FILENAME, this.editText.getText().toString(), FOLDERNAME);
            } else {
                StorageUtils.writeOnExternalPrivateStorage(this, FILENAME, this.editText.getText().toString(), FOLDERNAME);
            }
        } else {
            Toast.makeText(this, getString(R.string.external_storage_impossible_create_file), Toast.LENGTH_LONG).show();
        }
    }

    private void writeOnInternalStorage(){
        if (radioButtonInternalVolatileChoice.isChecked()){
            StorageUtils.writeOnVolatileInternalStorage(this, FILENAME, this.editText.getText().toString(), FOLDERNAME);
        } else {
            StorageUtils.writeOnInternalStorage(this, FILENAME, this.editText.getText().toString(), FOLDERNAME);
        }
    }

    // ----------------------------------
    // SHARE FILE
    // ----------------------------------

    private void shareFile(){
        File internalFile = StorageUtils.getFileFromInternalStorage(this, FILENAME, FOLDERNAME);
        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, internalFile);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.trip_book_share)));
    }
}