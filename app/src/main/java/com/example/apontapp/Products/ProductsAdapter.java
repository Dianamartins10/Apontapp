package com.example.apontapp.Products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apontapp.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> implements View.OnClickListener {


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewCategory;
        public TextView buttonAdd;
        private ArrayList<String> datasetprod;
        private Context context;



        public ViewHolder(View v){
            super(v);
            textViewName = v.findViewById ( R.id.list_row_nameproduct );
            textViewCategory= v.findViewById ( R.id.list_row_category );
            buttonAdd= v.findViewById ( R.id.textViewAdd );
        }
    }
    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
       // return datasetprod.size();
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
