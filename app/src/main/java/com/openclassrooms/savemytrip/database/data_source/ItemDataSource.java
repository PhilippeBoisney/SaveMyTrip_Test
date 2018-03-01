package com.openclassrooms.savemytrip.database.data_source;

import com.openclassrooms.savemytrip.database.dao.ItemDao;
import com.openclassrooms.savemytrip.models.Item;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Philippe on 27/02/2018.
 */

public class ItemDataSource {

    private final ItemDao itemDao;

    public ItemDataSource(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    // --- GET ---

    public Flowable<List<Item>> getItems(long userId){
        return this.itemDao.getItems(userId);
    }

    // --- CREATE ---

    public void createItem(Item item){
        itemDao.insertItem(item);
    }

    // --- DELETE ---
    public void deleteItem(long itemId){
        itemDao.deleteItem(itemId);
    }

    // --- UPDATE ---
    public void updateItem(Item item){
        itemDao.updateItem(item);
    }

}
