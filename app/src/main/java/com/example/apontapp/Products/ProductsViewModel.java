package com.example.apontapp.Products;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.apontapp.Home.MyAdapter;
import com.example.apontapp.Models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class ProductsViewModel extends ViewModel {

    //enum to pass to producst activity
    enum ResultTypeListProd{
        SUCCESS, ERROR, LOGOUT
    }

    MutableLiveData<ResultTypeListProd> livedata = new MutableLiveData<>();
    MutableLiveData<ArrayList<String>> name = new MutableLiveData<> ();



    ProductsAdapter adapter = null;




    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String TAG ="";
    private ArrayList<String> listaTemp = new ArrayList<> ();

    public ProductsViewModel(){mAuth=FirebaseAuth.getInstance ();}

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        livedata.postValue ( ResultTypeListProd.LOGOUT );
    }

    public void products(){

        mAuth= FirebaseAuth.getInstance();

        String user_id= mAuth.getUid ();

        name.setValue(new ArrayList<String>());

        db=FirebaseFirestore.getInstance ();

        //products in firebase created by no user
        db.collection ( "products" ).whereEqualTo ("user_id","").get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult ()) {

                    listaTemp= name.getValue ();
                    if (task.isSuccessful ()) {


                        Log.d (TAG, "RESULTADO: " + listaTemp);
                        listaTemp.add ( String.valueOf ( document.get ( "name" ) ) );
                        ProductsViewModel.this.name.setValue ( listaTemp );
                        livedata.postValue ( ResultTypeListProd.SUCCESS );
                    }else{
                        livedata.postValue ( ResultTypeListProd.ERROR );
                    }
                }
            }
        } );

        //products in firebase that have been created by the user
        db.collection ( "products" ).whereEqualTo ("user_id",user_id).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult ()) {

                    listaTemp= name.getValue ();
                    if (task.isSuccessful ()) {


                        Log.d (TAG, "RESULTADO: " + listaTemp);
                        listaTemp.add ( String.valueOf ( document.get ( "name" ) ) );
                        ProductsViewModel.this.name.setValue ( listaTemp );
                        livedata.postValue ( ResultTypeListProd.SUCCESS );
                    }else{
                        livedata.postValue ( ResultTypeListProd.ERROR );
                    }
                }
            }
        } );



    }

}
