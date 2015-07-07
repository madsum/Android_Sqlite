package com.masum.sqllight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SqlLight";
    private SqlLightDBAdapter DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // when construct a new instance, it calls onCreate of SQLiteOpenHelper
        // if DATABAS VERSION is updated then it calls onUpgrade of SQLiteOpenHelper
        DB = new SqlLightDBAdapter(this);
        Log.i(MainActivity.TAG, "Activity onCreate called!");
        Message.ShowMessage(this, "Activity onCreate called!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onInsert(View view) {
        TextView tvName = (TextView) findViewById(R.id.etName);
        TextView tvPass = (TextView) findViewById(R.id.etPass);
        TextView tvAddress = (TextView) findViewById(R.id.etAddress);

        long ret = DB.insertData(tvName.getText().toString(),
                tvPass.getText().toString(),
                tvAddress.getText().toString());
        if (ret < 0) {
            Message.ShowMessage(this, "data inset failed.");
        } else {
            Message.ShowMessage(this, "data inset successfull.");
        }
    }

    public void onGetAllData(View view) {
        String data = DB.getAllData();
        Message.ShowMessage(this, data);
    }

    public void onGetPassByName(View view) {
        TextView tvName = (TextView) findViewById(R.id.etName);
        String pass = DB.getPassword(tvName.getText().toString());
        Message.ShowMessage(this, pass);
    }

    public void onGetNameByAddress(View view) {
        TextView tvAddress = (TextView) findViewById(R.id.etAddress);
        TextView tvPass = (TextView) findViewById(R.id.etPass);
        String name = DB.getName(tvAddress.getText().toString(), tvPass.getText().toString());
        Message.ShowMessage(this, name);
    }

    public void onDeleteRowByID(View view) {
        TextView tvRowID = (TextView) findViewById(R.id.etRowId);
        DB.deleteRowByID(tvRowID.getText().toString());

    }
}
