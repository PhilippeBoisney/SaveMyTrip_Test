package com.openclassrooms.savemytrip.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.openclassrooms.savemytrip.database.data_source.ItemDataSource;
import com.openclassrooms.savemytrip.database.data_source.UserDataSource;
import com.openclassrooms.savemytrip.todolist.ItemViewModel;

/**
 * Created by Philippe on 27/02/2018.
 */


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ItemDataSource itemDataSource;
    private final UserDataSource userDataSource;

    public ViewModelFactory(ItemDataSource itemDataSource, UserDataSource userDataSource) {
        this.itemDataSource = itemDataSource;
        this.userDataSource = userDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(itemDataSource, userDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}