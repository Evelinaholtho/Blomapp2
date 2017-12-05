package com.example.evelina.blomapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.example.evelina.blomapp.db.PlantStoreFactory;




public class AddPlant extends AppCompatActivity {
    private static final String LOG_TAG = AddPlant.class.getCanonicalName();
    private BottomNavigationView mBottomNav;
    private Button bCreatePlant;
    private EditText etPlantStort;
    private EditText etPlantName;
    private ImageButton ibAddPlantImage;
    private PopupWindow popupWindow;
    private String plantImage;
    private SeekBar sbWaterInterval;
    private SeekBar sbDaysSinceWater;
    private TextView tvWaterInterval;
    private TextView tvDaysSinceWater;
    private static final int CAMERA_REQUEST = 1888;
    Uri file;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_plant);
        BottomNavigationView bottomBar = findViewById(R.id.navigation_view);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation_view);
        bCreatePlant = (Button) findViewById(R.id.bCreatePlant);
        etPlantStort= (EditText) findViewById(R.id.etPlantSort);
        etPlantName = (EditText) findViewById(R.id.etPlantName);
        ibAddPlantImage = (ImageButton) findViewById(R.id.addPlantImage);
        sbWaterInterval = (SeekBar) findViewById(R.id.sbWaterIntervall);
        sbDaysSinceWater = (SeekBar) findViewById(R.id.sbDaysSinceWater);
        tvWaterInterval = (TextView) findViewById(R.id.tvWaterIntervall);
        tvDaysSinceWater = (TextView) findViewById(R.id.tvDaysSinceWater);
        tvWaterInterval.setText( "vattningsintervall");



        // Lyssnare för profilbild, öppnar upp PopupWindow
        ibAddPlantImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        sbWaterInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvWaterInterval.setText( "vattna var... " + sbWaterInterval.getProgress() + "  dag");
                progress_value = progress;

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbDaysSinceWater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDaysSinceWater.setText(sbDaysSinceWater.getProgress() + " dagar sen senaste vattningen");
                progress_value = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Lyssnare för spara knappen, sparar och går till MainActivity
        bCreatePlant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlant();
                Intent intent = new Intent(AddPlant.this, MainActivity.class) ;
                startActivity(intent);
            }
        });


      // TODO, bottomnav markerar fortfarande blomiconen som checkd
      mBottomNav.getMenu().getItem(0).setChecked(false);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ActivitySwitcher.selectActivity(AddPlant.this,item);
                return true;
            }
        });
    }

    // metod som öppnar PopPWindow och gör det möjligt för oss att interagera med det.
    public void showPopupWindow () {

        //We need to get the instance of the LayoutInflater, use the context of this activity
       LayoutInflater inflater = (LayoutInflater) AddPlant.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout (no need for root id, using entire layout)
        final View layout = inflater.inflate(R.layout.popup_window, null);
        popupWindow = new PopupWindow(layout, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // tillhör popupWindow
        ImageButton ibFlowerButton = (ImageButton) layout.findViewById(R.id.ibFlower);
        ImageButton ibCactusButton = (ImageButton) layout.findViewById(R.id.ibCactus);
        ImageButton ibCameraButton = (ImageButton) layout.findViewById(R.id.ibCamera);
        Button bSaveImage = (Button) layout.findViewById(R.id.bsaveImage);

        //kollar så att det finns kamera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ibCameraButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }


       // Sätter blombild som profilbild
        ibFlowerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ibAddPlantImage.setImageResource(R.drawable.ic_local_florist_black_48dp);
               //  plantImage = R.drawable.ic_local_florist_black_48dp;
            }
        });

        //Sätter kaktus som profilbild
        ibCactusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ibAddPlantImage.setImageResource(R.drawable.cactus);
                Uri uri = Uri.parse("android.resource://com.evelina.drawable" + R.drawable.cactus);
                 mCurrentPhotoPath =uri.getPath();
                // plantImage = path;
                //plantImage = R.drawable.cactus;
            }
        });

        // Startar kamera
        ibCameraButton.setOnClickListener(new OnClickListener() {
           @Override
            public void onClick(View v) {
               takePicture();

            }
        });


        // Stänger popup fönster
        bSaveImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }


    public void takePicture() {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            file = Uri.fromFile(createImageFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK);
            ibAddPlantImage.setImageURI(file);

        // getPath(file);


        /*if (requestCode == 100 && resultCode == RESULT_OK) {
                ibAddPlantImage.setImageURI(file);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
        ibAddPlantImage.setImageBitmap(photo);
            }
        }*/
    }

/*
    private File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "CameraDemo");
        String path;
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
*/

/*    // sparar fotot som en bitmap och sätter fotot som profilbild
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d(LOG_TAG, " onActivityResult: " + data);
            Log.d(LOG_TAG, " onActivityResult: " + data.getData());
//            Log.d(LOG_TAG, " onActivityResult: " + data.getData().getPath());
           ibAddPlantImage.setImageBitmap(photo);
        }
    }
*/
    public void makeDrawbleToBitmap(int adress) {

        Uri path = Uri.parse("android.resource://com.evelina.drawable" + R.drawable.cactus);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(LOG_TAG, "path " + mCurrentPhotoPath);
        return image;
    }

    public void savePlant(){
        final String plantSort = etPlantStort.getText().toString();
        final String plantName = etPlantName.getText().toString();
        final String plantimage =mCurrentPhotoPath ;
        final int waterInterval = sbWaterInterval.getProgress();
        final int daysSinceWater = sbDaysSinceWater.getProgress();

        // Skapar ny plant
        Plant plant = new Plant(plantSort,plantName,plantimage, waterInterval, daysSinceWater);
        PlantStoreFactory.getInstance(this).addPlant(plant);

    }

}


