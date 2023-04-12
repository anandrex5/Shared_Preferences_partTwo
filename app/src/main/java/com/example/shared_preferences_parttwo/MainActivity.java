package com.example.shared_preferences_parttwo;


//SharedPreferences Part Two

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName;
    EditText userMessage;
    Button counter;
    CheckBox remember;
    int count = 0;

    String name;
    String message;
    boolean isChecked;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.textEditView);
        userMessage = findViewById(R.id.editTextMessage);
        counter = findViewById(R.id.button);
        remember = findViewById(R.id.checkBox);

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = count + 1;
                counter.setText("" + count);
            }
        });
        //i'll retrieve the data here
        retrieveData();
    }

    @Override
    protected void onPause() {

        //Now we call the method
        saveData();

        super.onPause();
    }
    //Inside this method, i will call the shared Preference Object.
    public void saveData()
    {
        //this method take two parameters first one is name for the main sharedPreferences class
        //and second one is Context ,that mode is private that will give me the access permission to save data from
        //everywhere in the application, but will restrict accessing outside. So, that other applications will not be able to
        //reach the saved data.

        sharedPreferences = getSharedPreferences("saveData", Context.MODE_PRIVATE);
        //Now put all the data into the Container

        //This code take the name from the user at attacks,convert it into the String and assign it to the name container
        name = userName.getText().toString();
        message = userMessage.getText().toString();

        //when i close the app, if it's checked, it's state is True and if it's unchecked its state is False
        if(remember.isChecked())
        {
            isChecked = true;
        }
        else {
            isChecked = false;
        }
        //We grabbed every piece of data from the app, so we need to save this data
        //i'll save the data using this edit class, it's subclass of the sharedPreference class

        SharedPreferences.Editor editor = sharedPreferences.edit();
        //After that i'll save the data using this editor
        editor.putString("key name",name);
        editor.putString("key message", message);
        editor.putInt("key count", count);
        editor.putBoolean("key remember", isChecked);

        //this line helped to store your data
        editor.commit();

        Toast.makeText(getApplicationContext(), "Your data is saved", Toast.LENGTH_LONG).show();
    }
    //Here we create a retrieved data class saved data class
    public void retrieveData()
    {
        sharedPreferences = getSharedPreferences("saveData",MODE_PRIVATE);

        //i'll call save data back using the same container's
        //if key is not present or nothing is saved, the default value will be null
        name = sharedPreferences.getString("key name", null);
        message = sharedPreferences.getString("key name", null);
        count = sharedPreferences.getInt("key count",0);
        isChecked = sharedPreferences.getBoolean("key remember",false);

        //i took the data using the getText message from the userName component
        userName.setText(name);
        userMessage.setText(message);
        counter.setText("" + count);

        if(isChecked)
        {
            remember.setChecked(true);

        }
        else
        {
            remember.setChecked(false);
        }
    }
}
