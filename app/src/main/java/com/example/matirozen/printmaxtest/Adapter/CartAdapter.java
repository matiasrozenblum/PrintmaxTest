package com.example.matirozen.printmaxtest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;
import com.example.matirozen.printmaxtest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    Context context;
    List<Cart> cartList;
    String[] material = {"fasco", "saten", "poliamida", "poliamida eco", "saten negro", "saten marfil", "saten autoadhesivo", "algodon", "alta definicion", "tafeta"};
    String[] presentacion = {"rollo", "cortadas"};

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
        holder.txtProductName.setText(cartList.get(position).etiqueta);
        holder.txtPrice.setText(new StringBuilder("$").append(cartList.get(position).price));
        holder.txtDetails.setText(new StringBuilder("Cantidad: ")
                .append(cartList.get(position).cantidad).append(" ").append(cartList.get(position).unidad).append("\n")
                .append("Material: ").append(material[cartList.get(position).material]).append("\n")
                .append("Tamaño: ").append(cartList.get(position).ancho).append(" mm x ").append(cartList.get(position).largo).append(" mm").append("\n")
                .append("Colores: ").append(cartList.get(position).colores).append("\n")
                .append("Presentacion: ").append(presentacion[cartList.get(position).presentacion]).append("\n").toString());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtProductName, txtDetails, txtPrice;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;

        public CartViewHolder(View itemView){
            super(itemView);
            imgProduct = (ImageView)itemView.findViewById(R.id.imgProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtDetails = (TextView)itemView.findViewById(R.id.txtDetails);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewBackground);
            viewForeground = (LinearLayout)itemView.findViewById(R.id.viewForeground);
        }
    }
    public void removeItem(int position){
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position){
        cartList.add(position, item);
        notifyItemInserted(position);
    }
}
