package com.fl.anup.daac;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fl.anup.daac.com.fl.anup.daac.ws.Message;
import com.fl.anup.daac.com.fl.anup.daac.ws.ResponseStatus;
import com.fl.anup.daac.com.fl.anup.daac.ws.WebServiceUtilties;

import java.io.File;

/**
 * Created by Anup on 3/15/2017.
 */

public class FileBrowserActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_PICK_FILE = 1;

    private TextView mfilePath_view;
    private Button mBrowseBtn;
    private Button mUploadBtn;
    private String mSelectedFilePath;
    private String mUserName;
    private String mUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);

        mfilePath_view = (TextView)findViewById(R.id.file_path);
        mBrowseBtn = (Button)findViewById(R.id.browse);
        mBrowseBtn.setOnClickListener(this);

        mUploadBtn = (Button)findViewById(R.id.upload);
        mUploadBtn.setEnabled(false);

        mUserName = getIntent().getStringExtra("userName");
        mUserId = getIntent().getStringExtra("userId");

        TextView activityText = (TextView)findViewById(R.id.activity_text);
        activityText.setText("Hi "+ mUserName +", Upload the file to cloud");

    }

    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.browse:
                Intent intent = new Intent(this, FilePicker.class);
                startActivityForResult(intent, REQUEST_PICK_FILE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            switch(requestCode) {

                case REQUEST_PICK_FILE:

                    if(data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        mSelectedFilePath = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH)).getPath();
                        mfilePath_view.setText(mSelectedFilePath);
                        if(mSelectedFilePath != null) {
                            mUploadBtn.setEnabled(true);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you like to Logout?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),
                                "Logged out successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void uploadFile(View v) {
        if(mSelectedFilePath == null) {
            Toast.makeText(getApplicationContext(),
                    "No File selected!",Toast.LENGTH_LONG).show();
            mBrowseBtn.requestFocus();
            return;
        }
        try {
            (new UploadFileTask()).execute();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }
    }

    public class UploadFileTask extends AsyncTask<Void, Void, Message> {




        @Override
        protected Message doInBackground(Void... params) {

            Message msg = WebServiceUtilties.uploadFile(mUserId, mSelectedFilePath);
            return msg;
        }

        @Override
        protected void onPostExecute(final Message msg) {

            if (msg != null) {

                if(msg.getStatus().equals(ResponseStatus.FAILURE.name())) {
                    Toast.makeText(getApplicationContext(),
                            msg.getAddLogs().get("errorMessage"),Toast.LENGTH_LONG).show();

                } else if(msg.getStatus().equals(ResponseStatus.SUCCESS.name())) {

                    Toast.makeText(getApplicationContext(),
                            msg.getAddLogs().get("Uploaded Successfully"),Toast.LENGTH_LONG).show();
                    mSelectedFilePath = null;
                    mfilePath_view.setText("");
                }

            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }
}
