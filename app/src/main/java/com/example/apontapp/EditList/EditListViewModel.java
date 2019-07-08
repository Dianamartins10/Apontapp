package com.example.apontapp.EditList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.apontapp.Models.List;
import com.example.apontapp.NewList.NewListViewModel;
import com.example.apontapp.ProductsByList.ProductByListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditListViewModel extends ViewModel {

    enum ResultTypeList{
        SUCCESS, CHECKNAME, LOGOUT
    }

    private FirebaseAuth mAuth;
    MutableLiveData<EditListViewModel.ResultTypeList> livedata = new MutableLiveData<>();
    MutableLiveData<EditListViewModel.ResultTypeList> logout = new MutableLiveData<> ();
    private FirebaseFirestore db;

    public EditListViewModel(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        livedata.postValue ( EditListViewModel.ResultTypeList.LOGOUT );
    }

    public void editList(final String nameList, final String currentList){
        mAuth= FirebaseAuth.getInstance();

        if(nameList.isEmpty()){
            livedata.postValue(EditListViewModel.ResultTypeList.CHECKNAME);
        }else if (!nameList.isEmpty()) {

            livedata.postValue(EditListViewModel.ResultTypeList.SUCCESS);


            /*
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference();
            Query query = reference.child("lists").orderByChild("listName").equalTo(currentList);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = nodeDataSnapshot.getKey(); //
                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("listName", nameList);
                    reference.child(path).updateChildren(result);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
            */

        }
    }
}
