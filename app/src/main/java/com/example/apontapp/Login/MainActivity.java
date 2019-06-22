package com.example.apontapp.Login;

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

import com.example.apontapp.Home.HomeActivity;
import com.example.apontapp.R;
import com.example.apontapp.Regist.RegistActivity;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btn_login;
    private Button btn_regist;
    private LoginViewModel loginViewModel= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginViewModel.liveData.observe(this, new Observer<LoginViewModel.ResultType>() {
            @Override
            public void onChanged(@Nullable LoginViewModel.ResultType resultType) {

                switch (resultType){

                    case SUCCESS:
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                        break;

                    case CHECKEMAIL:
                        Toast.makeText(MainActivity.this, "Email Obrigatório!", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case CHECKPASS:
                        Toast.makeText(MainActivity.this, "Password Obrigatória!", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case CHECKBOTH:
                        Toast.makeText(MainActivity.this, "Credênciais Necessárias!", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case ERROR:
                        Toast.makeText(MainActivity.this, "Combinação de Email/Password Errada!", Toast.LENGTH_LONG)
                                .show();
                        break;


                }
            }
        });

        email = findViewById(R.id.editemail);
        password = findViewById(R.id.editpwd);
        btn_login = findViewById(R.id.btn_Login);
        btn_regist = findViewById(R.id.btn_Registar);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newScreen = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(newScreen);
            }
        });
    }

    private void startSignIn(){
        String emailN= email.getText().toString();
        String passwordN= password.getText().toString();
        loginViewModel.login(emailN,passwordN);
    }
}
