package com.example.bittask.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bittask.Model.HomeModel;
import com.example.bittask.R;
import com.example.bittask.UI.DisplayPhotoActivity;
import com.example.bittask.Utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HomeModel> homeList;

    public HomeAdapter(Context context, ArrayList<HomeModel> homeList) {
        this.context = context;
        this.homeList = homeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_photo, viewGroup, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context).load(homeList.get(position).getImage()).apply(new RequestOptions()
                .placeholder(R.drawable.ic_placeholder_48dp)
                .centerCrop())
                .into(holder.photoIv);
        holder.photoIv.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayPhotoActivity.class);
            intent.putExtra(Constants.PHOTO_KEY, homeList.get(position));
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photoIv)
        ImageView photoIv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
