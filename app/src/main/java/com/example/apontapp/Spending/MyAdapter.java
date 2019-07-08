package com.example.apontapp.Spending;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.apontapp.ProductsByList.ProductByListActivity;
import com.example.apontapp.R;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class MyAdapter extends RecyclerView.Adapter<com.example.apontapp.Spending.MyAdapter.ViewHolder> implements View.OnClickListener {


    private ArrayList<String> dataset;
    private Context context;

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TextView buttonViewOption;
        public EditText listprice;

        public ViewHolder(View v){
            super(v);
            textView = v.findViewById ( R.id.list_row );
            buttonViewOption= v.findViewById (R.id.textViewOptions);
            listprice = v.findViewById(R.id.listprice);
        }
    }

    public MyAdapter(ArrayList<String> myDataset){
        dataset=myDataset;
    }

    public void setDataset(ArrayList<String> dataset, Context mCtx) {

        this.dataset = dataset;
        this.context=mCtx;
    }



    @Override
    public com.example.apontapp.Spending.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from ( parent.getContext () )
                .inflate ( R.layout.row_list_spending,parent, false );

        com.example.apontapp.Spending.MyAdapter.ViewHolder vh = new com.example.apontapp.Spending.MyAdapter.ViewHolder( v );
        return vh;
    }


    ArrayList<Double> conta = new ArrayList<>();

    public void onBindViewHolder(final com.example.apontapp.Spending.MyAdapter.ViewHolder holder, final int position){

        holder.getAdapterPosition ();
        holder.textView.setText(dataset.get(position));

        holder.getAdapterPosition ();
        holder.listprice.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                context = v.getContext ();
                /*
                conta.clear();

                for(int i =0; i<dataset.size();i++){
                    if(String.valueOf(holder.listprice.getText()).equals(null) ||String.valueOf(holder.listprice.getText()).equals("")){
                        conta.add(i, (double) 0);
                    }

                }

                conta.set(holder.getAdapterPosition (),Double.valueOf(String.valueOf(holder.listprice.getText())));
                double sum = 0;

                for (double value : conta) {
                    sum += value;
                }
                Log.d("", String.valueOf(conta));
                Log.d("", String.valueOf(sum));
                */
            }

        } );


    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }

    public void updateDataset(ArrayList<String> newData) {
        dataset = newData;
        notifyDataSetChanged ();
    }
}