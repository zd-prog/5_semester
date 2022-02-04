package by.ziziko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter {

    public  ListAdapter(Context context, ArrayList<Book> bookList)
    {
        super(context, R.layout.list_item, bookList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Book book = (Book) getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from((getContext())).inflate(R.layout.list_item, parent, false);
        }

        TextView bookName = convertView.findViewById(R.id.bookName);
        TextView bookAuthor = convertView.findViewById(R.id.bookAuthor);

        bookName.setText(book.name);
        bookAuthor.setText(book.author);

        return convertView;
    }
}
