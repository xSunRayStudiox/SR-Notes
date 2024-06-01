package com.sun_ray.srnotes.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Write_Notes extends Fragment {

    private ImageView back_Button, save_Note, Url_Pick, image_Pick, fabColor_Pick, note_Delete;
    private EditText et_Title, et_Content;
    private String date;

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write__notes, container, false);

        back_Button = view.findViewById(R.id.backButton);
        back_Button.setOnClickListener(v -> setFragment());
        
        et_Title = view.findViewById(R.id.etTitle);
        et_Content = view.findViewById(R.id.etContent);
        
        save_Note = view.findViewById(R.id.saveNote);
        Url_Pick = view.findViewById(R.id.UrlPick);
        image_Pick = view.findViewById(R.id.imagePick);
        fabColor_Pick = view.findViewById(R.id.fabColorPick);
        note_Delete = view.findViewById(R.id.noteDelete);

        // get Date
        date = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());

        // Click to Save
        save_Note.setOnClickListener(v -> {
            saveNotes();
        });
        
        return view;
    }
    
    private void saveNotes(){
        if (et_Title.getText().toString().trim().isEmpty()){
            Toast.makeText(requireActivity(), "Note Title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (et_Content.getText().toString().trim().isEmpty()){
            Toast.makeText(requireActivity(), "Note Content can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create object to save data
        final Note note = new Note();
        note.setTitle(et_Title.getText().toString());
        note.setContent(et_Content.getText().toString());
        note.setDate(date);

        class SaveNoteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getDB(requireContext()).noteDao().addNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                requireActivity().setResult(Activity.RESULT_OK, intent);
                requireActivity().finish();
            }
        }

        new SaveNoteTask().execute();

    }


    // On BackPress in Fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setFragment();
            }};
        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }

    // Set Fragment
    private void setFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Main_Fragment, new Notes_List());
        transaction.commit();
    }


}