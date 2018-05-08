package me.wopian.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class NoteActivity extends AppCompatActivity {
    private String noteTitle;

    public String getNoteTitle() {
        return noteTitle;
    }

    private void setNoteTitle(String title) {
        noteTitle = title;
    }

    public String getNoteFilename() {
        String check = StringUtils.right(noteTitle, 4);
        if (check.equals(".txt")) return Uri.encode(noteTitle);
        else return Uri.encode(noteTitle) + ".txt";
    }

    public File getNoteFile() {
        return getFileStreamPath(getNoteFilename());
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

    public String getNoteContents() {
        String data = "";
        String line;
        InputStream stream;

        try {
            stream = openFileInput(getNoteFilename());
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);

            while ((line = buffer.readLine()) != null) {
                data += line;
            }

        } catch (FileNotFoundException e) {
            // New note
            //Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
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

    public boolean clickBack() {
        if (getParentActivityIntent() == null) {
            onBackPressed();
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    public boolean clickSave() {
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

        return true;
    }

    public boolean deleteNote(File file) {
        if (file.delete()) {
            Toast.makeText(this, "Deleted Note", Toast.LENGTH_SHORT).show();
            return true;
        }

        Toast.makeText(this, "Note not deleted", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean clickDelete() {
        final File file = getNoteFile();
        if (!file.exists()) return false;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        if (!deleteNote(file)) break;
                        Intent intent = new Intent(NoteActivity.this, NoteListActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("Delete note?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .create()
                .show();

        return true;
    }

    public boolean clickEditTitle() {
        final EditText editText = new EditText(NoteActivity.this);
        editText.setText(getNoteTitle());

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteNote(getNoteFile());
                        setNoteTitle(String.valueOf(editText.getText()));
                        getSupportActionBar().setTitle(getNoteTitle());
                        clickSave();
                        break;
                }
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("Edit note title")
                .setView(editText)
                .setPositiveButton("Save", dialogClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return clickBack();
            case R.id.action_save:
                return clickSave();
            case R.id.action_delete:
                return clickDelete();
            case R.id.action_edit_title:
                return clickEditTitle();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
