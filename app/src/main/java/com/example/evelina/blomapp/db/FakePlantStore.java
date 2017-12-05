package com.example.evelina.blomapp.db;

import android.content.Context;
import android.util.Log;

import com.example.evelina.blomapp.Plant;
import com.example.evelina.blomapp.interfaces.PlantStore;
import com.example.evelina.blomapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evelina on 2017-11-15.
 */

public class FakePlantStore implements PlantStore {
    private static final String LOG_TAG = FakeUserStore.class.getSimpleName();

    private List<Plant> plantList = new ArrayList<>();

    {
        Log.d(LOG_TAG, "FakePlantStore constructor");
        Log.d(LOG_TAG, "FakePlantStore constructor size: " + plantList.size());
    }

    @Override
    public List<Plant> getAllPlants() {
        Log.d(LOG_TAG, "getAllUsersFromHashMap size: " + plantList.size());

        return plantList;
    }

    @Override
    public void fillPlantList() {

        //  plantList.add(new Plant("Fredskalla", "null", R.drawable.ic_local_florist_black_36dp, 10, 2));
        //plantList.add(new Plant("Svanssparris", "null",  R.drawable.ic_local_florist_black_36dp, 30, 10));
        // plantList.add(new Plant("Elefantöra", "null",R.drawable.elefant, 7, 1));
        Log.d(LOG_TAG, "plantlisSsize: " + plantList.size());


    }

    @Override
    public void addPlant(Plant plant) {

        plantList.add(plant);
        Log.d(LOG_TAG, "addPlant():  " + plantList.size());

    }

    public FakePlantStore(Context context) {
        Log.d(LOG_TAG, "fakePlantStore()");
    }

    @Override
    public void updatePlant(Plant currentplant) {

        int index = currentplant.getPosition();

        plantList.set(index, currentplant);
    }
}



