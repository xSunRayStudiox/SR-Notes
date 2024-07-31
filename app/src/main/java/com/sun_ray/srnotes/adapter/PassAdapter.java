package com.sun_ray.srnotes.adapter;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.db.PassDatabase;
import com.sun_ray.srnotes.fragment.Password_List;
import com.sun_ray.srnotes.model.Password;

import java.util.ArrayList;
import java.util.List;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.ViewHolder> {
    Password_List password_list;
    Context context;
    List<Password> list = new ArrayList<>();
    PassDatabase database;

    public PassAdapter(Password_List password_list, Context context, List<Password> list, PassDatabase database) {
        this.password_list = password_list;
        this.context = context;
        this.list = list;
        this.database = database;
    }

    @NonNull
    @Override
    public PassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PassAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_password_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PassAdapter.ViewHolder holder, int position) {
        holder.Heading.setText(list.get(position).getHeading());
        holder.ID.setText(list.get(position).getUserId());
        holder.Pass.setText(list.get(position).getPass());
        holder.Date.setText(list.get(position).getDate());

        holder.CopyId.setOnClickListener(v -> {
            CopyClass(list.get(position).getUserId());
        });

        holder.CopyPass.setOnClickListener(v -> {
            CopyClass(list.get(position).getPass());
        });

        holder.Pass_item.setOnLongClickListener(v -> {
            DeleteItem(position);
            return true;
        });
    }

    private void DeleteItem(int position) {
        boolean a = true;
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setIcon(R.drawable.ic_delete)
                .setMessage("Are You Sure Want To Delete ?")
                .setPositiveButton("Yes", (dialog1, which) -> {
                    database.passDao().deletePass(new Password(
                            list.get(position).getId(),
                            list.get(position).getHeading(),
                            list.get(position).getUserId(),
                            list.get(position).getPass(),
                            list.get(position).getDate()));
                    password_list.ShowPass();
                }).setNegativeButton("No", (dialog12, which) -> {}).show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Password> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    public void CopyClass(String text){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copped", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copped : "+text, Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Heading, ID, Pass, Date;
        ImageView CopyId, CopyPass;
        LinearLayout Pass_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Pass_item = itemView.findViewById(R.id.Password_item);
            Heading = itemView.findViewById(R.id.pass_title);
            ID = itemView.findViewById(R.id.pass_id);
            Pass = itemView.findViewById(R.id.pass_password);
            Date = itemView.findViewById(R.id.pass_date);
            CopyId = itemView.findViewById(R.id.copy_id);
            CopyPass = itemView.findViewById(R.id.copy_pass);
        }
    }
}
