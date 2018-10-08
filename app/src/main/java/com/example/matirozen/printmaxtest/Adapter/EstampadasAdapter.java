package com.example.matirozen.printmaxtest.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matirozen.printmaxtest.Database.ModelDB.Cart;
import com.example.matirozen.printmaxtest.Interface.ItemClickListener;
import com.example.matirozen.printmaxtest.Model.Drink;
import com.example.matirozen.printmaxtest.R;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EstampadasAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

    Context context;
    List<Drink> drinkList;
    String[] material = {"fasco", "saten", "poliamida", "saten negro", "saten marfil", "saten autoadhesivo", "algodon"};
    String[] presentacion = {"rollo", "cortadas"};

    public EstampadasAdapter(Context context, List<Drink> drinkList) {
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
                .inflate(R.layout.estampadas_add_to_cart_layout, null);

        //View
        ImageView imgProductDialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        RadioButton rbMetros = (RadioButton)itemView.findViewById(R.id.rbMetros);
        RadioButton rbUnidades = (RadioButton)itemView.findViewById(R.id.rbUnidades);
        final EditText edtQty = (EditText)itemView.findViewById(R.id.edt_qty);
        TextView txtProductDialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        RadioButton fasco = (RadioButton)itemView.findViewById(R.id.fasco);
        RadioButton saten = (RadioButton)itemView.findViewById(R.id.saten);
        RadioButton poliamida = (RadioButton)itemView.findViewById(R.id.poliamida);
        RadioButton satenNegro = (RadioButton)itemView.findViewById(R.id.satenNegro);
        RadioButton satenMarfil = (RadioButton)itemView.findViewById(R.id.satenMarfil);
        RadioButton satenAutoadhesivo = (RadioButton)itemView.findViewById(R.id.satenAutoadhesivo);
        RadioButton algodon = (RadioButton)itemView.findViewById(R.id.algodon);
        Spinner spAncho = (Spinner) itemView.findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.estampadasAncho, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAncho.setAdapter(adapter);
        final EditText edtLargo = (EditText)itemView.findViewById(R.id.edtLargo);
        RadioButton unColor = (RadioButton)itemView.findViewById(R.id.unColor);
        RadioButton dosColores = (RadioButton)itemView.findViewById(R.id.dosColores);
        RadioButton tresColores = (RadioButton)itemView.findViewById(R.id.tresColores);
        RadioButton cuatroColores = (RadioButton)itemView.findViewById(R.id.cuatroColores);
        TextView txtProductPrice = (TextView)itemView.findViewById(R.id.txtProductPrice);
        txtProductPrice.setText(new StringBuilder("$").append(drinkList.get(position).price));
        rbMetros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrintmaxTestService.unidad = "Metros";
            }
        });
        rbUnidades.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrintmaxTestService.unidad = "Unidades";
            }
        });
        edtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PrintmaxTestService.cantidad = Integer.parseInt(edtQty.getText().toString());
            }
        });
        spAncho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PrintmaxTestService.ancho = (int) adapterView.getItemIdAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtLargo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PrintmaxTestService.largo = Integer.parseInt(edtLargo.getText().toString());
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
        satenMarfil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 4;
            }
        });
        satenAutoadhesivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 5;
            }
        });
        algodon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.material = 6;
            }
        });

        unColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrintmaxTestService.colores = 1;
            }
        });
        dosColores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrintmaxTestService.colores = 2;
            }
        });
        tresColores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrintmaxTestService.colores = 3;
            }
        });
        cuatroColores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PrintmaxTestService.colores = 4;
            }
        });
        RadioButton cortadas = (RadioButton)itemView.findViewById(R.id.cortadas);
        RadioButton rollo = (RadioButton)itemView.findViewById(R.id.rollo);
        rollo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.presentacion = 0;
            }
        });
        cortadas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    PrintmaxTestService.presentacion= 1;
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
                if(PrintmaxTestService.cantidad == -1){
                    Toast.makeText(context, "Ingrese metros/unidades", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.material == -1){
                    Toast.makeText(context, "Ingrese material", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.ancho == -1){
                    Toast.makeText(context, "Ingrese ancho", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.largo == -1){
                    Toast.makeText(context, "Ingrese largo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.colores == -1){
                    Toast.makeText(context, "Ingrese colores", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PrintmaxTestService.presentacion == -1){
                    Toast.makeText(context, "Ingrese formato", Toast.LENGTH_SHORT).show();
                    return;
                }

                showConfirmDialog(position, PrintmaxTestService.cantidad, PrintmaxTestService.unidad);
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void showConfirmDialog(final int position, final int cantidad, final String unidad) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout, null);

        //View
        ImageView imgProductDialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txtProductDialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txtProductPrice = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txtCantidad = (TextView)itemView.findViewById(R.id.txt_metros);
        TextView txtMaterial = (TextView)itemView.findViewById(R.id.txt_material);
        TextView txtTam = (TextView)itemView.findViewById(R.id.txtTam);
        TextView txtColores = (TextView)itemView.findViewById(R.id.txtColores);
        TextView txtPres = (TextView)itemView.findViewById(R.id.txtPres);

        Picasso.with(context).load(drinkList.get(position).link).into(imgProductDialog);
        txtProductDialog.setText(new StringBuilder(drinkList.get(position).name).toString());
        txtCantidad.setText(new StringBuilder().append(PrintmaxTestService.cantidad).append(" ").append(unidad).toString());
        txtColores.setText(new StringBuilder("Colores: ").append(PrintmaxTestService.colores));
        String mat = material[PrintmaxTestService.material];
        String pres = presentacion[PrintmaxTestService.presentacion];

        double price = (Double.parseDouble(drinkList.get(position).price)* cantidad);
        txtProductPrice.setText(new StringBuilder("$").append(price).toString());
        txtMaterial.setText(new StringBuilder("Material: ").append(mat));
        txtTam.setText(new StringBuilder(PrintmaxTestService.ancho).append(" mm x ").append(PrintmaxTestService.largo).append(" mm"));
        txtPres.setText(new StringBuilder("Presentacion: ").append(pres));

        final double finalPrice = price;
        builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                try {

                    //Add to SQLite
                    Cart cartItem = new Cart();
                    cartItem.name = txtProductDialog.getText().toString();
                    cartItem.cantidad = cantidad;
                    cartItem.unidad = unidad;
                    cartItem.material = PrintmaxTestService.material;
                    cartItem.ancho = PrintmaxTestService.ancho;
                    cartItem.largo = PrintmaxTestService.largo;
                    cartItem.colores = PrintmaxTestService.colores;
                    cartItem.presentacion = PrintmaxTestService.presentacion;
                    cartItem.price = finalPrice;
                    cartItem.link = drinkList.get(position).link;

                    //Add to DB
                    PrintmaxTestService.get().cartRepository.insertIntoCart(cartItem);
                    Log.d("MATIROZEN_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Enviado al carrito", Toast.LENGTH_SHORT).show();
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("MATIROZEN_DEBUG", ex.getMessage());
                }
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
