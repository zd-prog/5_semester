package com.example.android.ziziko;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CardListAdapter extends ListAdapter<ContactCard, CardViewHolder> {

    private int position;
    public CardListAdapter(@NonNull DiffUtil.ItemCallback<ContactCard> diffCallback) {
        super(diffCallback);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        ContactCard current = getItem(position);
        holder.bind(current.imagePath, current.name, current.company, current.number);

        holder.itemView.setOnLongClickListener(v ->
        {
            setPosition(position);
            return false;
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    static class CardDiff extends DiffUtil.ItemCallback<ContactCard> {

        @Override
        public boolean areItemsTheSame(@NonNull ContactCard oldItem, @NonNull ContactCard newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ContactCard oldItem, @NonNull ContactCard newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
