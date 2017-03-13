package com.fl.anup.daac;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fl.anup.daac.com.fl.anup.daac.models.User;
import com.fl.anup.daac.com.fl.anup.daac.ws.Message;
import com.fl.anup.daac.com.fl.anup.daac.ws.ResponseStatus;
import com.fl.anup.daac.com.fl.anup.daac.ws.WebServiceUtilties;

public class UpdatePasswordActivity extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        userId = getIntent().getStringExtra("userId");
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void updatePassword(View v){
        EditText pwd_view = (EditText)findViewById(R.id.password_view);
        EditText confirm_pwd_view = (EditText)findViewById(R.id.confirm_password_view);
        String pwd = pwd_view.getText().toString();
        String confirm_pwd = confirm_pwd_view.getText().toString();

        if(pwd == null || pwd.isEmpty()) {
            pwd_view.setError("Cannot be empty");
            return;
        } else if(confirm_pwd == null || confirm_pwd.isEmpty()) {
            confirm_pwd_view.setError("Cannot be empty");
            return;
        }

        if(!pwd.equals(confirm_pwd)) {
            pwd_view.setText("");
            confirm_pwd_view.setText("");

            Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_LONG).show();

            return;
        }

        (new UpdatePasswordTask(pwd)).execute();


    }

    public class UpdatePasswordTask extends AsyncTask<Void,Void,Message> {

        String pwd;
        public UpdatePasswordTask(String password) {
            pwd = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Message msg) {
            super.onPostExecute(msg);

            if(msg==null){
                Toast.makeText(getApplicationContext(),
                        "Error communicating with server",Toast.LENGTH_LONG).show();
                return;
            }

            if(ResponseStatus.SUCCESS.name().equals(msg.getStatus())) {
                Toast.makeText(getApplicationContext(),
                        "Password Updated successfully",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),UserLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();
            } else{
                Toast.makeText(getApplicationContext(),
                        msg.getAddLogs().get("errorMessage"),Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Message doInBackground(Void... params) {

            return WebServiceUtilties.updatePassword(userId,pwd);
        }
    }
}
