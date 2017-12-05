package com.example.evelina.blomapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evelina.blomapp.db.FakePlantStore;
import com.example.evelina.blomapp.db.PlantStoreFactory;
import com.example.evelina.blomapp.interfaces.PlantStore;
import com.example.evelina.blomapp.PlantAdapter;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addPlantButton;
    private TextView welcomeMessage;
    private BottomNavigationView mBottomNav;
    private List<Plant> plants;
    private ListView listView;
    private PlantAdapter mAdapter;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomBar = findViewById(R.id.navigation_view);
        plants = new ArrayList<>();
        plants = PlantStoreFactory.getInstance(this).getAllPlants();
        listView = (ListView) findViewById(R.id.listViewPlant);
        View header = (View)getLayoutInflater().inflate(R.layout.list_item, null);
        listView.addHeaderView(header);


       mAdapter = new PlantAdapter(this, R.layout.list_item, plants);
        listView.setAdapter(mAdapter);
        addPlantButton=(FloatingActionButton)findViewById(R.id.addPlantButton);
        Log.d(LOG_TAG, "current user: " +     Session.getInstance().currentUser);

        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Intent intent = getIntent();
        String username = Session.getInstance().currentUser.getUsername();

        String message = username + " Välkommen";
        welcomeMessage.setText(message);


        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(AddPlant.class);
            }
        });
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation_view);
        mBottomNav.getMenu().getItem(0).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ActivitySwitcher.selectActivity(MainActivity.this,item);
                return true;
            }
        });

      /*  listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant plant = (Plant) parent.getItemAtPosition(position);
                plant.setPosition(position);
                Log.d(LOG_TAG, "OnItemClic " + plant.getPosition());
            }});

*/
    }



    public void startActivity (Class activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    public void removePlantOnClickHandler(View v) {
        Plant itemToRemove = (Plant) v.getTag();
        Log.d(LOG_TAG, "removePlantListHandeler " + v.getTag());
        mAdapter.remove(itemToRemove);
    }
    }
