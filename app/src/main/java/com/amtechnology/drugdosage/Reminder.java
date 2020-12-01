package com.amtechnology.drugdosage;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reminder extends Fragment {
    ImageView profilepic;
    TextView profilename;
    View v;



    public Reminder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_reminder, container, false);
        loaditems();

    return v;

    }
    public void loaditems(){
        RecyclerView mRecyclerview = v.findViewById(R.id.recyclerN);
        profilename = v.findViewById(R.id.profilename);
        profilepic = v.findViewById(R.id.profilepic);
        Glide.with(getContext()).load(home.ProfilePic).dontAnimate().into(profilepic);
        profilename.setText(home.ProfileName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        SQLDatabase sqlDatabase = new SQLDatabase(getActivity(),"DrugReminder",null,1);

        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
        ArrayList<ReminderDetails> taskDetails = sqlDatabase.GetAllData(sharedPreferences.getString("Email",null));

        ReminderAdapter reminderAdapter = new ReminderAdapter(getActivity(), taskDetails);
        mRecyclerview.setAdapter(reminderAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        loaditems();
    }

}


