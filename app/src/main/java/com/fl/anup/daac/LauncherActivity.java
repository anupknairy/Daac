package com.fl.anup.daac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.fl.anup.daac.db.AppDatabase;

public class LauncherActivity extends AppCompatActivity {

    TextView initializationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public LauncherActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInitializationText();

        boolean isDomainRegistered = AppDatabase.getInstance(getApplicationContext()).isDomainInitialized();

        initializationTxt.setVisibility(View.GONE);

        if(isDomainRegistered) {
            showCurrentDomainToContinue();
        } else {
            showActivitytoRegisterNewDomain();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        initializationTxt = (TextView)findViewById(R.id.initializing_txt);
        initializationTxt.setText("Loading...");
    }

    private void showActivitytoRegisterNewDomain() {
        Intent intent= new Intent(this,SetDomainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showCurrentDomainToContinue() {

        String domainName = AppDatabase.getInstance(getApplicationContext()).getDomainName();

        TextView currentDomainTxt = (TextView)findViewById(R.id.currentDomain_txt);
        currentDomainTxt.setText(domainName);
        currentDomainTxt.setVisibility(View.VISIBLE);

        Button continueBtn = (Button)findViewById(R.id.continue_btn);
        Button resetBtn = (Button)findViewById(R.id.reset_btn);

        continueBtn.setVisibility(View.VISIBLE);
        resetBtn.setVisibility(View.VISIBLE);


    }

    public void showLoginActivity(View view) {
        Intent intent = new Intent(this,UserLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showResetDomainActivity(View v) {
        showActivitytoRegisterNewDomain();
    }
}
