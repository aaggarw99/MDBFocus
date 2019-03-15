package com.example.mdbfocus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Location> locations;

    public LocationAdapter(Context context, ArrayList<Location> locations) {
        // passes in a context and list of social objects
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // our custom cell layout (card.xml)
        View row = inflater.inflate(R.layout.card, viewGroup, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Location l = locations.get(i);

        // set cell stuff
        ((Item) viewHolder).nameField.setText(l.getName());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    // class that holds all cell values in recycler view
    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameField;


        public Item(View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.locName);

            // this allows the cell to be clicked
            itemView.setOnClickListener(this);
        }

        // handles when cell is clicked on
        @Override
        public void onClick(View v) {
            Location location = locations.get(getAdapterPosition());
//            Intent intent = new Intent(context, LocationDetail.class);
//            intent.putExtra("NAME", purchase.getName());
//
//            context.startActivity(intent);
        }
    }

    /*
    public void removeAt(int position, Context context) {
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);
        Purchase p = purchases.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, purchases.size());

        mDatabaseHelper.deleteitem(p.getName());
    }*/

    public void setSearchOperation(List<Location> newList) {
        locations = new ArrayList<>();
        locations.addAll(newList);
        notifyDataSetChanged();
    }
}
