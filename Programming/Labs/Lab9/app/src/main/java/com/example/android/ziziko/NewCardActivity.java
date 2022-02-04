package com.example.android.ziziko;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class NewCardActivity extends AppCompatActivity {

    private EditText mEditName;
    private EditText mEditCompany;
    private EditText mEditNumber;

    private CardViewModel mCardViewModel;
    CardListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        mEditName = findViewById(R.id.edit_name);
        mEditCompany = findViewById(R.id.edit_company);
        mEditNumber = findViewById(R.id.edit_phone);

        final Button button = findViewById(R.id.button_save);

        mCardViewModel = new ViewModelProvider(this).get(CardViewModel.class);

        adapter = new CardListAdapter(new CardListAdapter.CardDiff());

        mCardViewModel.getAllCards().observe(this, cards -> {
            adapter.submitList(cards);
        });

        button.setOnClickListener(view -> {
            if (mEditName.getText().toString().equals("") || mEditCompany.getText().toString().equals("") ||
                    mEditNumber.getText().toString().equals("")) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG);
            } else {
                mCardViewModel.insert(new ContactCard(null, mEditName.getText().toString(),
                        mEditCompany.getText().toString(), mEditNumber.getText().toString()));
            }

            finish();
        });
    }
}

