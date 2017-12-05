package com.example.evelina.blomapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.evelina.blomapp.db.FakePlantStore;
import com.example.evelina.blomapp.db.PlantStoreFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditPlant extends AppCompatActivity {
    private static final String LOG_TAG = EditPlant.class.getCanonicalName();
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
    Plant currentPlant = SessionPlant.getInstance().currentPlant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plant);
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

        etPlantStort.setText(currentPlant.getSort());
        etPlantName.setText(currentPlant.getName());
        sbWaterInterval.setProgress(currentPlant.getWaterInterval());
        sbDaysSinceWater.setProgress(currentPlant.getDaysSinceWater());

        ibAddPlantImage.setOnClickListener(new View.OnClickListener() {
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
        bCreatePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlant();
                Intent intent = new Intent(EditPlant.this, MainActivity.class) ;
                startActivity(intent);
            }
        });

        // TODO, bottomnav markerar fortfarande blomiconen som checkd
        mBottomNav.getMenu().getItem(0).setChecked(false);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ActivitySwitcher.selectActivity(EditPlant.this,item);
                return true;
            }
        });
    }

    public void showPopupWindow () {

        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) EditPlant.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ibFlowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibAddPlantImage.setImageResource(R.drawable.ic_local_florist_black_48dp);
                //  plantImage = R.drawable.ic_local_florist_black_48dp;
            }
        });

        //Sätter kaktus som profilbild
        ibCactusButton.setOnClickListener(new View.OnClickListener() {
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
        ibCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();

            }
        });


        // Stänger popup fönster
        bSaveImage.setOnClickListener(new View.OnClickListener() {
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

    }


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

    public void updatePlant(){
        final String plantSort = etPlantStort.getText().toString();
        final String plantName = etPlantName.getText().toString();
        final String plantimage =mCurrentPhotoPath;
        final int waterInterval = sbWaterInterval.getProgress();
        final int daysSinceWater = sbDaysSinceWater.getProgress();

        currentPlant.setName(plantName);
        currentPlant.setSort(plantSort);
        currentPlant.setWaterInterval(waterInterval);
        currentPlant.setDaysSinceWater(daysSinceWater);
        currentPlant.setImage(plantimage);


        Log.d(LOG_TAG, "namn " + currentPlant.getName());

        PlantStoreFactory.getInstance(this). updatePlant(currentPlant);
        // Skapar ny plant



    }


    }

