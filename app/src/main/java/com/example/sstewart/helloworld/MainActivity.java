package com.example.sstewart.helloworld;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    String msg = "Android : ";
    EditText productNameText;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find controls
        productNameText = (EditText)findViewById(R.id.editText);
        displayText = (TextView)findViewById(R.id.notification_text);



        Log.d(msg, "The onCreate() event");
    }


    @Override
    protected void onStart()    {
        super.onStart();



        Log.d(msg, "The onStart() event");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
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

    public void addProduct(View view){

        MyDbHandler dbHandler = new MyDbHandler(this, null, null, 1);
        String productName = productNameText.getText().toString();
        dbHandler.addProduct(productName);
        UpdateDisplay("Adding Product");
    }

    public void removeProduct(View view){

        MyDbHandler dbHandler = new MyDbHandler(this, null, null, 1);
        String productName = productNameText.getText().toString();
        dbHandler.removeProduct(productName);
        UpdateDisplay("Removing Product");
    }

    public void lookupProduct(View view)
    {
        UpdateDisplay("Looking Up Product");
    }

    private void UpdateDisplay(String context){
        MyDbHandler dbHandler = new MyDbHandler(this, null, null, 1);
        String productName = productNameText.getText().toString();
        Product product = dbHandler.findProduct(productName);

        String message = context + ": ";
        if (product == null) {
            message += productName + " does not exist.";
        }        else {
            message += "There are currently " + product.getQuantity() + " " + productName + "(s).";
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
