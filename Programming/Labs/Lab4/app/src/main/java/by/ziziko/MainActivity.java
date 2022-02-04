package by.ziziko;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private ImageButton image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.name);
        EditText author = findViewById(R.id.author);
        Button next = findViewById(R.id.accept);
        image = findViewById(R.id.image);
        TextView e_mail = findViewById(R.id.e_mail);
        TextView phone = findViewById(R.id.phone);


        next.setOnClickListener(v ->
                {
                    Intent SecondPageIntent = new Intent(this, MainActivity2.class);
                    SecondPageIntent.putExtra("name1", String.valueOf(name.getText()));
                    SecondPageIntent.putExtra("author1", author.getText().toString());
                    startActivity((SecondPageIntent));
                });
        image.setOnClickListener(v ->
        {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try{
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
            }
        });

        e_mail.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/octet-stream");
            startActivity(intent);

        });

        phone.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone.getText().toString()));
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(thumbnailBitmap);
        }
    }
}