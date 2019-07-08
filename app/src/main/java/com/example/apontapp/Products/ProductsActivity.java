package com.example.apontapp.Products;

import android.app.Activity;
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
import com.example.apontapp.R;
import com.example.apontapp.Spending.SpendingActivity;

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
    private void enterGastos() {
        startActivity(new Intent(ProductsActivity.this, SpendingActivity.class));
    }

    private void logoutUser() {
        startActivity(new Intent(ProductsActivity.this, MainActivity.class));
        productsViewModel.logout ();
        finish();

    }

    private void addProductToList(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
    }

    public void itemClicked(View v) {
        if (((CheckBox) v).isChecked()) {
            Toast.makeText(ProductsActivity.this,
                    "Checked", Toast.LENGTH_LONG).show();
        }
    }

}
