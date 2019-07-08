package com.example.apontapp.Products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

        //public Button addProductToList;





        public ViewHolder(View v){
            super(v);
            textViewName = v.findViewById ( R.id.list_row_nameproduct );
            buttonAdd= v.findViewById ( R.id.btnViewAdd );
            checkBox=v.findViewById ( R.id.btnViewAdd );
            //addProductToList = v.findViewById(R.id.btn_AddProductToList);
            //textViewName.setOnClickListener((View.OnClickListener) this);
        }


        ArrayList<String> selectedproducts = new ArrayList<>();
        private void addProductToList(){
            Log.d("", String.valueOf(selectedproducts));
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

    ArrayList<String> selectedproducts = new ArrayList<>();

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.getAdapterPosition ();

        holder.textViewName.setText ( datasetprod.get(position));

        holder.textViewName.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked ()){
                    holder.checkBox.setChecked ( false );
                    selectedproducts.remove(datasetprod.get(holder.getAdapterPosition()));
                    Log.d("", String.valueOf(selectedproducts));

                }else{
                    holder.checkBox.setChecked ( true );
                    selectedproducts.add(datasetprod.get(holder.getAdapterPosition()));
                    Log.d("", String.valueOf(selectedproducts));
                }
            }
        } );
        holder.checkBox.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked ()){
                    holder.checkBox.setChecked ( true );

                    selectedproducts.add(datasetprod.get(holder.getAdapterPosition()));
                    Log.d("", String.valueOf(selectedproducts));
                }else{
                    holder.checkBox.setChecked ( false );
                    selectedproducts.remove(datasetprod.get(holder.getAdapterPosition()));
                    Log.d("", String.valueOf(selectedproducts));
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

    public ArrayList<String> getproducts(){
        ArrayList<String> selecteditem = selectedproducts;
        return selecteditem;


    }

}
