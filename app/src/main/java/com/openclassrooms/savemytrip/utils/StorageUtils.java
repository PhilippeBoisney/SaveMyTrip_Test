package com.openclassrooms.savemytrip.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.openclassrooms.savemytrip.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Philippe on 26/02/2018.
 */

public class StorageUtils {

    private static File createOrGetFile(File destination, String fileName, String folderName){
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }

    // ----------------------------------
    // INTERNAL STORAGE
    // ----------------------------------

    public static File getFileFromInternalStorage(Context context, String fileName, String folderName){
        return createOrGetFile(context.getFilesDir(), fileName, folderName);
    }

    public static String getTextFromInternalStorage(Context context, String fileName, String folderName){
        File file = createOrGetFile(context.getFilesDir(), fileName, folderName);
        return readOnFile(context, file);
    }

    public static String getTextFromInternalVolatileStorage(Context context, String fileName, String folderName){
        File file = createOrGetFile(context.getCacheDir(), fileName, folderName);
        return readOnFile(context, file);
    }

    public static void writeOnInternalStorage(Context context, String fileName, String text, String folderName) {
        File file = createOrGetFile(context.getFilesDir(), fileName, folderName);
        writeOnFile(context, text, file);
    }

    public static void writeOnVolatileInternalStorage(Context context, String fileName, String text, String folderName) {
        File file = createOrGetFile(context.getCacheDir(), fileName, folderName);
        writeOnFile(context, text, file);
    }

    // ----------------------------------
    // EXTERNAL STORAGE
    // ----------------------------------

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // ---

    public static void writeOnExternalPublicStorage(Context context, String fileName, String text, String folderName) {
        File file = createOrGetFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName, folderName);
        writeOnFile(context, text, file);
    }

    public static void writeOnExternalPrivateStorage(Context context, String fileName, String text, String folderName) {
        File file = createOrGetFile(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName, folderName);
        writeOnFile(context, text, file);
    }

    // ---

    public static String getTextFromExternalPublicStorage(Context context, String fileName, String folderName){
        File file = createOrGetFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName, folderName);
        return readOnFile(context, file);
    }

    public static String getTextFromExternalPrivateStorage(Context context, String fileName, String folderName){
        File file = createOrGetFile(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName, folderName);
        return readOnFile(context, file);
    }

    // ----------------------------------
    // READ & WRITE ON STORAGE
    // ----------------------------------

    private static String readOnFile(Context context, File file){

        String result = null;

        if (file.exists()) {

            BufferedReader br;

            try {

                br = new BufferedReader(new FileReader(file));

                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }

                    result = sb.toString();
                }
                finally {
                    br.close();
                }
            }
            catch (IOException e) {
                Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
            }
        }

        return result;
    }

    // ---

    private static void writeOnFile(Context context, String text, File file){

        try {

            file.getParentFile().mkdirs();

            FileOutputStream fos = new FileOutputStream(file);
            Writer w = new BufferedWriter(new OutputStreamWriter(fos));

            try {
                w.write(text);
                w.flush();
                fos.getFD().sync();
            } finally {
                w.close();
                Toast.makeText(context, context.getString(R.string.saved), Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
        }
    }

}
