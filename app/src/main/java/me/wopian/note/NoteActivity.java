package me.wopian.note;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class NoteActivity extends AppCompatActivity {
    private String noteTitle;

    public String getNoteTitle () {
        return noteTitle;
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
    }

    public void saveNote () {
        String ext = ".txt";
        String filename = Uri.encode(getNoteTitle());
        String path = filename + ext;

        EditText noteText = findViewById(R.id.note_text);
        String data = noteText.getText().toString();
        FileOutputStream stream;

        try {
            stream = openFileOutput(path, Context.MODE_PRIVATE);
            stream.write(data.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*

        try {
            FileOutputStream stream = new FileOutputStream(getNoteTitle());
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            Log.e("error", "failed to save note", e);
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
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
