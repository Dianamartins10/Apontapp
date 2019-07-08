package com.example.apontapp.Spending;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


import com.example.apontapp.Home.HomeActivity;
import com.example.apontapp.Home.HomeViewModel;
import com.example.apontapp.Login.MainActivity;
import com.example.apontapp.Spending.SpendingViewModel;
import com.example.apontapp.Spending.MyAdapter;
import com.example.apontapp.R;

import java.util.ArrayList;

public class SpendingActivity extends AppCompatActivity {

    //initialize variables
    private ArrayList<String> lista = new ArrayList<> ();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SpendingViewModel spendingViewModel=null;
    private Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_spending);

        recyclerView = findViewById ( R.id.recyclerview );
        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager ( layoutManager );

        adapter = new com.example.apontapp.Spending.MyAdapter(lista);
        recyclerView.setAdapter ( adapter );

        calculate = findViewById ( R.id.buttonSpending );

        spendingViewModel = ViewModelProviders.of(this).get (SpendingViewModel.class);

        spendingViewModel.spending ();

        spendingViewModel.livedata.observe ( this, new Observer<SpendingViewModel.ResultTypeView> () {
            @Override
            public void onChanged(@Nullable SpendingViewModel.ResultTypeView resultTypeView) {
                switch (resultTypeView){
                    case SUCCESS:
                        list();
                        break;

                    case ERROR:
                        Toast.makeText(SpendingActivity.this, "Ainda não existem listas criadas!", Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            }
        } );

        spendingViewModel.lista.observe ( this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> strings) {
                adapter.updateDataset(strings);
            }
        } );
    }

    public void list(){
        //spendingViewModel.spending(lista);
    }

    //creates options menu of topbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    //redirect if click on items
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
    //method thats redirect to spending activity
    private void enterGastos() {
        startActivity(new Intent(SpendingActivity.this, SpendingActivity.class));
    }

    //method that calls logout in viewmodel
    private void logoutUser() {
        finishAffinity();
        startActivity(new Intent(SpendingActivity.this, MainActivity.class));
        spendingViewModel.logout ();
        finish();

    }
}
