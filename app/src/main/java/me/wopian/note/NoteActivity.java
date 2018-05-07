package me.wopian.note;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private String noteTitle;

    public String getNoteTitle () {
        return noteTitle;
    }

    public String getNoteFilename () {
        String check = StringUtils.right(noteTitle, 4);
        if (check.equals(".txt")) return Uri.encode(noteTitle);
        else return Uri.encode(noteTitle) + ".txt";
    }

    public String setNoteTitle (String title) {
        return noteTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        setNoteTitle(getIntent().getStringExtra("title"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()
                .setTitle(noteTitle);

        EditText noteText = findViewById(R.id.note_text);
        noteText.setText(getNoteContents());
    }

    public void saveNote () {
        EditText noteText = findViewById(R.id.note_text);
        String data = noteText.getText().toString();
        FileOutputStream stream;

        try {
            stream = openFileOutput(getNoteFilename(), Context.MODE_PRIVATE);
            stream.write(data.getBytes());
            stream.close();
            Toast.makeText(this, "Saved Note", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getNoteContents () {
        String data = "";
        String line;
        InputStream stream;

        try {
            stream = openFileInput(getNoteFilename());
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);

            while((line = buffer.readLine()) != null) {
                data += line;
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public synchronized boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case R.id.action_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
