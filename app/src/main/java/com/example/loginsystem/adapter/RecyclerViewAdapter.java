package com.example.loginsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsystem.AddNote;
import com.example.loginsystem.R;
import com.example.loginsystem.data.MyDBHandler;
import com.example.loginsystem.model.Notes;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Notes> notesList;
    private MyDBHandler dbHandler;

    public RecyclerViewAdapter(Context context, List<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.dbHandler = new MyDBHandler(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Notes notes = notesList.get(position);

        holder.notesTitle.setText(notes.getTitle());
        holder.notesContent.setText(notes.getContent());
        holder.notesTimeStamp.setText(notes.getTimestamp());

        holder.notesOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Notes notes1 = notesList.get(position);

                Intent intent = new Intent(context, AddNote.class);
                intent.putExtra("email", notes1.getEmail());
                intent.putExtra("title",notes1.getTitle());
                intent.putExtra("content",notes1.getContent());
                intent.putExtra("timeStamp",notes1.getTimestamp());
                intent.putExtra("id",notes1.getId());
                context.startActivity(intent);
            }
        });
        holder.notesDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Notes notes1 = notesList.get(position);

                dbHandler.deleteNote(notes1);
                notesList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notesList.size());

                Toast.makeText(context, "Deleted: " + notes1.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView notesTitle;
        public TextView notesContent;
        public TextView notesTimeStamp;
        public Button notesOpen;
        public Button notesDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            notesTitle = itemView.findViewById(R.id.title);
            notesContent = itemView.findViewById(R.id.content);
            notesTimeStamp = itemView.findViewById(R.id.time);
            notesOpen = itemView.findViewById(R.id.openBtn);
            notesDel = itemView.findViewById(R.id.deleteBtn);

        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();

            Toast.makeText(context, "The position is " + String.valueOf(position), Toast.LENGTH_SHORT).show();
        }
    }
}
