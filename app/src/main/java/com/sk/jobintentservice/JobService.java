package com.sk.jobintentservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

/**
 * Created by Sambhaji Karad on 01/11/18.
 */

public class JobService extends JobIntentService {

    private static final String TAG = JobService.class.getSimpleName();
    public static final String RECEIVER = "receiver";
    public static final int SHOW_RESULT = 123;
    /**
     * Result receiver object to send results
     */
    private ResultReceiver mResultReceiver;
    /**
     * Unique job ID for this service.
     */
    static final int DOWNLOAD_JOB_ID = 1000;
    /**
     * Actions download
     */
    private static final String ACTION_DOWNLOAD = "action.DOWNLOAD_DATA";

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, ServiceResultReceiver workerResultReceiver) {
        Intent intent = new Intent(context, JobService.class);
        intent.putExtra(RECEIVER, workerResultReceiver);
        intent.setAction(ACTION_DOWNLOAD);
        enqueueWork(context, JobService.class, DOWNLOAD_JOB_ID, intent);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork() called with: intent = [" + intent + "]");
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_DOWNLOAD:
                    mResultReceiver = intent.getParcelableExtra(RECEIVER);
                    for(int i=0;i<10;i++){
                        try {
                            Thread.sleep(1000);
                            Bundle bundle = new Bundle();
                            bundle.putString("data",String.format("Showing From JobIntent Service %d", i));
                            mResultReceiver.send(SHOW_RESULT, bundle);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }
}