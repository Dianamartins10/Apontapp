package com.example.apontapp.EditList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class EditListViewModel extends ViewModel {

    //enum to send state to view
    enum ResultTypeList{
        SUCCESS, CHECKNAME, LOGOUT,ERROR
    }


    MutableLiveData<EditListViewModel.ResultTypeList> livedata = new MutableLiveData<>();
    MutableLiveData<EditListViewModel.ResultTypeList> logout = new MutableLiveData<> ();

    //variables initialization
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public EditListViewModel(){
        mAuth = FirebaseAuth.getInstance();
    }

    //logout method
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        livedata.postValue ( EditListViewModel.ResultTypeList.LOGOUT );
    }


    //name of list field on firebase
    private static final String NAME_KEY = "listName";

    public void editList(final String nameList, final String currentList) {


            mAuth = FirebaseAuth.getInstance ();

            if (nameList.isEmpty ()) {
                livedata.postValue ( EditListViewModel.ResultTypeList.CHECKNAME );
            } else if (!nameList.isEmpty ()) {


                livedata.postValue ( EditListViewModel.ResultTypeList.SUCCESS );
                db = FirebaseFirestore.getInstance ();

                //get data from firestore and send to arraylist
                db.collection ( "lists" )
                        .get ()
                        .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful ()) {
                                    for (QueryDocumentSnapshot document : task.getResult ()) {
                                        //Log.d("", document.getId() + " => " + document.getString("listName"));

                                        if (document.getString ( "listName" ).equals ( currentList )) {
                                            DocumentReference lists = db.collection ( "lists" ).document ( document.getId () );
                                            lists.update ( NAME_KEY, nameList );
                                        }
                                    }
                                } else {
                                    Log.d ( "", "Error getting documents: ", task.getException () );
                                }
                            }
                        } );
        }
    }
}
