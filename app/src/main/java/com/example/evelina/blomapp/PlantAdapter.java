package com.example.evelina.blomapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Evelina on 2017-11-16.
 */

public class PlantAdapter extends ArrayAdapter<Plant> {
    private Context context;
    private List<Plant> plantList;
    private int layoutResourceId;
    private Activity parentActivity;
    //private LayoutInflater inflater;
    private static final String LOG_TAG = PlantAdapter.class.getSimpleName();
    private static final int NOTI_SECONDARY1 = 1200;
    Plant currentPlant;



    public PlantAdapter(@NonNull Context context, int layoutResourceId, List<Plant> plantList ) {
      super(context, 0 , plantList);
        this.context = context;
        this.plantList = plantList;
        this.layoutResourceId = layoutResourceId;

    }


    static class viewHolder {
        TextView plantName;
        TextView plantSort;
        ImageView plantImage;
        ImageView editPlant;
        ImageView deletePlant;
        ImageView waterPlant;
        ProgressBar progressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
    View row =convertView;
    viewHolder holder = null;

        Log.v("row", String.valueOf(row));

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);


            //convertView = inflater.inflate(R.layout.list_item, parent, false);
            // convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
           // currentPlant = plantList.get(position);
            //currentPlant.setPosition(position);
            //Log.d(LOG_TAG, "positionen " + currentPlant.getPosition());

            holder = new viewHolder();
            holder.editPlant = (ImageView) row.findViewById(R.id.ibEdit);
            holder.plantImage = (ImageView) row.findViewById(R.id.ivPlantImage);
            holder.waterPlant = (ImageView) row.findViewById(R.id.ibWater);
            holder.deletePlant = (ImageView) row.findViewById(R.id.ibDelete);
            holder.plantName = (TextView) row.findViewById(R.id.tvPlantName);
            holder.plantSort = (TextView) row.findViewById(R.id.tvPlantSortli);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.progressBarList);
            row.setTag(holder);

         } else {

            holder = (viewHolder)row.getTag();
        }

        final Plant plant = plantList.get(position);
        Log.v("Position", String.valueOf(position));
        holder.plantName.setText(plant.getName());
        holder.plantSort.setText(plant.getSort());
        holder.plantImage.setImageURI(Uri.parse(new File(plant.getImagePlant()).toString()));
        countprogress(plant, holder.progressBar);
        final ProgressBar pb = holder.progressBar;



        //  ImageView ibEditi = (ImageView) convertView.findViewById(R.id.ibEdit);
      // ImageView ibWater = (ImageView) convertView.findViewById(R.id.ibWater);
       // final TextView plantName = (TextView) convertView.findViewById(R.id.tvPlantName);
       // plantName.setText(currentPlant.getName());

//        final TextView plantSort = (TextView) convertView.findViewById(R.id.tvPlantSortli);
  //      plantSort.setText(currentPlant.getSort());
    //    final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBarList);
      //  countprogress(currentPlant, progressBar);




           holder.editPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String plantSort = plant.getSort();
                String plantName = plant.getName();
                String plantImage = plant.getImagePlant();
                int waterInterval = plant.getWaterInterval();
                int daySinceWater = plant.getDaysSinceWater();

                Intent intent  = new Intent(context, EditPlant.class);

                Log.d(LOG_TAG, "input from class" + plantSort);
                SessionPlant.getInstance().currentPlant = new Plant(plantSort, plantName, plantImage,waterInterval, daySinceWater);
                Log.d(LOG_TAG, "plantan är " + currentPlant);

                context.startActivity(intent);
            }
        });


           holder.waterPlant.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int daySinceWater = 0;
                   plant.setDaysSinceWater(daySinceWater);
                   countprogress(plant, pb);
               }
           });

        return row;
    }



    public void countprogress(Plant plant, ProgressBar pb){
        int daysSinceWater= plant.getDaysSinceWater();
        int waterInterval = plant.getWaterInterval();

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);  // number of days to add
        today = (c.getTime());
        Log.d(LOG_TAG, "date: " + today);

        Date waterDay = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, waterInterval - daysSinceWater);
        waterDay = calendar.getTime();
        Log.d(LOG_TAG, "waterday: " + waterDay);

      SimpleDateFormat formattedDate = new SimpleDateFormat("yyyyMMdd");

        String sToday = (String)(formattedDate.format(c.getTime()));
        String sWaterday = (String)(formattedDate.format(c.getTime()));


        if (today.equals(waterDay)){

            Log.d(LOG_TAG, "notifikation: " + waterDay);

        }

        if (today == waterDay) {
            Log.d(LOG_TAG, "notifikation==: " + waterDay);

        }
        if (sToday == sWaterday) {
            Log.d(LOG_TAG, "notifikation==: " + waterDay);

        }

        pb.setMax(waterInterval);
        pb.setProgress(waterInterval-daysSinceWater);


    }



}


