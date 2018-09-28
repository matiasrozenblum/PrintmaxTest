package com.example.matirozen.printmaxtest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;
import com.example.matirozen.printmaxtest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.imgProduct);
        holder.txtProductName.setText(cartList.get(position).name);
        holder.txtPrice.setText(new StringBuilder("$").append(cartList.get(position).price));
        holder.txtDetails.setText(new StringBuilder("Metros: ")
                .append(cartList.get(position).metros).append("\n")
                .append("Material:").append(cartList.get(position).material).append("\n")
                .append("Formato:").append(cartList.get(position).formato).append("\n").toString());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtProductName, txtDetails, txtPrice;

        public CartViewHolder(View itemView){
            super(itemView);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtDetails = (TextView)itemView.findViewById(R.id.txtDetails);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
        }
    }
}
