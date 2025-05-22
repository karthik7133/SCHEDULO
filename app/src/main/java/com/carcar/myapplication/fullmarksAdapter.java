package com.carcar.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fullmarksAdapter extends RecyclerView.Adapter<fullmarksAdapter.TableViewHolder> {
    private final ArrayList<Marksmodel> marksmodelArrayList;

    // Constructor
    public fullmarksAdapter(ArrayList<Marksmodel> marksmodelArrayList) {
        this.marksmodelArrayList = marksmodelArrayList != null ? marksmodelArrayList : new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return marksmodelArrayList.size();
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marksrow, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        // Get the current item from the list
        Marksmodel currentItem = marksmodelArrayList.get(position);

        // Bind data to the views
        holder.subjectTextView.setText(currentItem.getSubject());
        holder.marksTextView.setText(currentItem.getMarks());
        holder.GradeTextView.setText(currentItem.getGrade());

        // Apply fade-in animation
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .alpha(1f)
                .setDuration(600) // Animation duration in milliseconds
                .start();
    }

    // ViewHolder class
    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView, marksTextView, GradeTextView;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            subjectTextView = itemView.findViewById(R.id.rowsubject);
            marksTextView = itemView.findViewById(R.id.rowmarks);
            GradeTextView = itemView.findViewById(R.id.rowgrade);

            // Debugging: Check if any TextView is null
            if (subjectTextView == null || marksTextView == null || GradeTextView == null) {
                throw new IllegalStateException("One or more TextViews are null. Check your layout file (marksrow.xml).");
            }
        }
    }
}