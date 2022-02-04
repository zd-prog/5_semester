package com.example.android.ziziko;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards_table")
public class ContactCard {

    String imagePath;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    String name;

    @NonNull
    @ColumnInfo(name = "company")
    String company;

    @NonNull
    @ColumnInfo(name = "phone")
    String number;

    ContactCard(String imagePath, @NonNull String name, @NonNull String company, String number) {
        this.company = company;
        this.imagePath = imagePath;
        this.name = name;
        this.number = number;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}