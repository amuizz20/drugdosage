package com.amtechnology.drugdosage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText fullname,email,phoneno,password,healthcarerefno;
    ProgressDialog progressDialog;
    ImageView profilepic;
    TextView profileimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneno = findViewById(R.id.phoneno);
        healthcarerefno = findViewById(R.id.healthcare);
        profilepic = findViewById(R.id.profilepic);
        profileimg = findViewById(R.id.profileimg);
        setProfileimgbutton();
    }

    public void signupclick(View view) {
        InternetConnectivity internetConnectivity = new InternetConnectivity(this);
        if(internetConnectivity.isConnected()) {
            //Progress dialog for loading when sign up button is pressed
            progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (fullname.getText().toString().equals("") || email.getText().toString().equals("")|| !email.getText().toString().endsWith(".com") || !email.getText().toString().contains("@") ||
                    password.getText().toString().equals("") && phoneno.getText().toString().equals("")) {
                progressDialog.cancel();
                Toast.makeText(this, "Something Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
            }
            else if(healthcarerefno.length()!=6){
                progressDialog.cancel();
                Toast.makeText(this, "Health Care Ref no must be 6 digits", Toast.LENGTH_SHORT).show();

            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Map<String, String> usermap = new HashMap<>();
                                    usermap.put("Name", fullname.getText().toString());
                                    usermap.put("HealthCareRefno", healthcarerefno.getText().toString());
                                    usermap.put("Email", email.getText().toString());
                                    usermap.put("Phoneno", phoneno.getText().toString());
                                    usermap.put("profileimg",profileimg.getText().toString());
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
                                    ref.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.cancel();
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                            } else {
                                                progressDialog.cancel();
                                                Toast.makeText(getApplicationContext(), "Something Went Wrong! Please Try Again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    firebaseAuth.signOut();
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Error in Sign up", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        }
        else{
            Toast.makeText(this,"Network Not Available",Toast.LENGTH_SHORT).show();
        }

    }
    public void setProfileimgbutton(){
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select a File"),123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri locationoffile;
            locationoffile = data.getData();
            String name = getImageFilePath(locationoffile);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            final StorageReference storageReference = firebaseStorage.getReference().child(name);
            UploadTask uploadTask = storageReference.putFile(locationoffile);
            Task<Uri>urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        String text= task.getResult().toString();
                        profileimg.setText(text);
                        Glide.with(signup.this).load(profileimg.getText()).into(profilepic);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    profileimg.setText(e.toString());

                }
            });

        }


    }

    public String getImageFilePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
        String fileName = null;
        ContentResolver cr = this.getApplicationContext().getContentResolver();

        Cursor metaCursor = cr.query(uri,
                projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    fileName = metaCursor.getString(0);
                }
            } finally {
                metaCursor.close();
            }
        }
        return fileName;
    }
}
