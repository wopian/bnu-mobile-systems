package me.wopian.note;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NotesBuilder> notesList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder (View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

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

