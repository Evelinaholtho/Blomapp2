package com.example.evelina.blomapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Evelina on 2017-11-14.
 */


public  class Message {
/*
 * ett meddelande som visas upp för användaren
 * tar en context, meddelande, och en text till navigationsknapp
 *
*/
public Message(){


}
android.support.v7.app.AlertDialog.Builder mBuilder;
public static boolean close =true;


    public static void alert_msg(Context context, String message, String navigateButtonText) {
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        alertDialog.setNegativeButton(navigateButtonText, null);
        alertDialog.setMessage(message);
        alertDialog.create();
        alertDialog.show();
    }

    public static void popupLayout(Context context, View layout){

        LayoutInflater inflater = LayoutInflater.from(context);
        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        mBuilder.setView(layout);
        final android.support.v7.app.AlertDialog dialog = mBuilder.create();
            dialog.show();

    }








    }




