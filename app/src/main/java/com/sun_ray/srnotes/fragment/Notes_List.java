package com.sun_ray.srnotes.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sun_ray.srnotes.R;

public class Notes_List extends Fragment {

    private LinearLayout create;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes__list, container, false);

        create = view.findViewById(R.id.add_not_fab);
//        recyclerView = view.findViewById(R.id.note_list);
//        empty = view.findViewById(R.id.empty_Notes);
//
//        helper = DatabaseHelper.getDB(getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        create.setOnClickListener(v -> {
            Fragment fragment = new Write_Notes();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.Main_Fragment, fragment);
            transaction.commit();
        });

       // ShowNotes();

        return view;
    }

    public void ShowNotes(){
//        list = (ArrayList<DataTable>) helper.dataNotesDOA().getData();
//        Collections.reverse(list);
//        if (list.size()>0){
//            recyclerView.setVisibility(View.VISIBLE);
//            empty.setVisibility(View.GONE);
//            recyclerView.setAdapter(new NotesAdapter(getContext(),list,helper,this));
//        } else {
//            empty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }

    }

}