package com.example.matirozen.printmaxtest.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matirozen.printmaxtest.Interface.ItemClickListener;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.R;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

    Context context;
    List<Drink> drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_layout, null);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, final int position) {
        holder.txt_price.setText(new StringBuilder("$").append(drinkList.get(position).price).toString());
        holder.txt_drink_name.setText(drinkList.get(position).name);
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddToCartDialog(position);
            }
        });
        Picasso.with(context)
                .load(drinkList.get(position).link)
                .into(holder.img_product);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddToCartDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout, null);

        //View
        ImageView imgProductDialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        final EditText edtQty = (EditText)itemView.findViewById(R.id.edt_qty);
        TextView txtProductDialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        RadioButton fasco = (RadioButton)itemView.findViewById(R.id.fasco);
        RadioButton saten = (RadioButton)itemView.findViewById(R.id.saten);
        RadioButton poliamida = (RadioButton)itemView.findViewById(R.id.poliamida);
        RadioButton satenNegro = (RadioButton)itemView.findViewById(R.id.satenNegro);
        TextView txtProductPrice = (TextView)itemView.findViewById(R.id.txtProductPrice);
        txtProductPrice.setText(new StringBuilder("$").append(drinkList.get(position).price));
        edtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PrintmaxTestService.metros = Integer.parseInt(edtQty.getText().toString());
            }
        });
        fasco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 0;
            }
        });
        saten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 1;
            }
        });
        poliamida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 2;
            }
        });
        satenNegro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 3;
            }
        });

        RadioButton cortadas = (RadioButton)itemView.findViewById(R.id.cortadas);
        RadioButton rollo = (RadioButton)itemView.findViewById(R.id.rollo);
        rollo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.formato = 0;
            }
        });
        cortadas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.formato = 1;
            }
        });


        Picasso.with(context)
                .load(drinkList.get(position).link)
                .into(imgProductDialog);
        txtProductDialog.setText(drinkList.get(position).name);

        builder.setView(itemView);
        builder.setNegativeButton("Add to cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(PrintmaxTestService.metros == -1){
                    Toast.makeText(context, "Please enter metros", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.material == -1){
                    Toast.makeText(context, "Please enter material", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.formato == -1){
                    Toast.makeText(context, "Please enter formato", Toast.LENGTH_SHORT).show();
                    return;
                }

                showConfirmDialog(position, PrintmaxTestService.metros, PrintmaxTestService.material, PrintmaxTestService.formato);
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void showConfirmDialog(int position, int metros, int material, int formato) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout, null);

        //View
        ImageView imgProductDialog = (ImageView)itemView.findViewById(R.id.img_product);
        TextView txtProductDialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txtProductPrice = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txtMetros = (TextView)itemView.findViewById(R.id.txt_metros);
        TextView txtMaterial = (TextView)itemView.findViewById(R.id.txt_material);
        TextView txtFormato = (TextView)itemView.findViewById(R.id.txt_formato);

        Picasso.with(context).load(drinkList.get(position).link).into(imgProductDialog);
        txtProductDialog.setText(new StringBuilder(drinkList.get(position).name).toString());
        txtMetros.setText(new StringBuilder().append(PrintmaxTestService.metros).append(" metros").toString());
        String mat = "";
        String form = "";

        double price = (Double.parseDouble(drinkList.get(position).price)* metros);
        switch (PrintmaxTestService.material){
            case 0:
                mat = "fasco";
            case 1:
                mat = "Saten";
                price *= 2;
                break;
            case 2:
                mat = "poliamida";
                price *= 4;
                break;
            case 3:
                mat = "Saten negro";
                price *= 6;
                break;
        }
        switch (PrintmaxTestService.formato){
            case 0:
                form = "Rollo";
            case 1:
                form = "Cortadas";
                price *= 2;
        }
        txtProductPrice.setText(new StringBuilder("$").append(price).toString());
        txtMaterial.setText(new StringBuilder("Material: ").append(mat));
        txtFormato.setText(new StringBuilder("Formato: ").append(form));

        builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Add to SQLite
                dialogInterface.dismiss();
            }
        });

        builder.setView(itemView);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
