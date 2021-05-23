package com.example.myworkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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
    ExerciseL exercise1;
    SearchView searchView1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        listView=findViewById(R.id.ListView1);
        searchView1=findViewById(R.id.searchExers);
        databaseReference= FirebaseDatabase.getInstance().getReference("exercises");
        exercise1 = new ExerciseL();
        title_list = new ArrayList<>();
        image_list = new ArrayList<>();
        description_list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this,R.layout.item_layout, R.id.item, title_list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot a:snapshot.getChildren())
                {
                    exercise1 = a.getValue(ExerciseL.class);
                    title_list.add(exercise1.getTitle());
                    description_list.add(exercise1.getDescription());
                    image_list.add(exercise1.getImage());
                }
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intentItem = new Intent(ReadActivity.this, Description_activity.class);

                        String k =image_list.get(position);
                        intentItem.putExtra("image",k);
                        String p =description_list.get(position);
                        intentItem.putExtra("description",p);

                        startActivity(intentItem);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });/**/
        if (searchView1!=null){
            searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search1(newText);
                    return true;
                }
            });
        }
    }

    private void search1(String newText) {
        if(title_list.contains(newText)){
            Toast.makeText(ReadActivity.this,"Есть!",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ReadActivity.this,"Мы еще не добавили!",Toast.LENGTH_SHORT).show();
        }
        /*for(String object: title_list){
            Log.d("STRING:", object);
            if(newText.toLowerCase().contains(object.toLowerCase())){
                Toast.makeText(ReadActivity.this,"Есть!",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(ReadActivity.this,"Мы еще не добавили!",Toast.LENGTH_SHORT).show();
            }
        }*/

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
