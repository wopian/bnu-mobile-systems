package me.wopian.note;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NotesBuilder> notesList;

    NotesAdapter(List<NotesBuilder> notesList) {
        this.notesList = notesList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotesBuilder note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.ago.setText(note.getAgo());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        public TextView title;
        public TextView ago;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.card_title);
            ago = view.findViewById(R.id.card_ago);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, NoteActivity.class);
            intent.putExtra("title", title.getText());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(view.getContext(), "Long press", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}

