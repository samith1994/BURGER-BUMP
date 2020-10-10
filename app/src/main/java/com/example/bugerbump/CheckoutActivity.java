package com.example.bugerbump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity
{
    Toolbar toolbar;

    private String foodName, foodPrice, foodQty, TotalPrice;
    private TextView viewFoodName, viewFoodPrice, viewFoodSubTotal, viewFoodTotal;
    private Button orderBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");


        Intent intent = getIntent();
        foodName =intent.getStringExtra("foodName");
        foodPrice =intent.getStringExtra("foodPrice");
        foodQty =intent.getStringExtra("qty");


        viewFoodName =findViewById(R.id.checkout_food_name);
        viewFoodPrice = findViewById(R.id.checkout_food_price);
        viewFoodSubTotal =findViewById(R.id.checkout_sub_total);
        viewFoodTotal = findViewById(R.id.checkout_total);
        orderBtn = findViewById(R.id.order_button);




        
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),DeliveryDetails.class);
                startActivity(i);

            }
        });


        //displaying food data from the database
        DisplayingFoodData();
    }

    //getting food data from the database
    private void DisplayingFoodData()
    {
       viewFoodName.setText(foodName);
       TotalPrice = String.valueOf(Integer.parseInt(foodQty) * Integer.parseInt(foodPrice));
       viewFoodPrice.setText("Rs "+TotalPrice);
       viewFoodSubTotal.setText("Rs "+TotalPrice);
       viewFoodTotal.setText("Rs "+TotalPrice);
    }
}
