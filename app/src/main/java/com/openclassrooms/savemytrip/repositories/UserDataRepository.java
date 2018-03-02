package com.openclassrooms.savemytrip.repositories;

import com.openclassrooms.savemytrip.database.dao.UserDao;
import com.openclassrooms.savemytrip.models.User;

import io.reactivex.Flowable;

/**
 * Created by Philippe on 28/02/2018.
 */

public class UserDataRepository {

    private final UserDao userDao;

    public UserDataRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    // --- GET USER ---
    public Flowable<User> getUser(long userId){
        return this.userDao.getUser(userId);
    }
}
