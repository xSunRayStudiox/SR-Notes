package com.sun_ray.srnotes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.db.UrlDatabase;
import com.sun_ray.srnotes.fragment.Url_List;
import com.sun_ray.srnotes.model.Url;

import java.util.ArrayList;
import java.util.List;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.ViewHolder> {
    Url_List url_list;
    Context context;
    List<Url> list = new ArrayList<>();
    UrlDatabase database;

    public UrlAdapter(Url_List url_list, Context context, List<Url> list, UrlDatabase database) {
        this.url_list = url_list;
        this.context = context;
        this.list = list;
        this.database = database;
    }

    @NonNull
    @Override
    public UrlAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UrlAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_link_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UrlAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.url.setText(list.get(position).getLink());
        holder.date.setText(list.get(position).getDate());
        try {
            holder.url.setSelected(true);
            holder.title.setSelected(true);
        } catch (Exception e){
            Toast.makeText(context, "Error: "+e, Toast.LENGTH_LONG).show();
        }

        holder.cardView.setOnLongClickListener(v -> {
            DeleteItem(position);
            return true;
        });

        holder.Open.setOnClickListener(v -> {
            try {
                String Url = list.get(position).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                context.startActivity(i);
            } catch (Exception e){
                Toast.makeText(context, "Error: Not Support Url "+e, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void DeleteItem(int position) {
        boolean a = true;
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setIcon(R.drawable.ic_delete)
                .setMessage("Are You Sure Want To Delete ?")
                .setPositiveButton("Yes", (dialog1, which) -> {
                    database.urlDao().deleteUrl(new Url(
                            list.get(position).getId(),
                            list.get(position).getTitle(),
                            list.get(position).getLink(),
                            list.get(position).getDate()));
                    url_list.ShowUrls();
                }).setNegativeButton("No", (dialog12, which) -> {}).show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Url> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;
        Button Open;
        TextView title, url, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.Url_item);
            Open = itemView.findViewById(R.id.open);
            title = itemView.findViewById(R.id.title);
            url = itemView.findViewById(R.id.url);
            date = itemView.findViewById(R.id.date);
        }
    }
}
