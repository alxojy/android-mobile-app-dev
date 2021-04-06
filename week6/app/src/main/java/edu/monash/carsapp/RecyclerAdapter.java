package edu.monash.carsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Car> data;


    public RecyclerAdapter(ArrayList<Car> _data) {
        super();
        data = _data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false); //CardView inflated as RecyclerView list item

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemModel.setText(data.get(position).getModel());
        viewHolder.itemMaker.setText(data.get(position).getMaker());
        viewHolder.itemYear.setText(data.get(position).getYear());
        viewHolder.itemColor.setText(data.get(position).getColor());
        viewHolder.itemSeats.setText(data.get(position).getSeats());
        viewHolder.itemPrice.setText(data.get(position).getPrice() + "");


        //a class declared in a method (so called local or anonymous class can only access the method's local variables if they are declared final (1.8 or are effectively final)
        //this has to do with Java closures
        // see https://docs.oracle.com/javase/tutorial/java/javaOO/localclasses.html and https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html
        final int fPosition = position;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Item at position " + fPosition + " was clicked!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
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
