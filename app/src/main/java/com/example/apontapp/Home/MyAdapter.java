package com.example.apontapp.Home;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apontapp.EditList.EditListActivity;
import com.example.apontapp.ProductsByList.ProductByListActivity;
import com.example.apontapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {


    private ArrayList<String> dataset;
    private Context context;

    @Override
    public void onClick(View v) {

    }
    public void updateDataset(ArrayList<String> newData) {
        dataset = newData;
        notifyDataSetChanged ();
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

    private FirebaseFirestore db;
    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position){

        holder.getAdapterPosition ();
        holder.textView.setText(dataset.get(position));
        holder.textView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                context = v.getContext ();
                //Log.d ( TAG, dataset.get(position) );

                Intent i = new Intent ( context, ProductByListActivity.class );
                i.putExtra("name", dataset.get(position));
                context.startActivity ( i );

            }
        } );

        holder.buttonViewOption.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                context = view.getContext ();

                //add popup menu by list edit + delete
                PopupMenu popup = new PopupMenu ( context, holder.buttonViewOption );
                popup.inflate ( R.menu.options_menu );
                popup.setOnMenuItemClickListener ( new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId ()){
                                case R.id.editar:
                                    Intent intent = new Intent ( context, EditListActivity.class );
                                    intent.putExtra("name", dataset.get(position));
                                    context.startActivity ( intent );
                                    break;
                                case R.id.apagar:

                                    db = FirebaseFirestore.getInstance ();
                                    db.collection ( "lists" )
                                            .get ()
                                            .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                    if (task.isSuccessful ()) {
                                                        for (QueryDocumentSnapshot document : task.getResult ()) {

                                                            if (document.getString ( "listName" ).equals ( dataset.get(position) )) {
                                                                DocumentReference lists = db.collection ( "lists" ).document ( document.getId () );
                                                                lists.delete();
                                                            }
                                                        }
                                                    } else {
                                                        Log.d ( "", "Error getting documents: ", task.getException () );
                                                    }
                                                }
                                            } );

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


}
