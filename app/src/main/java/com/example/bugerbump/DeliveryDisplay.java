package com.example.bugerbump;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryDisplay extends AppCompatActivity {
    private static final String TAG = "DeliveryDisplay";
    EditText Fname, Lname, Address, ContactNo, Email;
    Button btnUpdate, btnPayment;
    DatabaseReference upRef;
    Shipping shipping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_display);

        Fname = findViewById(R.id.editTextFirstPersonNameDD);
        Lname = findViewById(R.id.editTextSecondPersonNameDD);
        Address = findViewById(R.id.editTextAddressDD);
        ContactNo = findViewById(R.id.editTextNumberDD);
        Email = findViewById(R.id.editTextEmailAddressDD);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnPayment = (Button) findViewById(R.id.btnPayment);

        upRef = FirebaseDatabase.getInstance().getReference().child("Shipping").child("-MJ7KzkRccYj3hwRbd1K");
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i(TAG, "onDataChange");

                shipping = dataSnapshot.getValue(Shipping.class);
                Log.i(TAG, "onDataChange: shipping =" + shipping.getFirstName());
                Fname.setText(shipping.getFirstName());
                Lname.setText(shipping.getLastName());
                Address.setText(shipping.getAddress());
                ContactNo.setText(shipping.getPhoneNumber());
                Email.setText(shipping.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentNavigation();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipping = new Shipping();

                shipping.setFirstName(Fname.getText().toString().trim());
                shipping.setLastName(Lname.getText().toString().trim());
                shipping.setAddress(Address.getText().toString().trim());
                shipping.setPhoneNumber(ContactNo.getText().toString().trim());
                shipping.setEmail(Email.getText().toString().trim());

                upRef.setValue(shipping);
                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void PaymentNavigation () {
        Intent intent = new Intent(getApplicationContext(), Payment.class);
        startActivity(intent);
    }
}