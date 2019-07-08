package com.example.apontapp.ResetPassword;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apontapp.Login.MainActivity;
import com.example.apontapp.R;

public class ResetPasswordActivity extends AppCompatActivity {


    private EditText email;
    private Button reset;
    private ResetPasswordViewModel resetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_reset_password );

        resetPasswordViewModel = ViewModelProviders.of ( this ).get ( ResetPasswordViewModel.class );


        //receive states of resetpasswordviewmodel of enum
        resetPasswordViewModel.livedata.observe ( this, new Observer<ResetPasswordViewModel.ResultTypeReset> () {
            @Override
            public void onChanged(@Nullable ResetPasswordViewModel.ResultTypeReset resultTypeReset) {
                switch (resultTypeReset){
                    case SUCCESS:
                        startActivity (new Intent (ResetPasswordActivity.this, MainActivity.class ));
                        Toast.makeText ( ResetPasswordActivity.this, "Processo Concluído! Por favor Verifique o Seu Email.", Toast.LENGTH_LONG ).show ();
                        finish ();
                        break;

                    case EMAILR:
                        Toast.makeText ( ResetPasswordActivity.this, "Email Obrigatório!", Toast.LENGTH_LONG ).show ();
                        break;
                    case ERROR:
                        Toast.makeText ( ResetPasswordActivity.this, "Erro ao Recuperar Password!", Toast.LENGTH_LONG ).show ();

                }
            }
        } );

        email = findViewById ( R.id.resetPass_edit );
        reset = findViewById ( R.id.resetPass_btn );

        reset.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                reset();
            }
        } );
    }

    //get text of inputs and calls viewmodel method
    public void reset(){
        String emailT = email.getText ().toString ();
        resetPasswordViewModel.resetPass ( emailT );
    }
}
