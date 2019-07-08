package com.example.apontapp.Login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    //enum to send state to view
    enum ResultType {
        SUCCESS, ERROR, CHECKBOTH, CHECKEMAIL, CHECKPASS
    }

    MutableLiveData<ResultType> liveData = new MutableLiveData<>();
    MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //method thats verify current user
    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if(currentUser !=null){
                    LoginViewModel.this.currentUser.postValue(currentUser);
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);

    }

    //method of login and input verifications
    public void login(String email, String password){
        if (email.isEmpty() && password.isEmpty()) {
            liveData.postValue(ResultType.CHECKBOTH);
        } else if (password.isEmpty()) {
            liveData.postValue(ResultType.CHECKPASS);
        } else if (email.isEmpty()) {
            liveData.postValue(ResultType.CHECKEMAIL);
        } else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        liveData.postValue(ResultType.ERROR);
                    } else {
                        liveData.postValue(ResultType.SUCCESS);
                    }
                }
            });

        }
    }
}
