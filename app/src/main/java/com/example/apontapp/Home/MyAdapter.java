package com.example.apontapp.Home;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.example.apontapp.ProductsByList.ProductByListActivity;
import com.example.apontapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private FirebaseFirestore db;
    private FirebaseAuth mAtuh;
    private String TAG ="RESULTADO:";
    private ArrayList<String> dataset;
    private Context context;



    @Override
    public void onClick(View v) {


    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TextView buttonViewOption;

        public ViewHolder(View v){
            super(v);
            textView = v.findViewById ( R.id.list_row );
            buttonViewOption= v.findViewById (R.id.textViewOptions);
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
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from ( parent.getContext () )
                .inflate ( R.layout.row_list,parent, false );

        ViewHolder vh = new ViewHolder ( v );
        return vh;
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position){

        holder.getAdapterPosition ();
        holder.textView.setText(dataset.get(position));
        holder.textView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                context = v.getContext ();
                Log.d ( TAG, dataset.get(position) );
                Intent intent = new Intent ( context, ProductByListActivity.class );
                context.startActivity ( intent );

            }
        } );

        holder.buttonViewOption.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                context = view.getContext ();
                PopupMenu popup = new PopupMenu ( context, holder.buttonViewOption );
                popup.inflate ( R.menu.options_menu );
                popup.setOnMenuItemClickListener ( new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId ()){
                                case R.id.editar:

                                    break;

                                case R.id.apagar:


                                    break;
                            }
                            return false;
                    }
                } );
                popup.show ();
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
