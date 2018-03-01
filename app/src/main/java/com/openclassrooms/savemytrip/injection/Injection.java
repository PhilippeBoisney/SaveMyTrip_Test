package com.openclassrooms.savemytrip.injection;

import android.content.Context;

import com.openclassrooms.savemytrip.database.SaveMyTripDatabase;
import com.openclassrooms.savemytrip.database.data_source.ItemDataSource;
import com.openclassrooms.savemytrip.database.data_source.UserDataSource;
import com.openclassrooms.savemytrip.view_model.ViewModelFactory;

/**
 * Created by Philippe on 27/02/2018.
 */

public class Injection {

    public static ItemDataSource provideItemDataSource(Context context) {
        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);
        return new ItemDataSource(database.itemDao());
    }

    public static UserDataSource provideUserDataSource(Context context) {
        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);
        return new UserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ItemDataSource dataSourceItem = provideItemDataSource(context);
        UserDataSource dataSourceUser = provideUserDataSource(context);
        return new ViewModelFactory(dataSourceItem, dataSourceUser);
    }
}
