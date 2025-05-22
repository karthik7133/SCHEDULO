package com.carcar.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter4 extends RecyclerView.Adapter<Adapter4.GpaViewHolder> {
    private List<gpamodel> gpaList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private gpacalc activity;

    public Adapter4(List<gpamodel> gpaList, Context context, SharedPreferences sharedPreferences, gpacalc activity) {
        this.gpaList = gpaList;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GpaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpamodel, parent, false);
        return new GpaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GpaViewHolder holder, int position) {
        gpamodel gpaItem = gpaList.get(position);
        holder.semesterTextView.setText("Semester: " + gpaItem.getSemester());
        holder.gpaTextView.setText("GPA: " + gpaItem.getGpa());
        holder.creditsTextView.setText("Credits: " + gpaItem.getCredits());

        // Slide up animation
        holder.itemView.setTranslationY(200);
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(600)
                .start();

        holder.itemView.setOnLongClickListener(v -> {
            showOptionsDialog(position);
            return true;
        });



    }

    @Override
    public int getItemCount() {
        return gpaList.size();
    }

    public static class GpaViewHolder extends RecyclerView.ViewHolder {
        TextView semesterTextView, gpaTextView, creditsTextView;

        public GpaViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterTextView = itemView.findViewById(R.id.rowsem);
            gpaTextView = itemView.findViewById(R.id.rowgpa);
            creditsTextView = itemView.findViewById(R.id.rowcredits);
        }
    }

    private void showOptionsDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Entry");
        builder.setMessage("Are you sure you want to delete this entry?");
        builder.setPositiveButton("Delete", (dialog, which) -> deleteItem(position));
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteItem(int position) {
        gpaList.remove(position);
        notifyItemRemoved(position);
        activity.saveData();
    }

}