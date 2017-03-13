package com.fl.anup.daac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fl.anup.daac.db.AppDatabase;

public class SetDomainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_domain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button setDomainBtn = (Button)findViewById(R.id.setdomain_btn);
        setDomainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText domain = (EditText)findViewById(R.id.domain);
                String domainName = domain.getText().toString();

                try {
                    AppDatabase.getInstance().setDomainName(domainName);
                    showLoginActivity();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Invalid URI",Toast.LENGTH_SHORT);
                    domain.setText("");
                }
            }
        });
    }

    private void showLoginActivity() {
        Intent intent = new Intent(this,UserLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
