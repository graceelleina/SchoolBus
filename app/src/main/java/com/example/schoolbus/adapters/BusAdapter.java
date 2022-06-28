package com.example.schoolbus.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.schoolbus.BusDetail;
import com.example.schoolbus.R;
import com.example.schoolbus.models.Bus;

import java.util.Vector;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

    private Vector<Bus> listBus;
    private Context context;

    public BusAdapter(Context context, Vector<Bus> listEmployee){
        this.listBus = listEmployee;
        this.context = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull BusAdapter.ViewHolder holder, int position) {
        // bind the data to your UI
        Bus e = listBus.get(position);
        holder.busName.setText(e.getName());
        holder.busType.setText(String.valueOf(e.getType()));
        holder.busPlate.setText(String.valueOf(e.getPlate()));
        holder.busPlate.setText(String.valueOf(e.getPlate()));
        holder.busUsername.setText(String.valueOf(e.getUsername()));

        Glide.with(context)
                .load(e.getPicture())
                .into(holder.busImg);

    }

    @Override
    public int getItemCount() {
        return this.listBus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView busImg;
        private TextView busName, busType, busPlate, busUsername;
        private CardView cvBus;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            cvBus = itemView.findViewById(R.id.cvItemBus);
            cvBus.setOnClickListener(this);
            busImg = itemView.findViewById(R.id.itemImg);
            busName = itemView.findViewById(R.id.itemName);
            busType = itemView.findViewById(R.id.itemType);
            busPlate = itemView.findViewById(R.id.itemPlate);
            busUsername = itemView.findViewById(R.id.itemUsername);

        }

        @Override
        public void onClick(View view) {
            Log.v("test", "masuk");
            Intent intent = new Intent(view.getContext(), BusDetail.class);
            busImg.buildDrawingCache();
            Bitmap image = busImg.getDrawingCache();
            Bundle extras = new Bundle();
            extras.putParcelable("imagebitmap", image);
            intent.putExtra( "name",busName.getText().toString());
            intent.putExtra( "type",busType.getText().toString());
            intent.putExtra( "plate", busPlate.getText().toString());
            intent.putExtra( "username", busUsername.getText().toString());
            intent.putExtras(extras);
            view.getContext().startActivity(intent);
        }
    }

}