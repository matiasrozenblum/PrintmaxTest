package com.example.matirozen.printmaxtest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matirozen.printmaxtest.HomeActivity;
import com.example.matirozen.printmaxtest.Interface.ItemClickListener;
import com.example.matirozen.printmaxtest.Model.Category;
import com.example.matirozen.printmaxtest.R;
import com.example.matirozen.printmaxtest.Retrofit.PrintmaxTestService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        //Load image
        Picasso.with(context)
                .load(categories.get(position).link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).name);

        //Event
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                PrintmaxTestService.currentCategory = categories.get(position);

                //Start new activity
                context.startActivity(new Intent(context, HomeActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
