package com.carcar.myapplication;


import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.TableViewHolder> {
    private final ArrayList<Marksmodel> marksmodelArrayList;
    private final Context con;

    public Adapter3(Context con,ArrayList<Marksmodel> marksmodelArrayList) {
        this.marksmodelArrayList = marksmodelArrayList;
        this.con=con;
    }

    @NonNull
    @Override
    public Adapter3.TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marksrow, parent, false);
        return new Adapter3.TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter3.TableViewHolder holder, int position) {
        Marksmodel currentItem = marksmodelArrayList.get(position);

        holder.Subject.setText(currentItem.getSubject().toUpperCase());
        holder.Grade.setText("GRADE :"+currentItem.getGrade().toUpperCase());
        holder.Marks.setText("MARKS :"+currentItem.getMarks());



        // Scale animation
        holder.itemView.setScaleX(0.8f);
        holder.itemView.setScaleY(0.8f);
        holder.itemView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .start();

        holder.itemView.setOnLongClickListener(View ->{
            showdialoguebox(position);
            return true;
        });


    }
    public void showdialoguebox(int pos){
        AlertDialog.Builder b=new AlertDialog.Builder(con);
        b.setTitle("Select Operation:")
                .setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
                    if(which==0){
                        update(pos);
                    }
                    if (which == 1) {
                        deleteItem(pos);
                    }
                })
                .show();
    }


    public void deleteItem(int pos) {
        if (pos >= 0 && pos < marksmodelArrayList.size()) {
            marksmodelArrayList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, marksmodelArrayList.size()); // Updates positions to prevent index issues
            saveData();
            Toast.makeText(con, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(con, "Invalid item position", Toast.LENGTH_SHORT).show();
        }
    }


    public void update(int pos){
        Marksmodel currentItem =marksmodelArrayList.get(pos);

        View view=LayoutInflater.from(con).inflate(R.layout.dialog_edit_item,null);
        EditText sub=view.findViewById(R.id.editSubject);
        EditText marks=view.findViewById(R.id.editmarks);
        EditText Grade=view.findViewById(R.id.editGrade);
        Button b=view.findViewById(R.id.update_button);

        sub.setText(currentItem.getSubject());
        marks.setText(currentItem.getMarks());
        Grade.setText(currentItem.getGrade());

        AlertDialog.Builder builder=new AlertDialog.Builder(con);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();


        b.setOnClickListener(v -> {
            String updatedSubject = sub.getText().toString().trim().toUpperCase();
            String updatedMarks = marks.getText().toString().trim();
            String updatedGrade = Grade.getText().toString().trim().toUpperCase();
            int reqcode =Integer.parseInt(updatedMarks);



            if (!updatedSubject.isEmpty() && !updatedMarks.isEmpty() && !updatedGrade.isEmpty()) {

                if(Integer.parseInt(updatedMarks)<=100 && isGrade(updatedGrade)){
                    // Update the ArrayList
                    marksmodelArrayList.set(pos, new Marksmodel(updatedSubject, updatedGrade, updatedMarks,reqcode));
                    notifyItemChanged(pos);
                    saveData();
                    Toast.makeText(con, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // Close the dialog

                }else{
                    Toast.makeText(con, "Enter correct things not other bullshit you  know", Toast.LENGTH_SHORT).show();
                    Toast.makeText(con, "Enter marks under 100 and check grade", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(con, "All fields are required!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveData() {
        SharedPreferences sharedPreferences = con.getSharedPreferences("MyMarkspreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(marksmodelArrayList);
        editor.putString("MarksList", json);
        editor.apply();
    }
    @Override
    public int getItemCount() {
        return marksmodelArrayList.size();
    }
    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView Subject, Marks, Grade;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            Subject = itemView.findViewById(R.id.rowsubject);
            Marks = itemView.findViewById(R.id.rowmarks);
            Grade = itemView.findViewById(R.id.rowgrade);

        }
    }
    public boolean isGrade(String c){
        String carray []={"A","B","C","D","E","F","S"};
        boolean found = false;
        String  target=c;
        for (String d : carray) {
            if (d.equalsIgnoreCase(target)) {
                found = true;
                break;
            }
        }

        return found;
    }
}
