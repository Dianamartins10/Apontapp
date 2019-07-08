package com.example.apontapp.Products;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apontapp.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> implements View.OnClickListener {


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

            //textViewName.setOnClickListener((View.OnClickListener) this);
        }



        public CheckBox getCheckBox() {
            return checkBox;
        }

        public TextView getTextView() {
            return textViewName;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.getAdapterPosition ();
        holder.textViewName.setText ( datasetprod.get(position));
        //Log.d("",datasetprod.get(position));
        holder.textViewName.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked ()){
                    holder.checkBox.setChecked ( false );
                    holder.getAdapterPosition();
                    Log.d("", String.valueOf(holder.getAdapterPosition()));

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
