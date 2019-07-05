package com.example.apontapp.NewProduct;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apontapp.Products.ProductsActivity;
import com.example.apontapp.R;

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

        createProduct = findViewById(R.id.btn_NewProduct);
        categoryProduct= findViewById(R.id.editNewProductCategory);
        nameProduct = findViewById(R.id.editNewProductName);

        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });

    }

    private void createProduct(){
        String name = nameProduct.getText().toString();
        String category = categoryProduct.getText().toString();
        newProductViewModel.createProduct(name,category);
    }
}
