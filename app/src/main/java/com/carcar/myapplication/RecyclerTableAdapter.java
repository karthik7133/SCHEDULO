package com.carcar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class RecyclerTableAdapter extends RecyclerView.Adapter<RecyclerTableAdapter.TableViewHolder> {

    private final ArrayList<TableModel> tableList;
    private final Context context;






    public RecyclerTableAdapter(Context context, ArrayList<TableModel> tableList) {
        this.tableList = tableList;
        this.context = context;


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
        holder.Place.setText(currentItem.getPlace().toUpperCase());
        holder.subjectTextView.setText(currentItem.getSubject());
        holder.startTimeTextView.setText("Start: " + currentItem.getStartTime() +"  TO  ");
        holder.endTimeTextView.setText("End: " + currentItem.getEndTime());


        // Apply fade-in animation
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .alpha(1f)
                .setDuration(500) // Animation duration in milliseconds
                .start();

        holder.itemView.setOnLongClickListener(view -> {
            showOptionsDialog(position);
            return true;
        });



    }

    private void showOptionsDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sure to delete")
                .setItems(new String[]{"No", "Delete"}, (dialog, which) -> {
                    if(which==0){
                        return;
                    }
                    if (which == 1) {
                        deleteItem(position);
                    }
                })
                .show();
    }

    private void deleteItem(int position) {
        if (position >= 0 && position < tableList.size()) {
            int requestCode = tableList.get(position).getRequestCode(); // Get request code safely
            UserDatabase db=UserDatabase.getInstance(context);

            String day=tableList.get(position).getDay();
            String corsecode=tableList.get(position).getSubject();
            cancelAlarm(requestCode); // Cancel the alarm before removing the item

            tableList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tableList.size()); // Prevents index shifting issues

            saveData(); // Persist changes in SharedPreferences
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Invalid item position", Toast.LENGTH_SHORT).show();
        }
    }




    private void saveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySchedulePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tableList);
        editor.putString("scheduleList", json);
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView, subjectTextView, startTimeTextView, endTimeTextView,Place;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            Place=itemView.findViewById(R.id.rowplace);
            dayTextView = itemView.findViewById(R.id.rowday);
            subjectTextView = itemView.findViewById(R.id.rowsub);
            startTimeTextView = itemView.findViewById(R.id.rowstarttime);
            endTimeTextView = itemView.findViewById(R.id.rowendtime);
        }
    }
    private void cancelAlarm(int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            //Toast.makeText(context, "Alarm canceled for requestCode: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }


}
