package com.example.android.ziziko;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards_table ORDER BY name ASC")
    LiveData<List<ContactCard>> getAlphabetizedCards();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ContactCard card);

    @Query("DELETE FROM cards_table")
    void deleteAll();

    @Delete
    void delete(ContactCard card);
}
