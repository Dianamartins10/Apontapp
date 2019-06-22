package com.example.apontapp.NewList;

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

public class NewListActivity extends AppCompatActivity {

    public EditText nameList;
    public Button createList;
    private NewListViewModel newListViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_new_list );


        newListViewModel = ViewModelProviders.of(this).get(NewListViewModel.class);

        newListViewModel.livedata.observe ( this, new Observer<NewListViewModel.ResultTypeList> () {
            @Override
            public void onChanged(@Nullable NewListViewModel.ResultTypeList resultTypeList) {
                switch(resultTypeList){
                    case CHECKNAME:
                        Toast.makeText(NewListActivity.this, "Nome da Lista Obrigatório!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case SUCCESS:
                        startActivity(new Intent (NewListActivity.this, HomeActivity.class));
                        Toast.makeText(NewListActivity.this, "A sua Lista foi Criada com Sucesso!", Toast.LENGTH_LONG)
                                .show();
                        finish();
                        break;

                }
            }
        } );

        createList= findViewById(R.id.btn_NewList);
        nameList= findViewById(R.id.editNewList);

        createList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList();
            }
        });
    }

    private void createList(){
        String name = nameList.getText().toString();
        newListViewModel.createList (name);
    }
}
