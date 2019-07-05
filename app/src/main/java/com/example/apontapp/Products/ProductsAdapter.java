package com.example.apontapp.Products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apontapp.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> implements View.OnClickListener {


    private ArrayList<String> datasetprod;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewCategory;
        public TextView buttonAdd;




        public ViewHolder(View v){
            super(v);
            textViewName = v.findViewById ( R.id.list_row_nameproduct );
            buttonAdd= v.findViewById ( R.id.btnViewAdd );
        }
    }

    public ProductsAdapter(ArrayList<String> mDatasetprod){
        datasetprod=mDatasetprod;
    }


    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from ( viewGroup.getContext () )
                .inflate ( R.layout.row_list_products,viewGroup, false );
        ViewHolder vh = new ViewHolder ( v );
        return  vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getAdapterPosition ();
        holder.textViewName.setText ( datasetprod.get(position));

        // aqui é onde vais adicionar a ação do teu botão
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
