package com.fl.anup.daac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fl.anup.daac.db.AppDatabase;

public class LauncherActivity extends AppCompatActivity {

    TextView initilizationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
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

        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();

        showInitializationText();

        boolean isDomainRegistered = AppDatabase.getInstance(getApplicationContext()).isDomainInitialized();

        initilizationTxt.setVisibility(View.GONE);

        if(isDomainRegistered) {
            showCurrentDomainToContinue();
        } else {
            showActivitytoRegisterNewDomain();
        }

        Button continueBtn = (Button)findViewById(R.id.continue_btn);
        Button resetBtn = (Button)findViewById(R.id.reset_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginActivity();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResetDomainActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInitializationText() {
        initilizationTxt = (TextView)findViewById(R.id.initializing_txt);
        initilizationTxt.setText("Loading...");
    }

    private void showActivitytoRegisterNewDomain() {
        Intent intent= new Intent(this,SetDomainActivity.class);
        startActivity(intent);
    }

    private void showCurrentDomainToContinue() {

        String domainName = AppDatabase.getInstance(getApplicationContext()).getDomainName();

        TextView currentDomainTxt = (TextView)findViewById(R.id.currentDomain_txt);
        currentDomainTxt.setText(domainName);
        currentDomainTxt.setVisibility(View.VISIBLE);
    }

    private void showResetDomainActivity() {

    }

    private void showLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
