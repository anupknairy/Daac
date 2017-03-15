package com.fl.anup.daac;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fl.anup.daac.com.fl.anup.daac.ws.Message;
import com.fl.anup.daac.com.fl.anup.daac.ws.ResponseStatus;
import com.fl.anup.daac.com.fl.anup.daac.ws.WebServiceUtilties;

import static com.fl.anup.daac.R.id.uname;

public class UserLoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private boolean isPassWordRecovery = false;

    // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mUserNameView = (EditText)findViewById(uname);
        mPasswordView = (EditText)findViewById(R.id.password);

    }

    public void doLogin(View v) {

        String uname = mUserNameView.getText().toString();

        if(isPassWordRecovery){
            (new PasswordRecoveryTask(uname)).execute();
            isPassWordRecovery = false;
            return;
        }

        String password = mPasswordView.getText().toString();

        boolean isCancelled = false;
        View focusView = null;

        if(uname == null || uname.isEmpty()) {
            isCancelled = true;
            mUserNameView.setError("User name cannot be empty");
            mUserNameView.requestFocus();
        } else if(password == null || password.isEmpty()) {
            isCancelled = true;
            mPasswordView.setError("Password cannot be empty");
            mPasswordView.requestFocus();
        }

        if(!isCancelled) {
            mAuthTask = new UserLoginTask(uname,password);
            mAuthTask.execute();
        }

    }

    public void registerNewUser(View v) {
        Intent intent = new Intent(this,RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void doPasswordRecovery(View v) {

        ((Button)findViewById(R.id.signup_btn)).setVisibility(View.GONE);
        ((Button)findViewById(R.id.forgot_pwd_btn)).setVisibility(View.GONE);
        ((Button)findViewById(R.id.login_btn)).setText("Recover Password");
        mPasswordView.setVisibility(View.INVISIBLE);

        isPassWordRecovery = true;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Message> {

        private final String mUname;
        private final String mPassword;

        UserLoginTask(String uname, String password) {
            mUname = uname;
            mPassword = password;
        }

        @Override
        protected Message doInBackground(Void... params) {

            Message msg = WebServiceUtilties.doLogin(mUname,mPassword);
            return msg;
        }

        @Override
        protected void onPostExecute(final Message msg) {
            mAuthTask = null;

            if (msg != null) {

                if(msg.getStatus().equals(ResponseStatus.UPDATEPASSWORD.name())) {
                    Intent intent = new Intent(getApplicationContext(),UpdatePasswordActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if(msg.getStatus().equals(ResponseStatus.FAILURE.name())) {
                    Toast.makeText(getApplicationContext(),
                            msg.getAddLogs().get("errorMessage"),Toast.LENGTH_LONG).show();

                } else if(msg.getStatus().equals(ResponseStatus.SUCCESS.name())) {
                    Intent intent = new Intent(getApplicationContext(),FileBrowserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("userName",msg.getUserName());
                    intent.putExtra("userId",msg.getUserId());
                    startActivity(intent);
                }

            } else {
                Toast.makeText(getApplicationContext(),
                        "Error",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    @Override
    protected void onStop() {
        mUserNameView.setText("");
        mPasswordView.setText("");
        mUserNameView.requestFocus();
        super.onStop();
    }


    public class PasswordRecoveryTask extends AsyncTask<Void, Void, Message> {

        private final String mUname;

        PasswordRecoveryTask(String uname) {
            mUname = uname;
        }

        @Override
        protected Message doInBackground(Void... params) {

            Message msg = WebServiceUtilties.recoveryPassword(mUname);
            return msg;
        }

        @Override
        protected void onPostExecute(final Message msg) {
            mAuthTask = null;

            if (msg != null) {

                if(msg.getStatus().equals(ResponseStatus.FAILURE.name())) {
                    Toast.makeText(getApplicationContext(),
                            msg.getAddLogs().get("errorMessage"),Toast.LENGTH_LONG).show();

                } else if(msg.getStatus().equals(ResponseStatus.SUCCESS.name())) {

                    Toast.makeText(getApplicationContext(),
                            msg.getAddLogs().get("success"),Toast.LENGTH_LONG).show();

                    ((Button)findViewById(R.id.signup_btn)).setVisibility(View.VISIBLE);
                    ((Button)findViewById(R.id.forgot_pwd_btn)).setVisibility(View.VISIBLE);
                    ((Button)findViewById(R.id.login_btn)).setText("Login");
                    mPasswordView.setVisibility(View.VISIBLE);

                    isPassWordRecovery = true;
                }

            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
