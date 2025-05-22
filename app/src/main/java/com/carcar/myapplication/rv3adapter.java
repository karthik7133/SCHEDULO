package com.carcar.myapplication;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class rv3adapter extends RecyclerView.Adapter<rv3adapter.myviewHOlder>{

    Context context;
    ArrayList<Daywiseschedule> arrayList=new ArrayList<>();;
    UserDatabase db;
    LoginDao l;
    ScheduleDao s;
    public rv3adapter(Context context,ArrayList<Daywiseschedule> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public rv3adapter.myviewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.datatbaseable,parent,false);
        return new rv3adapter.myviewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rv3adapter.myviewHOlder holder, int position) {
        holder.name.setText(arrayList.get(position).getDay());
        holder.password.setText(arrayList.get(position).getCorsecode());
        // Apply fade-in animation
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .alpha(1f)
                .setDuration(300) // Animation duration in milliseconds
                .start();

        holder.itemView.setOnLongClickListener(View->{
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

    private void deleteItem(int position){
        db=UserDatabase.getInstance(context);
        l=db.loginDao();
        s=db.scheduleDao();
        if (position >= 0 && position < arrayList.size()){
            String day = arrayList.get(position).getDay();
            String sub=arrayList.get(position).getCorsecode();
            s.deleteByDayAndCourseCode(day,sub);
            arrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, arrayList.size()); // Prevents index shifting issues
            Toast.makeText(context, "sub deleted :"+day, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Invalid item position", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class myviewHOlder extends RecyclerView.ViewHolder {
        TextView name,password;
        public myviewHOlder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.dbuname);
            password=itemView.findViewById(R.id.dbupassword);


        }
    }
}