package com.example.mygrocerylist.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mygrocerylist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import Data.DatabaseHandler;
import Model.Grocery;

public class MainActivity extends AppCompatActivity {
     private AlertDialog.Builder dialogBuilder;
     private AlertDialog dialog;
     private EditText groceryItem;
     private EditText groceryQty;
     private Button saveButton;
     private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          //              .setAction("Action", null).show();
                createPopupDialog();
            }
        });
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
    private void createPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        groceryItem = (EditText) view.findViewById(R.id.groceryItem);
        groceryQty = (EditText) view.findViewById(R.id.groceryQty);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                 public void onClick(View v) {


                //Todo: save db
                //Todo: Goto the next screen

                    if(!groceryItem.getText().toString().isEmpty()&& !groceryQty.getText().toString().isEmpty()){
                        saveGroceryToDB(v);
                    }

            }
        });
    }
        private void saveGroceryToDB(View v) {

            Grocery grocery = new Grocery();

            String newGrocery = groceryItem.getText().toString();
            String newGroceryQuantity = groceryQty.getText().toString();

            grocery.setName(newGrocery);
            grocery.setQuantity(newGroceryQuantity);
            //Save to DB
            db.addGrocery(grocery);

            Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();
            //Log.d("Item Added ID:", String.valueOf(db.getGroceriesCount()));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    //start a new activity
                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                }
            }, 1200); //  1 second.

        }


    public void byPassActivity() {
        //Checks if database is empty; if not, then we just
        //go to ListActivity and show all added items

        if (db.getGroceriesCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }

    }
}


