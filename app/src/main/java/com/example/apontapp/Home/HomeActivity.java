package com.example.apontapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.example.apontapp.NewList.NewListActivity;
import com.example.apontapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String TAG= "RESULTADO";
    private ArrayList<String> lista = new ArrayList<> ();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


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

        mAuth= FirebaseAuth.getInstance ();
        String user_id = mAuth.getUid ();

        db = FirebaseFirestore.getInstance();
        db.collection("lists").whereEqualTo ("user_id",user_id).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.get ( "listName" ) + " => " + document.getData());
                        lista.add(document.get("listName").toString());
                    }

                    adapter = new MyAdapter(lista);
                    recyclerView.setAdapter ( adapter );
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        } );

        recyclerView = findViewById ( R.id.recyclerview );
        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( layoutManager );


    }

}
