package com.example.bugerbump;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryDetails extends AppCompatActivity {

    Button btnConfirm, btnCancel,profileBtn,cartBtn;
    Toolbar toolbar;
    EditText Fname, Lname, Address, ContactNo, Email;
    Shipping shipping;
    DatabaseReference dbRef;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Fname = findViewById(R.id.editTextPersonFirstName);
        Lname = findViewById(R.id.editTextPersonSecondName);
        Address = findViewById(R.id.editTextAddress);
        ContactNo = findViewById(R.id.editTextNumber);
        Email = findViewById(R.id.editTextEmailAddress);

        shipping = new Shipping();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Shipping");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    i = (int) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDeliveryDisplay();

                if (TextUtils.isEmpty(Fname.getText().toString())) {
                    Toast.makeText(DeliveryDetails.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Lname.getText().toString())) {
                    Toast.makeText(DeliveryDetails.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Address.getText().toString())) {
                    Toast.makeText(DeliveryDetails.this, "Enter Address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ContactNo.getText().toString())) {
                    Toast.makeText(DeliveryDetails.this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Email.getText().toString())) {
                    Toast.makeText(DeliveryDetails.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else {
                    shipping.setFirstName(Fname.getText().toString().trim());
                    shipping.setLastName(Lname.getText().toString().trim());
                    shipping.setAddress(Address.getText().toString().trim());
                    shipping.setPhoneNumber(ContactNo.getText().toString().trim());
                    shipping.setEmail(Email.getText().toString().trim());

                    dbRef.push().setValue(shipping);
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToHomePage();
            }
        });

    }

    public void openDeliveryDisplay() {
        Intent i = new Intent(getApplicationContext(), DeliveryDisplay.class);
        startActivity(i);
    }

    public void returnToHomePage() {

    }
}