package com.example.apontapp.ProductsByList;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import android.arch.lifecycle.ViewModel;

import com.example.apontapp.Products.ProductsViewModel;
import com.example.apontapp.ProductsByList.ProductByListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductByListViewModel extends ViewModel {

    enum ResultTypeListProd{
        SUCCESS, ERROR, LOGOUT
    }

    MutableLiveData<ResultTypeListProd> livedata = new MutableLiveData<>();
    MutableLiveData<ArrayList<String>> name = new MutableLiveData<> ();
    MutableLiveData<ArrayList<String>> category= new MutableLiveData<> ();
    MutableLiveData<ResultTypeListProd> logout = new MutableLiveData<> ();


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String TAG ="";
    private ArrayList<String> listaTemp = new ArrayList<> ();


    public ProductByListViewModel(){mAuth=FirebaseAuth.getInstance ();}

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        livedata.postValue ( ResultTypeListProd.LOGOUT );
    }

    public void productByList(){

        mAuth= FirebaseAuth.getInstance();

        String user_id= mAuth.getUid ();
        String test = ProductByListActivity.namelist;

        name.setValue(new ArrayList<String>());

        db=FirebaseFirestore.getInstance ();


        db.collection ( "lists" ).whereEqualTo ("user_id",user_id).whereEqualTo("listName",test).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult ()) {

                    listaTemp= name.getValue ();
                    if (task.isSuccessful ()) {


                        Log.d (TAG, "RESULTADO: " + listaTemp);
                        String[] tempItems = String.valueOf ( document.get ( "products" ) ).split(", ");
                        for (int i = 0, size = tempItems.length; i < size; i++) {
                            listaTemp.add(tempItems[i].replace("[", "").replace("]", ""));
                        }

                        ProductByListViewModel.this.name.setValue ( listaTemp );
                        livedata.postValue ( ProductByListViewModel.ResultTypeListProd.SUCCESS );
                    }else{
                        livedata.postValue ( ProductByListViewModel.ResultTypeListProd.ERROR );
                    }
                }
            }
        } );


    }

}

