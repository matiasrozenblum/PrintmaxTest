package com.example.matirozen.printmaxtest.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import com.example.matirozen.printmaxtest.HomeActivity;
import com.example.matirozen.printmaxtest.Interface.ItemClickListener;
import com.example.matirozen.printmaxtest.Model.Tag;
import com.example.matirozen.printmaxtest.Model.Price;
import com.example.matirozen.printmaxtest.R;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.example.matirozen.printmaxtest.Utils.Listener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeAdapter extends RecyclerView.Adapter<TagViewHolder> {
    Context context;
    List<Tag> tagList;
    String[] material = {"fasco", "saten", "poliamida", "poliamida eco", "saten negro", "saten marfil", "saten autoadhesivo", "algodon", "alta definicion", "tafeta"};
    String[] codigo = {"f", "s", "p", "pe", "sn", "sm", "sa", "al", "ad", "tf"};
    String[] presentacion = {"rollo", "cortadas", "cortadas y dobladas"};
    int[] anchoBordadas = {12, 14, 16, 20, 25, 40, 50, 65, 80, 99};
    int[] anchoEstampadas = {10, 15, 20, 25, 30, 35, 40, 50};
    private static final int ESTAMPADAS = 0;
    private static final int BORDADAS = 1;
    private Listener listener;
    float cantUnidades;
    float cantMetros;

    public TypeAdapter(Context context, List<Tag> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.tag_item_layout, null);
        return new TagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, final int position) {
        if(position == 0){
            holder.txt_tag_name.setText(tagList.get(position).name);
            holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    estampadasShowAddToCartDialog(position, false);
                }
            });
            Picasso.with(context)
                    .load(tagList.get(position).link)
                    .into(holder.img_product);
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(position == 1){
            holder.txt_tag_name.setText(tagList.get(position).name);
            holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bordadasShowAddToCartDialog(position, false);
                }
            });
            Picasso.with(context)
                    .load(tagList.get(position).link)
                    .into(holder.img_product);
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    private void estampadasShowAddToCartDialog(final int position, boolean volver) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.estampadas_add_to_cart_layout, null);

        //View
        ImageView imgProductDialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        RadioButton rbMetros = (RadioButton)itemView.findViewById(R.id.rbMetros);
        RadioButton rbUnidades = (RadioButton)itemView.findViewById(R.id.rbUnidades);
        final EditText edtQty = (EditText)itemView.findViewById(R.id.edt_qty);
        TextView txtProductDialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        Spinner spMaterial = (Spinner) itemView.findViewById(R.id.spMaterial);
        final ArrayAdapter<CharSequence> materialAdapter = ArrayAdapter.createFromResource(
                context, R.array.estampadasMaterial, android.R.layout.simple_spinner_item);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaterial.setAdapter(materialAdapter);
        Spinner spAncho = (Spinner) itemView.findViewById(R.id.spAncho);
        final ArrayAdapter<CharSequence> anchoAdapter = ArrayAdapter.createFromResource(
                context, R.array.estampadasAncho, android.R.layout.simple_spinner_item);
        anchoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAncho.setAdapter(anchoAdapter);
        final EditText edtLargo = (EditText)itemView.findViewById(R.id.edtLargo);
        Spinner spColores = (Spinner) itemView.findViewById(R.id.spColores);
        final ArrayAdapter<CharSequence> coloresAdapter = ArrayAdapter.createFromResource(
                context, R.array.colores, android.R.layout.simple_spinner_item);
        coloresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColores.setAdapter(coloresAdapter);
        Spinner spPresentacion = (Spinner) itemView.findViewById(R.id.spPresentacion);
        final ArrayAdapter<CharSequence> presAdapter = ArrayAdapter.createFromResource(
                context, R.array.presentacion, android.R.layout.simple_spinner_item);
        presAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(presAdapter);
        if(volver){
            if(PrintmaxTestService.unidad == "Metros"){
                rbMetros.setChecked(true);
                rbUnidades.setChecked(false);
            } else {
                rbMetros.setChecked(false);
                rbUnidades.setChecked(true);
            }

            edtQty.setText(String.valueOf(PrintmaxTestService.cantidad));
            spAncho.setSelection(Arrays.binarySearch(anchoBordadas, PrintmaxTestService.ancho));
            edtLargo.setText(String.valueOf(PrintmaxTestService.largo));
            spMaterial.setSelection(PrintmaxTestService.material);
            spColores.setSelection(PrintmaxTestService.colores-1);
            spPresentacion.setSelection(PrintmaxTestService.presentacion);
        } else {
            PrintmaxTestService.unidad = "Metros";
            PrintmaxTestService.material = 0;
            PrintmaxTestService.colores = 1;
            PrintmaxTestService.presentacion = 0;
        }

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
                if (edtQty.getText().toString() != "" && edtQty.getText().toString() != "0") {
                    PrintmaxTestService.cantidad = Integer.parseInt(edtQty.getText().toString());
                }
            }
        });

        spAncho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PrintmaxTestService.ancho = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
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

        spMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PrintmaxTestService.material = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PrintmaxTestService.colores = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PrintmaxTestService.presentacion = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Picasso.with(context)
                .load(tagList.get(position).link)
                .into(imgProductDialog);
        txtProductDialog.setText(tagList.get(position).name);

        builder.setView(itemView);
        builder.setNegativeButton("Añadir al carrito", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
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
                String cod = new StringBuilder(codigo[PrintmaxTestService.material]).append(PrintmaxTestService.ancho).toString();
                PrintmaxTestService.get().getPrice(cod).enqueue(new Callback<Price>() {
                    @Override
                    public void onResponse(Call<Price> call, Response<Price> response) {
                        if (PrintmaxTestService.unidad == "Unidades") {
                            cantUnidades = PrintmaxTestService.cantidad;
                            PrintmaxTestService.cantidad *= (PrintmaxTestService.largo *1000);
                            cantMetros = PrintmaxTestService.cantidad;
                        } else {
                            cantMetros = PrintmaxTestService.cantidad;
                            cantUnidades = ((float) PrintmaxTestService.cantidad / PrintmaxTestService.largo) *1000;
                        }
                        if (PrintmaxTestService.cantidad >= 10000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getprecioe());
                        } else if (PrintmaxTestService.cantidad >= 5000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getpreciod());
                        } else if (PrintmaxTestService.cantidad >= 3000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getprecioc());
                        } else if (PrintmaxTestService.cantidad >= 1000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getpreciob());
                        } else {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getprecioa());
                        }

                        PrintmaxTestService.price *=  PrintmaxTestService.cantidad;

                        if(PrintmaxTestService.colores == 2){
                            PrintmaxTestService.price += PrintmaxTestService.price / 10;
                        } else if(PrintmaxTestService.colores == 3){
                            PrintmaxTestService.price += (PrintmaxTestService.price / 100)*18;
                        } else if (PrintmaxTestService.colores == 4){
                            PrintmaxTestService.price += PrintmaxTestService.price / 4;
                        }

                        if(PrintmaxTestService.presentacion == 1 && PrintmaxTestService.presentacion == 2){
                            PrintmaxTestService.price += (PrintmaxTestService.price / 100)*15;
                        }

                        if(PrintmaxTestService.price < 3000){
                            PrintmaxTestService.price = 3000;
                        }
                        PrintmaxTestService.priceMetro = PrintmaxTestService.price / cantMetros;
                        PrintmaxTestService.priceUnidad = PrintmaxTestService.price / cantUnidades;
                        showConfirmDialog(position, PrintmaxTestService.cantidad, PrintmaxTestService.unidad);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Price> call, Throwable t) {
                        String price = "a";
                    }
                });
            }
        });

        builder.show();

    }

    private void bordadasShowAddToCartDialog(final int position, boolean volver) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.bordadas_add_to_cart_layout, null);

        //View
        ImageView imgProductDialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        RadioButton rbMetros = (RadioButton)itemView.findViewById(R.id.rbMetros);
        RadioButton rbUnidades = (RadioButton)itemView.findViewById(R.id.rbUnidades);
        final EditText edtQty = (EditText)itemView.findViewById(R.id.edt_qty);
        TextView txtProductDialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        Spinner spMaterial = (Spinner) itemView.findViewById(R.id.spMaterial);
        final ArrayAdapter<CharSequence> materialAdapter = ArrayAdapter.createFromResource(
                context, R.array.bordadasMaterial, android.R.layout.simple_spinner_item);
        materialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaterial.setAdapter(materialAdapter);
        Spinner spAncho = (Spinner) itemView.findViewById(R.id.spAncho);
        final ArrayAdapter<CharSequence> anchoAdapter = ArrayAdapter.createFromResource(
                context, R.array.bordadasAncho, android.R.layout.simple_spinner_item);
        anchoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAncho.setAdapter(anchoAdapter);
        final EditText edtLargo = (EditText)itemView.findViewById(R.id.edtLargo);
        Spinner spColores = (Spinner) itemView.findViewById(R.id.spColores);
        final ArrayAdapter<CharSequence> coloresAdapter = ArrayAdapter.createFromResource(
                context, R.array.colores, android.R.layout.simple_spinner_item);
        coloresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColores.setAdapter(coloresAdapter);
        Spinner spPresentacion = (Spinner) itemView.findViewById(R.id.spPresentacion);
        final ArrayAdapter<CharSequence> presAdapter = ArrayAdapter.createFromResource(
                context, R.array.presentacion, android.R.layout.simple_spinner_item);
        presAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(presAdapter);
        if(volver){
            if(PrintmaxTestService.unidad == "Metros"){
                rbMetros.setChecked(true);
                rbUnidades.setChecked(false);
            } else {
                rbMetros.setChecked(false);
                rbUnidades.setChecked(true);
            }

            edtQty.setText(String.valueOf(PrintmaxTestService.cantidad));
            spAncho.setSelection(Arrays.binarySearch(anchoBordadas, PrintmaxTestService.ancho));
            edtLargo.setText(String.valueOf(PrintmaxTestService.largo));
            spMaterial.setSelection(PrintmaxTestService.material-8);
            spColores.setSelection(PrintmaxTestService.colores-1);
            spPresentacion.setSelection(PrintmaxTestService.presentacion);
        } else {
            PrintmaxTestService.unidad = "Metros";
            PrintmaxTestService.material = 8;
            PrintmaxTestService.colores = 1;
            PrintmaxTestService.presentacion = 0;
        }
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
                    if (!edtQty.getText().toString().equals("")) {
                        PrintmaxTestService.cantidad = Integer.parseInt(edtQty.getText().toString());
                    }
                }
            });

            spAncho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PrintmaxTestService.ancho = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
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
                    if(!edtLargo.getText().toString().equals("")){
                        PrintmaxTestService.largo = Integer.parseInt(edtLargo.getText().toString());
                    }
                }
            });


            spMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PrintmaxTestService.material = i+8;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            spColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PrintmaxTestService.colores = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            spPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PrintmaxTestService.presentacion = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        Picasso.with(context)
                .load(tagList.get(position).link)
                .into(imgProductDialog);
        txtProductDialog.setText(tagList.get(position).name);

        builder.setView(itemView);
        builder.setNegativeButton("Añadir al carrito", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
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
                String cod = new StringBuilder(codigo[PrintmaxTestService.material]).append(PrintmaxTestService.ancho).append(PrintmaxTestService.colores).append("c").toString();
                PrintmaxTestService.get().getPrice(cod).enqueue(new Callback<Price>() {
                    @Override
                    public void onResponse(Call<Price> call, Response<Price> response) {
                        if (PrintmaxTestService.unidad == "Unidades") {
                            cantUnidades = PrintmaxTestService.cantidad;
                            PrintmaxTestService.cantidad *= (PrintmaxTestService.largo *1000);
                            cantMetros = PrintmaxTestService.cantidad;
                        } else {
                            cantMetros = PrintmaxTestService.cantidad;
                            cantUnidades = ((float)PrintmaxTestService.cantidad / PrintmaxTestService.largo) *1000;
                        }
                        if (PrintmaxTestService.cantidad >= 10000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getprecioe());
                        } else if (PrintmaxTestService.cantidad >= 5000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getpreciod());
                        } else if (PrintmaxTestService.cantidad >= 3000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getprecioc());
                        } else if (PrintmaxTestService.cantidad >= 1000) {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getpreciob());
                        } else {
                            PrintmaxTestService.price = Float.parseFloat(response.body().getprecioa());
                        }

                        PrintmaxTestService.price *=  PrintmaxTestService.cantidad;

                        if(PrintmaxTestService.colores == 2){
                            PrintmaxTestService.price += PrintmaxTestService.price / 10;
                        } else if(PrintmaxTestService.colores == 3){
                            PrintmaxTestService.price += (PrintmaxTestService.price / 100)*18;
                        } else if (PrintmaxTestService.colores == 4){
                            PrintmaxTestService.price += PrintmaxTestService.price / 4;
                        }

                        if(PrintmaxTestService.presentacion == 0) {
                            PrintmaxTestService.price -= (PrintmaxTestService.price / 100) * 5;
                        }
                        if(PrintmaxTestService.price < 3000){
                            PrintmaxTestService.price = 3000;
                        }
                        PrintmaxTestService.priceMetro = PrintmaxTestService.price / cantMetros;
                        PrintmaxTestService.priceUnidad = PrintmaxTestService.price / cantUnidades;
                        showConfirmDialog(position, PrintmaxTestService.cantidad, PrintmaxTestService.unidad);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Price> call, Throwable t) {
                        String price = "a";
                    }
                });
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
        TextView txtProductPriceUnidad = (TextView)itemView.findViewById(R.id.txt_cart_product_priceUnidad);
        TextView txtProductPriceMetro = (TextView)itemView.findViewById(R.id.txt_cart_product_priceMetro);
        TextView txtProductPrice = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txtCantidad = (TextView)itemView.findViewById(R.id.txt_metros);
        TextView txtMaterial = (TextView)itemView.findViewById(R.id.txt_material);
        TextView txtTam = (TextView)itemView.findViewById(R.id.txtTam);
        TextView txtColores = (TextView)itemView.findViewById(R.id.txtColores);
        TextView txtPres = (TextView)itemView.findViewById(R.id.txtPres);

        Picasso.with(context).load(tagList.get(position).link).into(imgProductDialog);
        txtProductDialog.setText(new StringBuilder(tagList.get(position).name).toString());
        txtCantidad.setText(new StringBuilder().append(cantMetros).append(" metros").append("\n")
                .append((int) cantUnidades).append(" unidades").toString());
        txtColores.setText(new StringBuilder("Colores: ").append(PrintmaxTestService.colores));
        String mat = material[PrintmaxTestService.material];
        String pres = presentacion[PrintmaxTestService.presentacion];

        txtProductPriceUnidad.setText(new StringBuilder("Por unidad: $").append(PrintmaxTestService.priceUnidad).toString());
        txtProductPriceMetro.setText(new StringBuilder("Por metro: $").append(PrintmaxTestService.priceMetro).toString());
        txtProductPrice.setText(new StringBuilder("Total: $").append(PrintmaxTestService.price).toString());
        txtMaterial.setText(new StringBuilder("Material: ").append(mat));
        txtTam.setText(new StringBuilder("Tamaño: ").append(PrintmaxTestService.ancho).append(" mm x ").append(PrintmaxTestService.largo).append(" mm"));
        txtPres.setText(new StringBuilder("Presentacion: ").append(pres));
        
        final float finalPrice = PrintmaxTestService.price;
        builder.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(position == 0){
                    estampadasShowAddToCartDialog(position, true);
                } else {
                    bordadasShowAddToCartDialog(position, true);
                }
            }
        });
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                try {

                    //Add to SQLite
                    Cart cartItem = new Cart();
                    cartItem.etiqueta = txtProductDialog.getText().toString();
                    cartItem.cantidad = cantidad;
                    cartItem.unidad = unidad;
                    cartItem.material = PrintmaxTestService.material;
                    cartItem.ancho = PrintmaxTestService.ancho;
                    cartItem.largo = PrintmaxTestService.largo;
                    cartItem.colores = PrintmaxTestService.colores;
                    cartItem.presentacion = PrintmaxTestService.presentacion;
                    cartItem.price = finalPrice;

                    //Add to DB
                    PrintmaxTestService.get().cartRepository.insertIntoCart(cartItem);
                    Log.d("MATIROZEN_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Enviado al carrito", Toast.LENGTH_SHORT).show();
                } catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("MATIROZEN_DEBUG", ex.getMessage());
                }
                listener.update();
            }
        });

        builder.setView(itemView);
        builder.show();


    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    private boolean isEstampada() {
        if(PrintmaxTestService.material >= 0 && PrintmaxTestService.material < 8){
            return true;
        } else {
            return false;
        }
    }
    private boolean isBordada() {
        if(PrintmaxTestService.material >= 0 && PrintmaxTestService.material > 8){
            return true;
        } else {
            return false;
        }
    }
}
