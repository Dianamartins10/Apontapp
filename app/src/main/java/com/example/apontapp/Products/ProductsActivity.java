package com.example.apontapp.Products;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apontapp.Home.HomeActivity;
import com.example.apontapp.Login.MainActivity;
import com.example.apontapp.NewProduct.NewProductActivity;
import com.example.apontapp.ProductsByList.ProductByListActivity;
import com.example.apontapp.R;
import com.example.apontapp.Spending.SpendingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<> ();
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProductsViewModel productsViewModel=null;
    public Button addProductToList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_products );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        FloatingActionButton fab = findViewById ( R.id.fab_newprod);
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent newScreen = new Intent(ProductsActivity.this, NewProductActivity.class);
                startActivity(newScreen);
            }
        } );


        recyclerView= findViewById ( R.id.recyclerviewproducts );
        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( layoutManager );

        productsAdapter = new ProductsAdapter(list);
        recyclerView.setAdapter ( productsAdapter );

        productsViewModel = ViewModelProviders.of ( this ).get(ProductsViewModel.class);
        productsViewModel.products ();

        productsViewModel.livedata.observe ( this, new Observer<ProductsViewModel.ResultTypeListProd> () {
            @Override
            public void onChanged(@Nullable ProductsViewModel.ResultTypeListProd resultTypeListProd) {

                switch (resultTypeListProd){
                    case SUCCESS:

                    break;
                    case ERROR:
                        Toast.makeText(ProductsActivity.this, "Não Existem Produtos", Toast.LENGTH_LONG)
                                .show();
                        break;
                }

            }
        } );
        productsViewModel.name.observe ( this, new Observer<ArrayList<String>> () {
            @Override
            public void onChanged(@Nullable ArrayList<String> strings) {
                productsAdapter.updateDataset ( strings );
            }
        } );


//////////////////////////
        addProductToList = findViewById(R.id.btn_AddProductToList);
        addProductToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addProductToList();
                final String message = ProductByListActivity.namelist;
                Log.d("",message);

                db = FirebaseFirestore.getInstance();
                db.collection("lists")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d("", document.getId() + " => " + document.getString("listName"));

                                        if(document.getString("listName").equals(message)){

                                            DocumentReference lists = db.collection("lists").document(document.getId());
                                            ArrayList<String> oldlist = (ArrayList<String>) document.get("products");
                                            Log.d("", String.valueOf(oldlist));


                                            for (Object x : oldlist){
                                                if (!productsAdapter.selectedproducts.contains(x))
                                                    productsAdapter.selectedproducts.add((String) x);
                                                else{

                                                }
                                            }


                                            lists.update(NAME_KEY, productsAdapter.selectedproducts);

                                        }
                                    }
                                } else {
                                    Log.d("", "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutUser();
                return true;
            case R.id.action_gastos:
                enterGastos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //method thats redirect to spending activity
    private void enterGastos() {
        startActivity(new Intent(ProductsActivity.this, SpendingActivity.class));
    }

    //method that calls logout in viewmodel
    private void logoutUser() {
        finishAffinity();
        startActivity(new Intent(ProductsActivity.this, MainActivity.class));
        productsViewModel.logout ();
        finish();

    }

    private FirebaseFirestore db;
    private static final String NAME_KEY = "products";


    private void addProductToList(){
        Log.d("", String.valueOf(productsAdapter.selectedproducts));
        finish();


    }

    //method that shows a message whenever an item gets checked
    public void itemClicked(View v) {
        if (((CheckBox) v).isChecked()) {
            Toast.makeText(ProductsActivity.this,
                    "Checked", Toast.LENGTH_LONG).show();
        }
    }

}
