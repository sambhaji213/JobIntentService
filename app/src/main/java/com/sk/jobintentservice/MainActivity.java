package com.sk.jobintentservice;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static com.sk.jobintentservice.JobService.SHOW_RESULT;

public class MainActivity extends AppCompatActivity implements ServiceResultReceiver.Receiver {

    private ServiceResultReceiver mServiceResultReceiver;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceResultReceiver = new ServiceResultReceiver(new Handler());
        mServiceResultReceiver.setReceiver(this);
        mTextView = findViewById(R.id.textView);
        showDataFromBackground(MainActivity.this, mServiceResultReceiver);
    }

    private void showDataFromBackground(MainActivity mainActivity, ServiceResultReceiver mResultReceiver) {
        JobService.enqueueWork(mainActivity, mResultReceiver);
    }

    public void showData(String data) {
        mTextView.setText(String.format("%s\n%s", mTextView.getText(), data));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case SHOW_RESULT:
                if (resultData != null) {
                    showData(resultData.getString("data"));
                }
                break;
        }
    }
}
