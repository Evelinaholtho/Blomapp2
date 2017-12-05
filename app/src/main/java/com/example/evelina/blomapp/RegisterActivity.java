package com.example.evelina.blomapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.evelina.blomapp.db.FakeUserStore;
import com.example.evelina.blomapp.db.UserStoreFactory;
import com.example.evelina.blomapp.interfaces.UserStore;


public class RegisterActivity extends AppCompatActivity {
   //private UserStore userStore;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_register);
   //    userStore = new FakeUserStore(this);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        // Lssnare för Registrera användare knappen
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Läser av input från användare och lägger i strängar
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Skapar en ny User
                User user = new User(name, username, password);
                //lägger till skapad User i listan
                // TODO Fungerar ej, user skapas men inget läggs till i listan.
                UserStoreFactory.getInstance(RegisterActivity.this).addUser(user);

                if (name.equals("")) {
                    Message.alert_msg(RegisterActivity.this, getString(R.string.missingName), getString(R.string.tryAgain));
                }
                else if (username.equals("")) {
                    Message.alert_msg(RegisterActivity.this, getString(R.string.missingUsername), getString(R.string.tryAgain));
                }
                else if (password.equals("")) {
                    Message.alert_msg(RegisterActivity.this, getString(R.string.missingPassword), getString(R.string.tryAgain));
                } else {

                    Intent intent = new Intent(RegisterActivity.this, Login.class);
                    startActivity(intent);
                }
            }
      });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }



    }
