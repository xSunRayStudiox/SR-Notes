package com.sun_ray.srnotes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.adapter.PassAdapter;
import com.sun_ray.srnotes.db.PassDatabase;
import com.sun_ray.srnotes.model.Password;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Password_List extends Fragment {

    MaterialCardView create;
    TextView setDate;
    EditText pass_Title, pass_id, pass_pass;
    Button Save, Cancel;
    CardView Add_Pass;
    String get_Title, get_id, get_pass, date;

    ConstraintLayout NoData;
    RecyclerView recyclerView;
    PassDatabase database;
    PassAdapter adapter;
    List<Password> list;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        create = view.findViewById(R.id.add_not_fab);
        NoData = view.findViewById(R.id.noData);
        recyclerView = view.findViewById(R.id.rvPassword);
        Add_Pass = view.findViewById(R.id.add_password);

        setDate = view.findViewById(R.id.pass_date);
        pass_Title = view.findViewById(R.id.password_title);
        pass_id = view.findViewById(R.id.password_id);
        pass_pass = view.findViewById(R.id.password_password);

        Save = view.findViewById(R.id.save);
        Cancel = view.findViewById(R.id.cancel);

        // get Date
        date = new SimpleDateFormat("dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
        setDate.setText(date);

        database = PassDatabase.getDB(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        create.setOnClickListener(v -> {
            Add_Pass.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            create.setVisibility(View.GONE);
            NoData.setVisibility(View.GONE);
        });

        Save.setOnClickListener(v -> {
            SavePass();
        });

        Cancel.setOnClickListener(v -> {
            Add_Pass.setVisibility(View.GONE);
            create.setVisibility(View.VISIBLE);
            ShowPass();
        });

        ShowPass();

        // Searching
        try {
            searchView = (SearchView) view.findViewById(R.id.SearchView);
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

        }
        catch (Exception error) {
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }

        return view;
    }

    private void SavePass() {
        get_Title = pass_Title.getText().toString();
        get_id = pass_id.getText().toString();
        get_pass = pass_pass.getText().toString();

        if (get_Title.isEmpty() ){
            Toast.makeText(getContext(), "Empty Heading", Toast.LENGTH_SHORT).show();
        }
        else if (get_id.isEmpty()) {
            Toast.makeText(getContext(), "Empty ID", Toast.LENGTH_SHORT).show();
        }
        else if (get_pass.isEmpty()) {
            Toast.makeText(getContext(), "Empty Password", Toast.LENGTH_SHORT).show();
        }
        else {
            // Save
            database.passDao().addPass(new Password(get_Title,date, get_id, get_pass));
            pass_Title.getText().clear();
            pass_id.getText().clear();
            pass_pass.getText().clear();

            Add_Pass.setVisibility(View.GONE);
            create.setVisibility(View.VISIBLE);
            ShowPass();
        }
    }

    public void ShowPass() {
        list = database.passDao().getPass();
        if (!list.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            NoData.setVisibility(View.GONE);
            adapter = new PassAdapter(this, getContext(), list, database);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        } else {
            NoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void filterSearch(String newText) {
        List<Password> filterList = new ArrayList<>();
        for (Password singleNote : list) {
            if (singleNote.getHeading().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(singleNote);
            }
        }
        adapter.filterList(filterList);
    }
}