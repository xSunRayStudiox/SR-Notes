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
import com.sun_ray.srnotes.adapter.UrlAdapter;
import com.sun_ray.srnotes.db.UrlDatabase;
import com.sun_ray.srnotes.model.Url;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Url_List extends Fragment {

    MaterialCardView create;
    TextView setDate;
    EditText url_Title, url_Url;
    Button Url_Save, Cancel;
    CardView AddLink;
    String get_Title, get_Url;

    ConstraintLayout NoData;
    RecyclerView recyclerView;
    UrlDatabase database;
    UrlAdapter adapter;
    List<Url> list;
    SearchView searchView;

    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_url_list, container, false);

        create = view.findViewById(R.id.add_not_fab);
        NoData = view.findViewById(R.id.noData);
        recyclerView = view.findViewById(R.id.rvNote);

        AddLink = view.findViewById(R.id.add_link);
        setDate = view.findViewById(R.id.url_date);
        url_Title = view.findViewById(R.id.url_title);
        url_Url = view.findViewById(R.id.url_url);
        Url_Save = view.findViewById(R.id.save);
        Cancel = view.findViewById(R.id.cancel);

        // get Date
        date = new SimpleDateFormat("dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
        setDate.setText(date);

        database = UrlDatabase.getDB(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        create.setOnClickListener(v -> {
            AddLink.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            create.setVisibility(View.GONE);
            NoData.setVisibility(View.GONE);
        });

        Url_Save.setOnClickListener(v -> {
            SaveUrl();
        });

        Cancel.setOnClickListener(v -> {
            AddLink.setVisibility(View.GONE);
            create.setVisibility(View.VISIBLE);
            SaveUrl();
        });

        ShowUrls();

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

        }
        catch (Exception error) {
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }

        return view;
    }

    private void SaveUrl() {
        get_Title = url_Title.getText().toString();
        get_Url = url_Url.getText().toString();

        if (get_Title.isEmpty() ){
            Toast.makeText(getContext(), "Empty Heading", Toast.LENGTH_SHORT).show();
        }
        else if (get_Url.isEmpty()) {
            Toast.makeText(getContext(), "Empty Url", Toast.LENGTH_SHORT).show();
        }
        else {
            // Save
            database.urlDao().addUrl(new Url(get_Title, get_Url, date));
            url_Title.getText().clear();
            url_Url.getText().clear();


            AddLink.setVisibility(View.GONE);
            create.setVisibility(View.VISIBLE);
            ShowUrls();
        }
    }

    private void filterSearch(String newText) {
        List<Url> filterList = new ArrayList<>();
        for (Url singleNote : list) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(singleNote);
            }
        }
        adapter.filterList(filterList);
    }

    public void ShowUrls() {
        list = database.urlDao().getUrls();
        if (!list.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            NoData.setVisibility(View.GONE);
            adapter = new UrlAdapter(this, getContext(), list, database);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        } else {
            NoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}