package com.example.apontapp.Home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.apontapp.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private ArrayList<String> dataset;



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View v){
            super(v);
            textView = v.findViewById ( R.id.list_row );
        }
    }


    public MyAdapter(ArrayList<String> myDataset){
        dataset=myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from ( parent.getContext () )
                .inflate ( R.layout.row_list,parent, false );

        ViewHolder vh = new ViewHolder ( v );
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textView.setText(dataset.get(position));
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}
