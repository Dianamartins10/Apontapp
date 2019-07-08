package com.example.apontapp.NewProduct;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apontapp.Login.MainActivity;
import com.example.apontapp.Products.ProductsActivity;
import com.example.apontapp.R;
import com.example.apontapp.Spending.SpendingActivity;

public class NewProductActivity extends AppCompatActivity {
    public EditText nameProduct;
    public EditText categoryProduct;
    public Button createProduct;
    private NewProductViewModel newProductViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_new_product );

        newProductViewModel = ViewModelProviders.of(this).get(NewProductViewModel.class);
        //receive states of newproductviewmodel of enum
        newProductViewModel.livedata.observe ( this, new Observer<NewProductViewModel.ResultTypeProduct> () {
            @Override
            public void onChanged(@Nullable NewProductViewModel.ResultTypeProduct resultTypeProduct) {
                switch(resultTypeProduct){
                    case CHECKBOTH:
                        Toast.makeText(NewProductActivity.this, "Nome e Categoria Obrigatórios!", Toast.LENGTH_LONG)
                                .show();
                        finish();
                        break;

                    case CHECKNAME:
                        Toast.makeText(NewProductActivity.this, "Nome de Produto Necessário!", Toast.LENGTH_LONG)
                                .show();
                        finish();
                        break;

                    case CHECKCATEGORY:
                        Toast.makeText(NewProductActivity.this, "Categoria de Produto Necessário!", Toast.LENGTH_LONG)
                                .show();
                        finish();
                        break;

                    case SUCCESS:
                        startActivity(new Intent (NewProductActivity.this, ProductsActivity.class));
                        finish();
                        break;
                }
            }
        } );

        //associates variables with xml
        createProduct = findViewById(R.id.btn_NewProduct);
        categoryProduct= findViewById(R.id.editNewProductCategory);
        nameProduct = findViewById(R.id.editNewProductName);

        //action when btn of add product is clicked
        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });

    }

    //get text of inputs and calls viewmodel method
    private void createProduct(){
        String name = nameProduct.getText().toString();
        String category = categoryProduct.getText().toString();
        newProductViewModel.createProduct(name,category);
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
        startActivity(new Intent(NewProductActivity.this, SpendingActivity.class));
    }

    //method that calls logout in viewmodel
    private void logoutUser() {
        finishAffinity();
        startActivity(new Intent(NewProductActivity.this, MainActivity.class));
        newProductViewModel.logout ();
        finish();

    }
}
