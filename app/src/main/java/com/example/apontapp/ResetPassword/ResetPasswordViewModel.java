package com.example.apontapp.ResetPassword;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResetPasswordViewModel extends ViewModel {
    //enum to send state to view
    enum ResultTypeReset{
        SUCCESS, ERROR, EMAILR
    }

    MutableLiveData<ResultTypeReset> livedata = new MutableLiveData<>();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public ResetPasswordViewModel(){
        mAuth= FirebaseAuth.getInstance ();
    }


    //method that reset password
    public void resetPass(final String email){
        if (email.isEmpty ()){
            livedata.postValue ( ResultTypeReset.EMAILR );
        }else{
            mAuth.sendPasswordResetEmail ( email ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful ()){
                        livedata.postValue ( ResultTypeReset.SUCCESS );
                    }
                }
            } );


        }
    }


}
