package com.example.florausher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataSearch> data= Collections.emptyList();
    DataSearch current;

    // create constructor to initialize context and data sent from MainActivity
    public AdapterSearch(Context context, List<DataSearch> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_adapter_search, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataSearch current=data.get(position);
        myHolder.PlantName.setText(current.PlantName);
        myHolder.PlantDescription.setText("Description: " + current.PlantDescription);
        myHolder.PlantType.setText("Type: " + current.PlantType);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView PlantName;
        TextView PlantDescription;
        TextView PlantType;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            PlantName= (TextView) itemView.findViewById(R.id.PlantName);
            PlantDescription = (TextView) itemView.findViewById(R.id.PlantDescription);
            PlantType = (TextView) itemView.findViewById(R.id.PlantType);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

        }

    }

}