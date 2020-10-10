package com.example.bugerbump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    TextInputLayout prousername, proemail, prophoneNO, propassword;
    Button  prodelete, aleart,proupdate;
    String _username,_email,_phoneNo,_password;
    DatabaseReference reference;




    ImageView signoutLogo, aleartimg;
    Dialog dialog;


    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        prousername = findViewById(R.id.pro_username);
        proemail = findViewById(R.id.pro_email);
        prophoneNO = findViewById(R.id.pro_pnum);
        propassword = findViewById(R.id.pro_password);
        proupdate=findViewById(R.id.pro_update);


        signoutLogo = findViewById(R.id.signoutlogo);
        prodelete = findViewById(R.id.pro_delete);

        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);

        proupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("users").child(_phoneNo);
                String upUsername,upEmail,upPhoneNo,upPassword;
                upUsername=prousername.getEditText().getText().toString();
                upEmail=proemail.getEditText().getText().toString();
                upPhoneNo=prophoneNO.getEditText().getText().toString();
                upPassword=propassword.getEditText().getText().toString();
              Helper helper=new Helper(upUsername,upEmail,upPhoneNo,upPassword);
              databaseReference.setValue(helper);
                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });





        prodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showprodelete();

            }
        });
        signoutLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(profile.this, userlogin.class));
            }
        });


        showUserData();
    }

    private void showprodelete() {
        dialog.setContentView(R.layout.deletealert);
        aleartimg = (ImageView) dialog.findViewById(R.id.wrong);
        aleart = (Button) dialog.findViewById(R.id.deletealeart);
        aleartimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        aleart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference=FirebaseDatabase.getInstance().getReference("users").child(_phoneNo);
                reference.removeValue();


                startActivity(new Intent(profile.this, userlogin.class));
                dialog.dismiss();
            }
        });
    }


    private void showUserData() {
        Intent intent = getIntent();
        _username = intent.getStringExtra("username");
        _email = intent.getStringExtra("email");
         _phoneNo = intent.getStringExtra("phoneNo");
         _password = intent.getStringExtra("password");
        prousername.getEditText().setText(_username);
        proemail.getEditText().setText(_email);
        prophoneNO.getEditText().setText(_phoneNo);
        propassword.getEditText().setText(_password);




    }






}