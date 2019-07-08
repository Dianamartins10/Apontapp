package com.example.apontapp.EditList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apontapp.Home.HomeActivity;
import com.example.apontapp.EditList.EditListActivity;
import com.example.apontapp.EditList.EditListViewModel;
import com.example.apontapp.Login.MainActivity;
import com.example.apontapp.NewList.NewListActivity;
import com.example.apontapp.R;
import com.example.apontapp.Spending.SpendingActivity;

public class EditListActivity extends AppCompatActivity {

    public EditText nameList;
    public Button editList;
    private EditListViewModel editListViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_list );

        editListViewModel = ViewModelProviders.of(this).get(EditListViewModel.class);

        editListViewModel.livedata.observe ( this, new Observer<EditListViewModel.ResultTypeList>() {
            @Override
            public void onChanged(@Nullable EditListViewModel.ResultTypeList resultTypeList) {
                switch(resultTypeList){
                    case CHECKNAME:
                        Toast.makeText(EditListActivity.this, "Nome da Lista Obrigatório!", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case SUCCESS:
                        startActivity(new Intent(EditListActivity.this, HomeActivity.class));
                        Toast.makeText(EditListActivity.this, "A sua Lista foi Editada com Sucesso!", Toast.LENGTH_LONG)
                                .show();
                        finish();
                        break;

                }
            }

        } );

        editList= findViewById(R.id.btn_EditList);
        nameList= findViewById(R.id.editList);

        editList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editList();
            }
        });
    }
    private void editList(){
        String name = nameList.getText().toString();
        Intent intent = getIntent();
        String currentlist = intent.getStringExtra("name");
        editListViewModel.editList (name,currentlist);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutUser();
                return true;
            case R.id.action_gastos:
                enterGastos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void enterGastos() {
        startActivity(new Intent(EditListActivity.this, SpendingActivity.class));
    }

    private void logoutUser() {
        startActivity(new Intent(EditListActivity.this, MainActivity.class));
        editListViewModel.logout ();
        finish();

    }
}
