package com.openclassrooms.savemytrip.database.data_source;

import com.openclassrooms.savemytrip.database.dao.UserDao;
import com.openclassrooms.savemytrip.models.Item;
import com.openclassrooms.savemytrip.models.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Philippe on 28/02/2018.
 */

public class UserDataSource {

    private final UserDao userDao;

    public UserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    // --- GET USER ---
    public Flowable<User> getUser(long userId){
        return this.userDao.getUser(userId);
    }
}
