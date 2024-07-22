package com.sun_ray.srnotes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sun_ray.srnotes.Activity_Write_Notes;
import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.fragment.Notes_List;
import com.sun_ray.srnotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Notes_List notes_list;
    Context context;
    List<Note> list = new ArrayList<>();
    NoteDatabase database;

    public NoteAdapter(Notes_List notes_list, Context context, List<Note> list, NoteDatabase database) {
        this.notes_list = notes_list;
        this.context = context;
        this.list = list;
        this.database = database;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDate());
        holder.des.setText(list.get(position).getContent());

        // Retrieve Item Border Color Array
        TypedArray border = context.getResources().obtainTypedArray(R.array.dark_color);
        int[] borderArray = new int[border.length()];

        for (int i = 0; i < border.length(); i++){
            borderArray[i] = border.getColor(i,0);
        }
        border.recycle();
        holder.borderColor.setBackgroundColor(borderArray[list.get(position).getColorIndex()]);

        // Retrieve Item Page Color Array
        TypedArray page = context.getResources().obtainTypedArray(R.array.color_picker);
        int[] pageArray = new int[page.length()];

        for (int i = 0; i < page.length(); i++){
            pageArray[i] = page.getColor(i,0);
        }
        page.recycle();
        holder.pageColor.setBackgroundColor(pageArray[list.get(position).getColorIndex()]);

        holder.btn.setOnClickListener(v -> {
            OpenNotes(position,v);
        });

        holder.btn.setOnLongClickListener(v -> {
            DeleteItem(position);
            return true;
        });
    }

    private void DeleteItem(int position) {
        boolean a = true;
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are You Sure Want To Delete ?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Yes", (dialog1, which) -> {
                    database.noteDao().deleteNote(new Note(
                            list.get(position).getId(),
                            list.get(position).getTitle(),
                            list.get(position).getContent(),
                            list.get(position).getDate(),
                            list.get(position).getColorIndex()));
                    notes_list.ShowNotes();
                }).setNegativeButton("No", (dialog12, which) -> {}).show();
    }

    private void OpenNotes(int position, View v) {
        String argTitle = list.get(position).getTitle();
        String argDes = list.get(position).getContent();
        int argColor = list.get(position).getColorIndex();
        int id = list.get(position).getId();

        Intent i = new Intent(context, Activity_Write_Notes.class);
        i.putExtra("Title",argTitle);
        i.putExtra("Description",argDes);
        i.putExtra("Index",argColor);
        i.putExtra("ID",id);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Note> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView btn;
        View borderColor;
        MaterialTextView title, date;
        TextView des;
        LinearLayout pageColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.noteItem);
            borderColor = itemView.findViewById(R.id.BorderColor);
            title = itemView.findViewById(R.id.Title);
            date = itemView.findViewById(R.id.noteDate);
            des = itemView.findViewById(R.id.noteContent);
            pageColor = itemView.findViewById(R.id.PageColor);
        }
    }
}
