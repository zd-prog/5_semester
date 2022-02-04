package com.example.android.ziziko;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository {

    private CardDao mCardDao;
    private LiveData<List<ContactCard>> mAllCards;


    CardRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mCardDao = db.cardDao();
        mAllCards = mCardDao.getAlphabetizedCards();
    }


    LiveData<List<ContactCard>> getAllCards() {
        return mAllCards;
    }


    void insert(ContactCard card) {
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.insert(card);
        });
    }
    void delete(ContactCard card) {
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.delete(card);
        });
    }
}
