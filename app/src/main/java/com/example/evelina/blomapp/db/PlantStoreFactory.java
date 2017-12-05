package com.example.evelina.blomapp.db;

import android.content.Context;

import com.example.evelina.blomapp.interfaces.PlantStore;

/**
 * Created by Evelina on 2017-11-22.
 */

public class PlantStoreFactory {

        static PlantStore plantstore;


        public static PlantStore getInstance(Context c) {
            if (plantstore == null) {
                plantstore = new FakePlantStore(c);
                plantstore.fillPlantList();

            }
            return plantstore;

        }
    }
