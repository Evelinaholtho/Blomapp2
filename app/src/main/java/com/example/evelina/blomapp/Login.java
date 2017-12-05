package com.example.evelina.blomapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.evelina.blomapp.db.FakeUserStore;
import com.example.evelina.blomapp.db.UserStoreFactory;
import com.example.evelina.blomapp.interfaces.UserStore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Login extends AppCompatActivity {
    private static final String LOG_TAG = Login.class.getSimpleName();
    private View mProgressView;
    private View mLoginFormView;
    private EditText etUsername;
    private EditText etPassword;
    private Button bLogin;
    private TextView tvRegister;
   // private UserStore userStore;
   // private List <User> users;
 //   private Map<String, String> usersHashmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        etUsername = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPasswordL);
        bLogin = findViewById(R.id.bLogin);
        tvRegister = findViewById(R.id.tvRegisterLink);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tar input från användaren och lägger i en sträng
              final  String username = etUsername.getText().toString();
              final String password = etPassword.getText().toString();
              findUser(username,password);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
        // kollar så usern finns registrerad och att användarnamn och lösenord stämmer överrens
        public void findUser (String username, String password) {
            Iterator it = UserStoreFactory.getInstance(this).getAllUsersFromHashMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Log.d(LOG_TAG, pair.getKey() + " = " + pair.getValue());
            }
            // kollar efter så att värdena inte är tomma
            if (username.equals("") || password.equals("")) {
                //TODO byt felmeddelande till något mer passande
                Message.alert_msg(Login.this, getString(R.string.loginFaild), getString(R.string.tryAgain));
            } else {
                // kollar så att användanamn finns registrerat
                if (! UserStoreFactory.getInstance(this).getAllUsersFromHashMap().containsKey(username)) {
                    // TODO lägg texten i en sträng
                    Message.alert_msg(Login.this,"Det angivna användarnamnet är felaktigt", getString(R.string.tryAgain));

                    // kollar så att användarnamns stämmer överrens med lösenord
                } else if ( UserStoreFactory.getInstance(this).getAllUsersFromHashMap().get(username).equals(password)) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                    Session.getInstance().currentUser = new User("namn", username, password);
                        startActivity(intent);
                    } else {
                        // TODO Lägg texten i en sträng
                        Message.alert_msg(Login.this,"Det angivna lösenorden är felaktigt", getString(R.string.tryAgain));
                    }
            }
/*      public void enableIfEmpty (String username, String password){
           if (username.equals("") || password.equals("")) {
            bLogin.setEnabled(false);
           // b.setBackgroundDrawable(getResources().getDrawable(buttondark));

            } else {
            bLogin.setEnabled(true)
            }

        }
*/
            }
        }








