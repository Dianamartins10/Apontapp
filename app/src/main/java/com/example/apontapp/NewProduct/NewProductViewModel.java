package com.example.apontapp.NewProduct;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.apontapp.Models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewProductViewModel extends ViewModel {

    enum ResultTypeProduct{
        SUCCESS, CHECKNAME, CHECKCATEGORY, CHECKBOTH
    }

    private FirebaseAuth mAuth;
    MutableLiveData<ResultTypeProduct> livedata = new MutableLiveData<>();
    private FirebaseFirestore db;

    public void createProduct (final String name, final String category){
        mAuth= FirebaseAuth.getInstance();


        if(name.isEmpty() && category.isEmpty()){
            livedata.postValue(ResultTypeProduct.CHECKBOTH);
        }else if (name.isEmpty()) {
            livedata.postValue(ResultTypeProduct.CHECKNAME);
        }else if (category.isEmpty()) {
            livedata.postValue(ResultTypeProduct.CHECKCATEGORY);
        }else{
            livedata.postValue(ResultTypeProduct.SUCCESS);

            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            DocumentReference newProduct = db.collection("products").document();
            String user_id = mAuth.getCurrentUser().getUid();

            Product product = new Product(user_id, name, category);

            newProduct.set(product);
        }

    }


}
