package com.example.garbagecollector.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollector.R;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

    String[] partners_ru = {"Сервисы Яндекс", "Самсунг", "Макдональдс"};
    int[] images = {R.drawable.ic_yandex, R.drawable.ic_samsung,R.drawable.ic_mcdonalds};


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);



        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.shop_item_image.setImageResource(images[position]);
        holder.shop_item_text.setText(partners_ru[position]);



    }

    @Override
    public int getItemCount() {
        return 3;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shop_item_image;
        TextView shop_item_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_item_image = itemView.findViewById(R.id.shop_item_image);
            shop_item_text = itemView.findViewById(R.id.shop_item_text);

        }
    }

}
