package com.example.bugerbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartViewActivity extends AppCompatActivity
{
    private Toolbar toolbar;

    private RecyclerView foodList;
    private DatabaseReference cartsDatabaseReference, deleteCartDatabaseReference;

    private String _phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");

        Intent intent=getIntent();
        _phoneNo=intent.getStringExtra("phoneNo");

        foodList = findViewById(R.id.cart_food_list);
        cartsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child("7777");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //define foods order
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        foodList.setLayoutManager(linearLayoutManager);

        LoadCartFoods();
    }

    private void LoadCartFoods()
    {

      FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Cart>()
              .setQuery(cartsDatabaseReference, Cart.class)
              .build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart)
            {
                final String cartID = getRef(i).getKey();

                cartViewHolder.foodName.setText(cart.foodName);
                cartViewHolder.foodPrice.setText("Rs "+cart.foodPrice);
                cartViewHolder.foodQty.setText("Qty : "+cart.quantity);

                cartViewHolder.removeBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        deleteCartDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child("7777").child(cartID);
                        deleteCartDatabaseReference.removeValue();
                    }
                });

                cartViewHolder.buyBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent checkoutIntent = new Intent(CartViewActivity.this, CheckoutActivity.class);
                        checkoutIntent.putExtra("foodPrice", cart.foodPrice);
                        checkoutIntent.putExtra("qty",cart.quantity);
                        checkoutIntent.putExtra("foodName",cart.foodName);
                        startActivity(checkoutIntent);
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
                CartViewHolder cartViewHolder = new CartViewHolder(view);
                return  cartViewHolder;
            }
        };

        foodList.setAdapter(adapter);
        adapter.startListening();

    }

    public  static class CartViewHolder extends  RecyclerView.ViewHolder
    {
        TextView foodName, foodPrice, foodQty, removeBtn, buyBtn;

        public CartViewHolder(@NonNull View itemView)
        {
            super(itemView);

            foodName =itemView.findViewById(R.id.cart_food_name);
            foodPrice =itemView.findViewById(R.id.cart_food_price);
            foodQty =itemView.findViewById(R.id.cart_food_qty);
            removeBtn = itemView.findViewById(R.id.cart_food_remove_btn);
            buyBtn = itemView.findViewById(R.id.cart_food_buy_btn);
        }
    }
}


