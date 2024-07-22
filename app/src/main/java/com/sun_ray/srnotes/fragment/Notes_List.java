package com.sun_ray.srnotes.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.card.MaterialCardView;
import com.sun_ray.srnotes.Activity_Write_Notes;
import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.adapter.NoteAdapter;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class Notes_List extends Fragment {

    MaterialCardView create;
    ConstraintLayout NoData;
    RecyclerView recyclerView;
    NoteDatabase database;
    NoteAdapter adapter;
    List<Note> list;
    SearchView searchView;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        create = view.findViewById(R.id.add_not_fab);
        NoData = view.findViewById(R.id.noData);
        recyclerView = view.findViewById(R.id.rvNote);

        database = NoteDatabase.getDB(getContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        create.setOnClickListener(v -> {
            Intent i = new Intent(requireActivity(), Activity_Write_Notes.class);
            startActivity(i);
        });

        ShowNotes();

        // Searching
        try {
            searchView = (SearchView) view.findViewById(R.id.searchfilter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Search Function
                    filterSearch(newText);
                    return false;
                }
            });

        } catch (Exception error) {
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }

        return view;
    }

    private void filterSearch(String newText) {
        List<Note> filterList = new ArrayList<>();
        for (Note singleNote : list) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(singleNote);
            }
        }
        adapter.filterList(filterList);
    }

    public void ShowNotes() {
        list = database.noteDao().getNote();
        if (!list.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            NoData.setVisibility(View.GONE);
            adapter = new NoteAdapter(this, getContext(), list, database);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        } else {
            NoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}