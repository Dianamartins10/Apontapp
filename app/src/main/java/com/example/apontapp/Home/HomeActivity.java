package com.example.apontapp.Home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.apontapp.NewList.NewListActivity;
import com.example.apontapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String TAG= "RESULTADO";
    private ArrayList<String> lista = new ArrayList<> ();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HomeViewModel homeViewModel=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newScreen = new Intent(HomeActivity.this, NewListActivity.class);
                startActivity(newScreen);
            }
        });

        recyclerView = findViewById ( R.id.recyclerview );
        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( layoutManager );

        adapter = new MyAdapter(lista);
        recyclerView.setAdapter ( adapter );

        ArrayList<String> soparatestarxd = new ArrayList<> ( );

        soparatestarxd.add ( "teste" );
        soparatestarxd.add ( "teste123" );
        soparatestarxd.add ( "teste321" );

        adapter.updateDataset ( soparatestarxd );

        homeViewModel = ViewModelProviders.of(this).get (HomeViewModel.class);

        homeViewModel.home ();


        homeViewModel.livedata.observe ( this, new Observer<HomeViewModel.ResultTypeView> () {
            @Override
            public void onChanged(@Nullable HomeViewModel.ResultTypeView resultTypeView) {
                switch (resultTypeView){
                    case SUCCESS:
                            list();
                        break;

                    case ERROR:
                        Toast.makeText(HomeActivity.this, "Ainda não existem listas criadas!", Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            }
        } );

        homeViewModel.lista.observe ( this, new Observer<ArrayList<String>> () {
            @Override
            public void onChanged(@Nullable ArrayList<String> strings) {
                adapter.updateDataset(strings);
            }
        } );
    }

    public void list(){
        //homeViewModel.home(lista);
    }


}
