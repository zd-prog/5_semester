package com.example.android.ziziko;

import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    private final TextView cardName, cardCompany, cardPhone;

    private CardViewHolder(View view)
    {
        super(view);
        cardName = view.findViewById(R.id.personName);
        cardCompany = view.findViewById(R.id.personCompany);
        cardPhone = view.findViewById(R.id.personPhone);
        View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Choose action");
                menu.add(0, v.getId(), 0, "Delete");
            }
        };
        view.setOnCreateContextMenuListener(contextMenuListener);
    }

    public void bind(String path, String name, String company, String phone)
    {
        cardName.setText(name);
        cardCompany.setText(company);
        cardPhone.setText(phone);
    }

    static CardViewHolder create(ViewGroup parent)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new CardViewHolder(view);
    }
}
