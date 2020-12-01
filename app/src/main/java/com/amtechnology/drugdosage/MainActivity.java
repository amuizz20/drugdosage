package com.amtechnology.drugdosage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    EditText etemail, etpassword;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etemail = findViewById(R.id.emaillogin);
        etpassword = findViewById(R.id.passwordlogin);
        etpassword.setText("");

        SharedPreferences sharedPreferences = this.getSharedPreferences("AutoLogin",Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("autologin",false)){
            etemail.setText(sharedPreferences.getString("Email",null));
            etpassword.setText(sharedPreferences.getString("Password",null));
            signinaction();
            Toast.makeText(this,"Auto Login Success",Toast.LENGTH_SHORT).show();
        }
        processIntentAction(getIntent());

    }

    public void SigninButton(View view) {
             signinaction();
    }
    public void signinaction(){
        InternetConnectivity internetConnectivity = new InternetConnectivity(this);
        if (internetConnectivity.isConnected()) {

            if (etemail.getText().toString().equals("") || etpassword.getText().toString().equals("")) {
                Toast.makeText(this, "Something Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
            } else {
                //Progress dialog when log in button is pressed
                progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
                progressDialog.setMessage("Authenticating...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(etemail.getText().toString(), etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Firebase authentication is successful
                            progressDialog.setMessage("Loading");

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Map<String,String>details = (Map<String, String>) ds.getValue();
                                        if(details.get("Email").toString().equals(etemail.getText().toString())) {
                                            SharedPreferences.Editor sharedPreferences = getApplicationContext().getSharedPreferences("AutoLogin",Context.MODE_PRIVATE).edit();
                                            sharedPreferences.putString("Email",etemail.getText().toString());
                                            sharedPreferences.putString("Password",etpassword.getText().toString());
                                            sharedPreferences.putBoolean("autologin",true);
                                            sharedPreferences.apply();
                                            Intent intent = new Intent(MainActivity.this, home.class);
                                            intent.putExtra("Name", details.get("Name"));
                                            intent.putExtra("ProfilePic", details.get("profileimg"));
                                            startActivity(intent);
                                            break;
                                        }

                                    }
                                    finish();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        } else {
                            firebaseAuth.signOut();
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(), "Email or Password is Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(this, "Network not Available", Toast.LENGTH_SHORT).show();
        }

    }

    public void SignupButton(View view) {
        Intent intent = new Intent(MainActivity.this, signup.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntentAction(intent);
        super.onNewIntent(intent);
    }

    private void processIntentAction(Intent intent) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("Counter",Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferences = this.getSharedPreferences("Counter",Context.MODE_PRIVATE).edit();
   try {
    if (intent.getExtras() != null) {
        if (intent.getExtras().getString("MainActivity").contains("1")) {
            int notificationcount = sharedPreferences1.getInt("TakenCount", 0);
            sharedPreferences.putInt("TakenCount", ++notificationcount);
            sharedPreferences.apply();

        } else if(intent.getExtras().getString("MainActivity").contains("2")){
            int notificationcount = sharedPreferences1.getInt("NotTakenCount", 0);
            sharedPreferences.putInt("NotTakenCount", ++notificationcount);
            sharedPreferences.apply();

        }

    }
}catch (Exception e){
    //do nothing
}
    }
    }
