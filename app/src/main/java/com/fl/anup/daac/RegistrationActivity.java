package com.fl.anup.daac;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fl.anup.daac.com.fl.anup.daac.models.User;
import com.fl.anup.daac.com.fl.anup.daac.ws.Message;
import com.fl.anup.daac.com.fl.anup.daac.ws.ResponseStatus;
import com.fl.anup.daac.com.fl.anup.daac.ws.WebServiceUtilties;

public class RegistrationActivity extends AppCompatActivity {

    User user;
    EditText mUserName;
    EditText mEmail;
    EditText mPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUserName = (EditText)findViewById(R.id.name);
        mEmail = (EditText)findViewById(R.id.email);
        mPhoneNum = (EditText)findViewById(R.id.phone);
        mUserName.requestFocus();

        user = new User();
        user.setUserType("patient"); //default
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void onUserTypeClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.patient:
                if (checked)
                    user.setUserType("patient");
                    break;
            case R.id.doctor:
                if (checked)
                    user.setUserType("doctor");
                    break;
            default: break;
        }
    }

    public void createUser(View v) {

        String userName = mUserName.getText().toString();
        String email = mEmail.getText().toString();
        String phoneNum = mPhoneNum.getText().toString();

        boolean isValid=true;

        if(userName==null || userName.isEmpty()){
            isValid=false;
            mUserName.setError("User name cannot be empty");
            mUserName.requestFocus();
        } else  if(email==null || email.isEmpty()){
            isValid=false;
            mEmail.setError("Email cannot be empty");
            mEmail.requestFocus();
        } else  if(phoneNum==null || phoneNum.isEmpty()){
            isValid=false;
            mPhoneNum.setError("Phone number cannot be empty");
            mPhoneNum.requestFocus();
        }

        if(isValid) {
            user.setUserName(userName);
            user.seteMail(email);
            user.setPhoneNum(phoneNum);

            (new RegistrationTask(user)).execute();
        }

    }

    public class RegistrationTask extends AsyncTask<Void,Void,Message> {

        User userForm;
        public RegistrationTask(User user) {
            userForm = user;
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
                        "User created successfully",Toast.LENGTH_LONG).show();

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

            return WebServiceUtilties.createUser(user);
        }
    }
}
