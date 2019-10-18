package com.pavelekozhevnikov.homework1.Adapter;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pavelekozhevnikov.homework1.App;
import com.pavelekozhevnikov.homework1.Model.WeatherCardInfo;
import com.pavelekozhevnikov.homework1.R;

public class RecycledViewAdapter extends RecyclerView.Adapter {

    private WeatherCardInfo[] dataSource;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textViewDay;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView;
            textView = cardView.findViewById(R.id.textView);
            textViewDay = cardView.findViewById(R.id.textViewDay);
            imageView = cardView.findViewById(R.id.imageView);
        }

        TextView getTextViewDay() {
            return textViewDay;
        }

        TextView getTextView() {
            return textView;
        }

        ImageView getImageView() {
            return imageView;
        }
    }


    public RecycledViewAdapter(WeatherCardInfo[] dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecycledViewAdapter.ViewHolder)holder).getTextViewDay().setText(dataSource[position].day);
        ((RecycledViewAdapter.ViewHolder)holder).getTextView().setText(dataSource[position].temperature);
        int resId = App.getContext().getResources().getIdentifier("com.pavelekozhevnikov.homework1:drawable/" + dataSource[position].icon, null, null);
        if(resId>0)
            ((RecycledViewAdapter.ViewHolder)holder).getImageView().setImageResource(resId);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }
}
