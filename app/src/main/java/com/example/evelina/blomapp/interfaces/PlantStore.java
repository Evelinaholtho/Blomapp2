package com.example.evelina.blomapp.interfaces;

import com.example.evelina.blomapp.Plant;

import java.util.List;

/**
 * Created by Evelina on 2017-11-15.
 */

public interface PlantStore {

    List<Plant> getAllPlants();
    void fillPlantList();
    void addPlant(Plant plant);
    void updatePlant (Plant plant);


}
