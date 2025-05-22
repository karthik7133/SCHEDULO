package com.carcar.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.TableViewHolder> {

    private final ArrayList<TableModel> tableList;

    public Adapter2(ArrayList<TableModel> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetablerow, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        TableModel currentItem = tableList.get(position);

        holder.dayTextView.setText(currentItem.getDay());
        holder.placeTextView.setText(currentItem.getPlace().toUpperCase());
        holder.subjectTextView.setText(currentItem.getSubject());
        holder.startTimeTextView.setText("Start: " + currentItem.getStartTime() + "  TO  ");
        holder.endTimeTextView.setText("End: " + currentItem.getEndTime());



        // Slide from left animation
        holder.itemView.setTranslationX(-200);
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .translationX(0)
                .alpha(1f)
                .setDuration(600)
                .start();
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView, subjectTextView, startTimeTextView, endTimeTextView, placeTextView;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            placeTextView = itemView.findViewById(R.id.rowplace);
            dayTextView = itemView.findViewById(R.id.rowday);
            subjectTextView = itemView.findViewById(R.id.rowsub);
            startTimeTextView = itemView.findViewById(R.id.rowstarttime);
            endTimeTextView = itemView.findViewById(R.id.rowendtime);
        }
    }
}
