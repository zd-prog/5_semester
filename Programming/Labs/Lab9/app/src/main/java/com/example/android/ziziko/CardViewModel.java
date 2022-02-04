package com.example.android.ziziko;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository mRepository;
    private final LiveData<List<ContactCard>> mAllCards;

    public CardViewModel(Application application) {
        super(application);
        mRepository = new CardRepository(application);
        mAllCards = mRepository.getAllCards();
    }

    LiveData<List<ContactCard>> getAllCards() {
        return mAllCards;
    }

    void insert(ContactCard card) {
        mRepository.insert(card);
    }
    void delete(ContactCard card) {
        mRepository.delete(card);
    }

}
