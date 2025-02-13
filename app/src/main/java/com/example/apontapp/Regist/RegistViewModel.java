package com.example.apontapp.Regist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import com.example.apontapp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegistViewModel extends ViewModel {

    //enum to send state to view
    enum ResultTypeRegist {
        SUCCESS, ERROR, CHECKBOTH, COMPARE, VALIDEMAIL, EXISTEMAIL, CHECKPASSLENGT
    }

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    MutableLiveData<RegistViewModel.ResultTypeRegist> liveData = new MutableLiveData<>();

    private FirebaseFirestore db;

    public RegistViewModel(){
        mAuth=FirebaseAuth.getInstance();
    }

    //method of registation and input verifications
    public void regist(final String username, final String password, final String passwordConfirm, final  String email){

        mAuth= FirebaseAuth.getInstance();


        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || password.isEmpty()) {
            liveData.postValue(ResultTypeRegist.CHECKBOTH);
        }else if (password.length()<6) {
            liveData.postValue(ResultTypeRegist.CHECKPASSLENGT);
        } else if(!passwordConfirm.equals(password)) {
            liveData.postValue(ResultTypeRegist.COMPARE);
        } else if(!isValid(email)) {
            liveData.postValue ( ResultTypeRegist.VALIDEMAIL );
        }else{
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    liveData.postValue ( ResultTypeRegist.ERROR );

                                } else {
                                    liveData.postValue(ResultTypeRegist.SUCCESS);
                                    //create user in table of users
                                    db = FirebaseFirestore.getInstance();

                                    //creation of new document in firestore with user data
                                    DocumentReference newUser = db.collection("users").document(mAuth.getCurrentUser().getUid());

                                    User user = new User(username,email);

                                    newUser.set(user);

                                }
                            }
                        });
                    }
                }



    //method that validate email
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }


}
