package com.example.apontapp.Home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    enum ResultTypeView {
        SUCCESS, ERROR
    }

    MutableLiveData<ResultTypeView> livedata = new MutableLiveData<> ();
    MutableLiveData<ArrayList<String>> lista = new MutableLiveData<> ();


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<String> listaTemp = new ArrayList<> ();



    public HomeViewModel(){mAuth=FirebaseAuth.getInstance ();}

    public void home(){
        mAuth= FirebaseAuth.getInstance();

        String user_id= mAuth.getUid ();

        lista.setValue( new ArrayList<String>());

        db=FirebaseFirestore.getInstance ();
        db.collection("lists").whereEqualTo ("user_id",user_id).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful ()) {
                    for (QueryDocumentSnapshot document : task.getResult ()) {
                        listaTemp = lista.getValue ();

                        if (task.isSuccessful ()) {

                            listaTemp.add ( document.get ( "listName" ).toString () );
                            lista.setValue ( listaTemp );
                            livedata.postValue ( ResultTypeView.SUCCESS );
                        }
                        else  {
                            livedata.postValue ( ResultTypeView.ERROR );

                        }
                    }
                }
            }
        } );
    }
}
