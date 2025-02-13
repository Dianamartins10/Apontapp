package com.example.apontapp.NewList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.apontapp.Models.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewListViewModel extends ViewModel {

    //enum to send state to view
    enum ResultTypeList{
        SUCCESS, CHECKNAME, LOGOUT
    }

    private FirebaseAuth mAuth;
    MutableLiveData<ResultTypeList> livedata = new MutableLiveData<>();
    MutableLiveData<ResultTypeList> logout = new MutableLiveData<> ();
    private FirebaseFirestore db;

    public NewListViewModel(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        livedata.postValue ( ResultTypeList.LOGOUT );
    }


    //method thats create a new list based in list Model
    public void createList(final String nameList){
        mAuth= FirebaseAuth.getInstance();

        if(nameList.isEmpty()){
            livedata.postValue(ResultTypeList.CHECKNAME);
        }else if (!nameList.isEmpty()) {

            livedata.postValue(ResultTypeList.SUCCESS);

            db = FirebaseFirestore.getInstance();
            DocumentReference newList = db.collection("lists").document();
            String user_id = mAuth.getCurrentUser().getUid();

            final ArrayList<String> products;
            products = new ArrayList<>();

            List list = new List(user_id, nameList, products);

        newList.set(list);
    }
    }

}
