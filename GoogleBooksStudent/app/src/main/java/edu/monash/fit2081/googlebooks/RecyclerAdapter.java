package edu.monash.fit2081.googlebooks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<GoogleBook> data;


    public RecyclerAdapter(ArrayList<GoogleBook> _data) {
        super();
        data = _data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemTitle.setText(data.get(position).getBookTitle());
        viewHolder.itemAuthor.setText(data.get(position).getAuthors());
        viewHolder.itemDate.setText(data.get(position).getPublishedDate().substring(0,4));


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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemTitle;
        public TextView itemAuthor;
        public TextView itemDate;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemTitle = itemView.findViewById(R.id.item_title);
            itemAuthor = itemView.findViewById(R.id.item_author);
            itemDate = itemView.findViewById(R.id.item_date);
        }
    }
}
