package me.wopian.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private List<NotesBuilder> notesList = new ArrayList<>();

    private void showNewNoteDialog(final Context context, final View view) {
        final EditText editText = new EditText(context);

        new AlertDialog.Builder(context)
                .setTitle("Add a new note")
                .setView(editText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newNoteTitle = String.valueOf(editText.getText());

                        // Default to current date time if no title set
                        if (newNoteTitle == null || newNoteTitle.isEmpty()) {
                            newNoteTitle = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                                    .format(new Date());
                        }

                        Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                        intent.putExtra("title", newNoteTitle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private List<NotesBuilder> getNotes() {
        List<NotesBuilder> list = new ArrayList<>();
        File[] files = getFilesDir().listFiles();

        // List notes by most recently edited
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                // Requires SDK 19+
                // return Long.compare(o1.lastModified(), o2.lastModified());
                return Long.valueOf(o2.lastModified())
                        .compareTo(o1.lastModified());
            }
        });

        for (File file : files) {
            // Ignore directories in private app folder
            if (!file.isFile()) {
                continue;
            }

            // Get the proper title of the note
            String title = Uri.decode(file.getName());
            title = title.substring(0, title.lastIndexOf('.'));

            NotesBuilder note = new NotesBuilder(title);
            list.add(note);
        }

        return list;
    }

    private void listNotes() {
        RecyclerView notesRecycler = (RecyclerView) findViewById(R.id.notes_list);
        NotesAdapter notesAdapter = new NotesAdapter(getNotes());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(layoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(notesAdapter);
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

        // Populate recyclerView with notes
        listNotes();
    }

    // Force a refresh of the notes list when pressing hardware back-button
    @Override
    protected void onRestart() {
        super.onRestart();
        listNotes();
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
