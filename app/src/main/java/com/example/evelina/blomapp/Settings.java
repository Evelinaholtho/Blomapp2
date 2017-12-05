package com.example.evelina.blomapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;




public class Settings extends Activity {
    private BottomNavigationView mBottomNav;
    private TextView tvTimeFotNotification;
    private TextView tvSharePlants;
    private TextView tvChangePassword;
    private TextView tvLogOut;
    private TextView time;
    private Calendar calendar;
    private String format = "";
    private Button bSaveTime;
    private TextView tvTitle;
    private int CalendarHour, CalendarMinute;


    private  final String LOG_TAG = Settings.class.getSimpleName();
    private static final int NOTI_SECONDARY1 = 1200;
    private NotificationHelper noti;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        noti = new NotificationHelper(this);

        // settings
        tvChangePassword = (TextView) findViewById(R.id.tvChangePassword);
        tvLogOut = (TextView) findViewById(R.id.tvLogOut);
        tvTimeFotNotification = (TextView) findViewById(R.id.tvTimeforNotification);
        tvSharePlants = (TextView) findViewById(R.id.tvShareplants);


        tvSharePlants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(NOTI_SECONDARY1, getTitleSecondaryText());
            }
        });


        tvTimeFotNotification.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });


        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View mView = getLayoutInflater().inflate(R.layout.change_password_dialog, null);
                final EditText etPassword = (EditText) mView.findViewById(R.id.inputPassword);
                final EditText etPasswordConfirm = (EditText) mView.findViewById(R.id.confirmPassword);
                final Button bChangePassword = (Button) mView.findViewById(R.id.bChangePassword);

                Message.popupLayout(Settings.this, mView);

                        bChangePassword.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String password = etPassword.getText().toString();
                                final String passwordConfirm = etPasswordConfirm.getText().toString();
                                validatePassword(password, passwordConfirm);
                            }
                        });
            }
        });


        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(Settings.this, Login.class);
                //startActivity(intent);
                //Log.d(LOG_TAG, TimePickerFragment.getHour() + " : " + TimePickerFragment.getHour());

            }
        });


                /*
                 * Hämtar bottomNavigationView
                 * Sätter settingssybolen markerad
                 * lyssnare som reagerar på klick
                 */
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation_view);
        mBottomNav.getMenu().getItem(3).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ActivitySwitcher.switchActivity(Settings.this, item);
                return true;
            }
        });
    }



    public void sendNotification(int id, String title) {
        Notification.Builder nb = null;
        nb = noti.getNotification2(title, getString(R.string.notitficationText));
         if (nb != null) {
            noti.notify(id, nb);
        }
    }


    private String getTitleSecondaryText() {
        return getString(R.string.notificationTextApp);
        }


            /*  Hämtar en en instans av den nuvarande användaren och dess lösenord
             *  Kollar så att det nya lösenordet inte är samma som innan
             *  kollar så att det nya lösenorden och bekräftelse av lösenordet har samma värde
             *
             */



    public void validatePassword(String newPassword, String confirmPassword) {
        String currentPassword = Session.getInstance().currentUser.getPassword();

        if (newPassword.equals(currentPassword)) {
            Message.alert_msg(Settings.this, "lika som innan", getString(R.string.tryAgain));

        } else if (newPassword.equals(confirmPassword)) {
            changePassword(newPassword);

        } else if (!newPassword.equals(confirmPassword)) {
            Message.alert_msg(Settings.this, getString(R.string.loginFaild), getString(R.string.tryAgain));
            Message.close = false;
        } else {
            Message.alert_msg(Settings.this, "på else", getString(R.string.tryAgain));

        }
    }

    public void changePassword(String newPassword) {
        Session.getInstance().currentUser.setPassword(newPassword);
    }

    //Skapa intet till alarmReciver
    //Intent alarRecivrIntent = new Intent(this, AlarmReciver.class);


} // slut


        /*
            public void setTime(View view) {
                int hour = timePicker1.getCurrentHour();
                int min = timePicker1.getCurrentMinute();
                showTime(hour, min);
            }

            public void showTime(int hour, int min) {
                if (hour == 0) {
                    hour += 12;
                    format = "AM";
                } else if (hour == 12) {
                    format = "PM";
                } else if (hour > 12) {
                    hour -= 12;
                    format = "PM";
                } else {
                    format = "AM";
                }

                time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                        .append(" ").append(format));
            }

*/


