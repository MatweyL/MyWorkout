package com.example.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FoodMyAdapter foodMyAdapter;
    ArrayList<Food> list;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = findViewById(R.id.foodList);
        databaseReference= FirebaseDatabase.getInstance().getReference("food");
        databaseReference.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        foodMyAdapter = new FoodMyAdapter(this,list);
        recyclerView.setAdapter(foodMyAdapter);
        searchView=findViewById(R.id.searchFood);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Food food = dataSnapshot.getValue(Food.class);
                    list.add(food);
                }
                foodMyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<Food> searList=new ArrayList<>();
        for(Food object: list){
            if(object.getTitle().toLowerCase().contains(str.toLowerCase())){
                searList.add(object);
            }
        }
        FoodMyAdapter foodMyAdapter = new FoodMyAdapter(this,searList);
        recyclerView.setAdapter(foodMyAdapter);

    }


}