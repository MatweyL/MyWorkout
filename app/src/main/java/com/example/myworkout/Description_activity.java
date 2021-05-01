package com.example.myworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Description_activity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_activity);

        imageView = findViewById(R.id.imageView1);
        String uri=getIntent().getStringExtra("image");
        Picasso.get().load(uri).into(imageView);

        textView=findViewById(R.id.description_item);
        String descriptiontext=getIntent().getStringExtra("description");
        textView.setText(descriptiontext);



    }
}