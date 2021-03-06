package com.example.todorovah.keepwatching;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewRecord extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase myDatabase;
    Button saveButton;
    RadioGroup radioGroup;
    EditText titleText, notesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        //let movie be default choice
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(R.id.movieButton);

        titleText = findViewById(R.id.titleInput);
        notesText = findViewById(R.id.notesInput);

        myDatabase = openOrCreateDatabase(LoadEntries.DATABASE_NAME, MODE_PRIVATE, null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        String title = titleText.getText().toString().trim();
        if(title.isEmpty()) {
            titleText.setError("Title can't be empty");
            titleText.requestFocus();
            return;
        }

        String notes = notesText.getText().toString().trim();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String timestamp = sdf.format(cal.getTime());

        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if(radioButtonId == R.id.movieButton){
            String sql = "INSERT INTO entry(title, timestamp, notes, is_movie)" +
                    "VALUES(?, ?, ?, 1);";
            myDatabase.execSQL(sql, new String[]{title, timestamp,notes});
            Toast.makeText(this, "Movie saved!", Toast.LENGTH_SHORT).show();
        } else {
            String sql = "INSERT INTO entry(title, timestamp, notes, is_movie)" +
                    "VALUES(?, ?, ?, 0);";
            myDatabase.execSQL(sql, new String[]{title, timestamp,notes});
            Toast.makeText(this, "TV show saved!", Toast.LENGTH_SHORT).show();

        }

        titleText.getText().clear();
        notesText.getText().clear();
    }


}
