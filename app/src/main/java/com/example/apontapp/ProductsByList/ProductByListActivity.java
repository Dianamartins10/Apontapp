package com.example.apontapp.ProductsByList;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.apontapp.Login.MainActivity;
import com.example.apontapp.Products.ProductsActivity;
import com.example.apontapp.ProductsByList.ProductByListViewModel;
import com.example.apontapp.R;
import com.example.apontapp.Spending.SpendingActivity;

import java.util.ArrayList;

public class ProductByListActivity extends AppCompatActivity {

    public static String namelist;
    private ArrayList<String> list = new ArrayList<> ();
    private RecyclerView recyclerView;
    private ProductByListAdapter productByListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProductByListViewModel productByListViewModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_product_by_list );
        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        FloatingActionButton fab = (FloatingActionButton) findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( ProductByListActivity.this, ProductsActivity.class );
                startActivity ( intent );
            }
        } );

        Intent i = getIntent();
        namelist = i.getStringExtra("name");

        recyclerView= findViewById ( R.id.recyclerviewproducts );
        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager ( layoutManager );

        productByListAdapter = new ProductByListAdapter(list);
        recyclerView.setAdapter (productByListAdapter);

        productByListViewModel = ViewModelProviders.of(this).get(ProductByListViewModel.class);

        productByListViewModel.productByList ();

        productByListViewModel.livedata.observe ( this, new Observer<ProductByListViewModel.ResultTypeListProd> () {
            @Override
            public void onChanged(@Nullable ProductByListViewModel.ResultTypeListProd resultTypeListProd) {

                switch (resultTypeListProd){
                    case SUCCESS:

                        break;
                    case ERROR:
                        Toast.makeText(ProductByListActivity.this, "Não Existem Produtos", Toast.LENGTH_LONG)
                                .show();
                        break;
                }

            }
        } );

        productByListViewModel.name.observe ( this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> strings) {
                productByListAdapter.updateDataset ( strings );
            }
        } );


    }

    //creates options menu of topbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //redirect if click on items
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
        startActivity(new Intent(ProductByListActivity.this, SpendingActivity.class));
    }

    private void logoutUser() {
        finishAffinity();
        startActivity(new Intent(ProductByListActivity.this, MainActivity.class));
        productByListViewModel.logout ();
        finish();

    }
}
