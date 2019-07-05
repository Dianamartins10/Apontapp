package com.example.apontapp.ProductsByList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.apontapp.Products.ProductsActivity;
import com.example.apontapp.R;

public class ProductByListActivity extends AppCompatActivity {

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
    }
}
