package com.masum.sqllight;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by masum on 05/07/15.
 */
public  class Message {

    public static void ShowMessage( Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
