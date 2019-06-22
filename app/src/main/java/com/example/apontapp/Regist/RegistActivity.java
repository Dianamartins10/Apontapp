package com.example.apontapp.Regist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import com.example.apontapp.R;

public class RegistActivity extends AppCompatActivity {

    public Button btn_Registar;
    public EditText name;
    public EditText email;
    public EditText password;
    public EditText passwordConfirm;
    private RegistViewModel RegistViewModel= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);




    }
}
