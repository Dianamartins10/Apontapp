package com.example.apontapp.ProductsByList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.apontapp.R;

import java.util.ArrayList;

public class ProductByListAdapter extends RecyclerView.Adapter<ProductByListAdapter.ViewHolder> implements View.OnClickListener {


    private ArrayList<String> datasetprod;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView buttonAdd;
        public CheckBox checkBox;




        public ViewHolder(View v){
            super(v);
            textViewName = v.findViewById ( R.id.list_row_nameproduct );
            buttonAdd= v.findViewById ( R.id.btnViewAdd );
            checkBox=v.findViewById ( R.id.btnViewAdd );
        }
    }

    public ProductByListAdapter(ArrayList<String> mDatasetprod){
        datasetprod=mDatasetprod;
    }


    @Override
    public ProductByListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from ( viewGroup.getContext () )
                .inflate ( R.layout.row_list_products,viewGroup, false );
        ProductByListAdapter.ViewHolder vh = new ProductByListAdapter.ViewHolder( v );
        return  vh;
    }

    @Override
    public void onBindViewHolder(final ProductByListAdapter.ViewHolder holder, int position) {
        holder.getAdapterPosition ();
        holder.textViewName.setText ( datasetprod.get(position));
        holder.textViewName.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked ()){
                    holder.checkBox.setChecked ( false );
                }else{
                    holder.checkBox.setChecked ( true );
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return datasetprod.size();
    }

    @Override
    public void onClick(View v) {

    }

    public void updateDataset(ArrayList<String> newDataSet){
        datasetprod=newDataSet;
        notifyDataSetChanged ();
    }

}
