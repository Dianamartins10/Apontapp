package com.example.apontapp.ProductsByList;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import android.arch.lifecycle.ViewModel;

import com.example.apontapp.ProductsByList.ProductByListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

        name.setValue(new ArrayList<String>());

        db=FirebaseFirestore.getInstance ();


        db.collection ( "products" ).whereEqualTo ("user_id","").get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult ()) {

                    listaTemp= name.getValue ();
                    if (task.isSuccessful ()) {


                        Log.d (TAG, "RESULTADO: " + listaTemp);
                        listaTemp.add ( String.valueOf ( document.get ( "name" ) ) );
                        ProductByListViewModel.this.name.setValue ( listaTemp );
                        livedata.postValue ( ResultTypeListProd.SUCCESS );
                    }else{
                        livedata.postValue ( ResultTypeListProd.ERROR );
                    }
                }
            }
        } );

        db.collection ( "products" ).whereEqualTo ("user_id",user_id).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult ()) {

                    listaTemp= name.getValue ();
                    if (task.isSuccessful ()) {


                        Log.d (TAG, "RESULTADO: " + listaTemp);
                        listaTemp.add ( String.valueOf ( document.get ( "name" ) ) );
                        ProductByListViewModel.this.name.setValue ( listaTemp );
                        livedata.postValue ( ResultTypeListProd.SUCCESS );
                    }else{
                        livedata.postValue ( ResultTypeListProd.ERROR );
                    }
                }
            }
        } );

    }

}

