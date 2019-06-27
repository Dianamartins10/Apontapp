package com.example.apontapp.Home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeViewModel extends ViewModelProviders {

    enum ResultTypeView {
        SUCCESS, ERROR
    }

    MutableLiveData<ResultTypeView> livedata = new MutableLiveData<> ();
    MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


}
