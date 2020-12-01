package com.amtechnology.drugdosage;

import android.graphics.Typeface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReminderViewHolder extends RecyclerView.ViewHolder {
    TextView idmain,tvicon,tvtitle,datetime,remindertimes;
    RelativeLayout rlayout;
    public ReminderViewHolder(@NonNull View itemView) {
        super(itemView);
        rlayout = itemView.findViewById(R.id.rlayout);
        tvicon = itemView.findViewById(R.id.tvicon);
        tvtitle = itemView.findViewById(R.id.tvtitle);
        datetime = itemView.findViewById(R.id.datetime);
        idmain = itemView.findViewById(R.id.idmain);

    }
}
