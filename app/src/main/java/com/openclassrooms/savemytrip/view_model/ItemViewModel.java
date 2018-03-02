package com.openclassrooms.savemytrip.view_model;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.openclassrooms.savemytrip.repositories.ItemDataRepository;
import com.openclassrooms.savemytrip.repositories.UserDataRepository;
import com.openclassrooms.savemytrip.models.Item;
import com.openclassrooms.savemytrip.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Philippe on 27/02/2018.
 */

public class ItemViewModel extends ViewModel {

    // DATA SOURCES
    private final ItemDataRepository itemDataSource;
    private final UserDataRepository userDataSource;

    // DATA
    @Nullable
    private User currentUser;

    public ItemViewModel(ItemDataRepository itemDataSource, UserDataRepository userDataSource) {
        this.itemDataSource = itemDataSource;
        this.userDataSource = userDataSource;
    }

    // -------------
    // FOR USER
    // -------------

    public Flowable<User> getUser(long userId) {
        if (currentUser != null) {
            return Flowable.just(this.currentUser);
        } else {
            return userDataSource.getUser(userId)
                    .map(user -> this.currentUser = user);
        }
    }

    // -------------
    // FOR ITEM
    // -------------

    public Flowable<List<Item>> getItems(long userId) {
        return itemDataSource.getItems(userId);
    }

    public Completable createItem(Item item) {
        return Completable.fromAction(() -> {
            itemDataSource.createItem(item);
        });
    }

    public Completable deleteItem(long itemId) {
        return Completable.fromAction(() -> {
            itemDataSource.deleteItem(itemId);
        });
    }

    public Completable updateItem(Item item) {
        return Completable.fromAction(() -> {
            itemDataSource.updateItem(item);
        });
    }
}
