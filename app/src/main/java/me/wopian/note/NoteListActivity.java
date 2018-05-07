package me.wopian.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private List<NotesBuilder> notesList = new ArrayList<>();
    private NotesAdapter notesAdapter;
    private RecyclerView notesRecycler;

    private void showNewNoteDialog (final Context context, final View view) {
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add a new note");
        builder.setView(editText);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNoteTitle = String.valueOf(editText.getText());

                // Default to current date time if no title set
                if (newNoteTitle == null || newNoteTitle.isEmpty()) {
                    newNoteTitle = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                            .format(new Date());
                }

                // TODO: Open Intent
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                intent.putExtra("title", newNoteTitle);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewNoteDialog(NoteListActivity.this, view);
            }
        });

        notesRecycler = (RecyclerView) findViewById(R.id.notes_list);
        notesAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(layoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(notesAdapter);
        getNoteFilesDir();
    }

    private void getNoteFilesDir () {
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            // Ignore directories in private app folder
            if (!file.isFile()) { continue; }

            String title = Uri.decode(file.getName());
            NotesBuilder note = new NotesBuilder(title);
            notesList.add(note);
        }
    }

    /* TODO: Settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }
    */

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


}
