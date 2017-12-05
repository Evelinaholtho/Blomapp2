package com.example.evelina.blomapp;

import android.net.Uri;
import android.util.Log;

import java.net.URI;
import java.util.List;

/**
 * Created by Evelina on 2017-11-14.
 */

public class Plant {
    private String sort;
    private String name;
    private String imagePlant;
    private int waterInterval;
    private int daysSinceWater;
    private int index;
    private static int counter;
    private int objectId;
    private static final String LOG_TAG = Plant.class.getSimpleName();





    public Plant (String sort, String name, String imagePlant, int waterInterval, int daysSinceWater){
        this.sort = sort;
        this.name = name;
        this.imagePlant = imagePlant;
        this. waterInterval = waterInterval;
        this.daysSinceWater = daysSinceWater;
        this.objectId = counter++;
        Log.d(LOG_TAG, "objekt id =  " + objectId);
        Log.d(LOG_TAG, "counter  =  " + counter);


    }

    public String getName (){
        return name;
    }

    public String getSort(){
        return sort;
    }

    public String getImagePlant(){
        return imagePlant;
    }

    public int getWaterInterval(){
        return waterInterval;
    }

    public int getDaysSinceWater(){
        return daysSinceWater;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setSort (String newSort){
        this.sort = newSort;
    }

    public void setImage (String newimagePlant){
        this.imagePlant = newimagePlant;
    }

    public void setPosition(int position){
        index = position;
    }

    public int getPosition (){
        return index;
    }
    public int getId(){
        return objectId;
    }




    public void  setWaterInterval(int waterInterval){
        this.waterInterval = waterInterval;
    }
    public void setDaysSinceWater(int daysSinceWater){
        this.daysSinceWater=daysSinceWater;
    }



}
