package com.example.bugerbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment extends AppCompatActivity {
    Button btnPay, btnCancel;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnPay = findViewById(R.id.btnPay);
        btnCancel = findViewById(R.id.btnCancelPayment);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Shipping");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentdone();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentCancel();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void paymentdone() {
        Intent i = new Intent(Payment.this,MainActivity.class); //ENTER HOME PAGE TO HERE!!!
        Toast.makeText(Payment.this, "Payment Successful", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    public void paymentCancel() {
        Intent intent = new Intent(Payment.this, DeliveryDisplay.class);
        startActivity(intent);
    }
}