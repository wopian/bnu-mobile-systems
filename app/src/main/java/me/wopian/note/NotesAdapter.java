package me.wopian.note;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import android.support.v7.app.AppCompatActivity;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NotesBuilder> notesList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        public ViewHolder (View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, NoteActivity.class);
            intent.putExtra("title", title.getText());
            context.startActivity(intent);
        }
    }

    public NotesAdapter () {}

    public NotesAdapter (List<NotesBuilder> notesList) {
        this.notesList = notesList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        NotesBuilder note = notesList.get(position);
        holder.title.setText(note.getTitle());
        // holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount () {
        return notesList.size();
    }
}

