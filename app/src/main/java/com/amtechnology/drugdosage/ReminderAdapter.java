package com.amtechnology.drugdosage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    private List<ReminderDetails> mtaskdetails;
    private Context mContext;
    public ReminderAdapter(Context mContext, ArrayList<ReminderDetails> taskdetails){
        this.mContext = mContext;
        this.mtaskdetails = taskdetails;

    }


    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_reminder,parent
        ,false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, final int position) {
        holder.idmain.setText(mtaskdetails.get(position).getId());
        holder.datetime.setText(mtaskdetails.get(position).getTime());
        holder.tvtitle.setText(mtaskdetails.get(position).getMedicine());
        int randomNum = (int) (Math.random() * ((1) + 1));
        if(randomNum>=1){
            holder.tvicon.setBackgroundResource(R.mipmap.pill1);

        }
        else{
            holder.tvicon.setBackgroundResource(R.mipmap.pill2);

        }

        holder.rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MedicineRecord.class);
                intent.putExtra("Medicine Name",mtaskdetails.get(position).getMedicine());
                intent.putExtra("Time",mtaskdetails.get(position).getTime());
                intent.putExtra("Dosage",mtaskdetails.get(position).getDosage());
                intent.putExtra("ReminderTimes",mtaskdetails.get(position).getDays());
                intent.putExtra("id",mtaskdetails.get(position).getId());
                mContext.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mtaskdetails.size();
    }
}
