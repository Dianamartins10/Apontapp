package com.example.apontapp.Regist;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apontapp.Home.HomeActivity;
import com.example.apontapp.R;

public class RegistActivity extends AppCompatActivity {

    public Button btn_Registar;
    public EditText name;
    public EditText email;
    public EditText password;
    public EditText passwordConfirm;
    private RegistViewModel registViewModel=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        registViewModel = ViewModelProviders.of(this).get(RegistViewModel.class);


        registViewModel.liveData.observe(this, new Observer<RegistViewModel.ResultTypeRegist> () {
            @Override
            public void onChanged(RegistViewModel.ResultTypeRegist resultTypeRegist) {

                switch (resultTypeRegist) {
                    case SUCCESS:
                        startActivity(new Intent (RegistActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case CHECKNAME:
                        Toast.makeText(RegistActivity.this, "Nome Obrigatório!", Toast.LENGTH_LONG)
                                .show();
                    case CHECKEMAIL:
                        Toast.makeText(RegistActivity.this, "Email Obrigatório!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case CHECKPASS:
                        Toast.makeText(RegistActivity.this, "Password Obrigatória!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case CHECKPASSLENGT:
                        Toast.makeText(RegistActivity.this, "A Password Deve Conter no Mínimo 6 Caracteres!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case CHECKPASSCONFIRM:
                        Toast.makeText(RegistActivity.this, "As Passwords Não Coincidem!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case CHECKBOTH:
                        Toast.makeText(RegistActivity.this, "Todos os campos são de preenchimento obrigatório!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case VALIDEMAIL:
                        Toast.makeText(RegistActivity.this, "Email Inválido", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case EXISTEMAIL:
                        Toast.makeText(RegistActivity.this, "O Email que Inseriu Já se Encontra Registado! ", Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            }
        });

        btn_Registar= (Button) findViewById(R.id.btn_Regist);
        name= (EditText) findViewById(R.id.editName);
        email= (EditText) findViewById(R.id.editEmail);
        password= (EditText) findViewById(R.id.editPass);
        passwordConfirm= (EditText) findViewById(R.id.editPassConfirm);
        btn_Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });

    }

    private void startSignUp(){
        String emailR = email.getText().toString();
        String passwordR = password.getText().toString();
        String passwordConfirmR = passwordConfirm.getText().toString();
        String nameR = name.getText().toString();
        registViewModel.regist(nameR,passwordR,passwordConfirmR,emailR );
    }
}
