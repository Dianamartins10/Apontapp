package com.example.apontapp.Products;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apontapp.NewProduct.NewProductActivity;
import com.example.apontapp.R;

import java.util.ArrayList;

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
            }
        });

    }

    private void addProductToList(){

    }
}
