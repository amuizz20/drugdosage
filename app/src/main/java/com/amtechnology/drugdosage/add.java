package com.amtechnology.drugdosage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class add extends Fragment {
    EditText etmedicine, etdosage;
    Spinner stime, sdays;
    Button addschedule;


    public add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_add, container, false);
        etmedicine = v.findViewById(R.id.title);
        etdosage = v.findViewById(R.id.dosageet);
        stime = v.findViewById(R.id.timespinner);
        sdays = v.findViewById(R.id.dayspinner);
        addschedule = v.findViewById(R.id.addschedulebutton);
        addschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
                if (!etmedicine.getText().equals("") && !etdosage.getText().equals("0")) {
                    SQLDatabase sqlDatabase = new SQLDatabase(getContext(), "DrugReminder", null, 1);
                    sqlDatabase.insertintotable(etmedicine.getText().toString(), etdosage.getText().toString(),
                            stime.getSelectedItem().toString(), sdays.getSelectedItem().toString(),sharedPreferences.getString("Email",null));
                    NotificationAlarm notificationAlarm = new NotificationAlarm(getContext());
                    String time = stime.getSelectedItem().toString();
                    String hour = "";
                    hour+=time.charAt(0);
                    hour+=time.charAt(1);
                    String am_pm ="";
                    am_pm+= time.charAt(6);
                    am_pm+=time.charAt(7);
                    if(sdays.getSelectedItem().toString().contains("Every Day")) {
                        notificationAlarm.setAlarm(Integer.parseInt(getyear()), Integer.parseInt(getMonth()), Integer.parseInt(getDate()), Integer.parseInt(hour), am_pm, "You have to Take " + etmedicine.getText().toString() + " Now",true);
                    }
                    else{
                        notificationAlarm.setAlarm(Integer.parseInt(getyear()), Integer.parseInt(getMonth()), Integer.parseInt(getDate()), Integer.parseInt(hour), am_pm, "You have to Take " + etmedicine.getText().toString() + " Now",false);
                    }
                    etdosage.setText("");
                    etmedicine.setText("");

                } else {
                    etdosage.setError("It can't be 0");
                }
            }
        });


        return v;
    }

    public String getyear() {
        // Getting the current date and formatting it
        SimpleDateFormat yearformat = new SimpleDateFormat("YYYY");
        Date currentdate = new Date();
        return yearformat.format(currentdate);
    }

    public String getMonth() {
        // Getting the current date and formatting it
        SimpleDateFormat monthformat = new SimpleDateFormat("MM");
        Date currentdate = new Date();
        return monthformat.format(currentdate);

    }

    public String getDate() {
        // Getting the current date and formatting it
        SimpleDateFormat dayformat = new SimpleDateFormat("dd");
        Date currentdate = new Date();
        return dayformat.format(currentdate);

    }
}