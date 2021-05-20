package edu.monash.carsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.carsapp.provider.Car;
import edu.monash.carsapp.provider.CarViewModel;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Car> data;
    CarViewModel carViewModel;
    private Context context;

    RecyclerAdapter(CarViewModel viewModel) {
        carViewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false); //CardView inflated as RecyclerView list item
        context = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemMaker.setText(data.get(position).getMaker());
        viewHolder.itemModel.setText(data.get(position).getModel());
        viewHolder.itemYear.setText(data.get(position).getYear());
        viewHolder.itemColor.setText("Color: " + data.get(position).getColor());
        viewHolder.itemSeats.setText("Seats: " + data.get(position).getSeats());
        viewHolder.itemPrice.setText("$" + data.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void onItemMoved(int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(data, i, i + 1);
            }
        }
        else {
            for (int i = from; i > to; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
    }

    public void onItemSelected(RecyclerView.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.itemView.setBackgroundColor(Color.YELLOW);
    }

    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
    }

    public void setCars(List<Car> cars) {
        data = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemMaker;
        public TextView itemModel;
        public TextView itemYear;
        public TextView itemColor;
        public TextView itemSeats;
        public TextView itemPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemMaker = itemView.findViewById(R.id.cardMaker);
            itemModel = itemView.findViewById(R.id.cardModel);
            itemYear = itemView.findViewById(R.id.cardYear);
            itemColor = itemView.findViewById(R.id.cardColor);
            itemSeats = itemView.findViewById(R.id.cardSeats);
            itemPrice = itemView.findViewById(R.id.cardPrice);
        }
    }
}
