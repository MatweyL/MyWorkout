package com.example.myworkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    List<String> title_list, description_list,image_list;
    ArrayAdapter<String>  arrayAdapter;
    Exercise exercise1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        listView=findViewById(R.id.ListView1);
        databaseReference= FirebaseDatabase.getInstance().getReference("exercises");
        exercise1 = new Exercise();
        title_list = new ArrayList<>();
        image_list = new ArrayList<>();
        description_list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this,R.layout.item_layout, R.id.item, title_list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot a:snapshot.getChildren())
                {
                    exercise1 = a.getValue(Exercise.class);
                    title_list.add(exercise1.getTitle());
                    description_list.add(exercise1.getDescription());
                    image_list.add(exercise1.getImage());
                }
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intentItem = new Intent(ReadActivity.this, Description_activity.class);
                        String p =description_list.get(position);
                        intentItem.putExtra("description",p);
                        intentItem.putExtra("image",p);
                        startActivity(intentItem);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });/**/
    }
}
