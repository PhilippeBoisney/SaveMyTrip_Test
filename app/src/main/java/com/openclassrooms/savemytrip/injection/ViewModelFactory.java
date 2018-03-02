package com.openclassrooms.savemytrip.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.openclassrooms.savemytrip.repositories.ItemDataRepository;
import com.openclassrooms.savemytrip.repositories.UserDataRepository;
import com.openclassrooms.savemytrip.view_model.ItemViewModel;

/**
 * Created by Philippe on 27/02/2018.
 */


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ItemDataRepository itemDataSource;
    private final UserDataRepository userDataSource;

    public ViewModelFactory(ItemDataRepository itemDataSource, UserDataRepository userDataSource) {
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