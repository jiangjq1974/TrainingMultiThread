package com.example.lsx.trainingmulitiplethread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView mImageview;
    private Button mLoadimagebutton;
    private Button mToastbutton;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageview = (ImageView) findViewById(R.id.Main_Activity_Image_View);
        mLoadimagebutton = (Button) findViewById(R.id.Main_Activity_Load_Image_Button);
        mToastbutton = (Button) findViewById(R.id.Main_Activity_Toast_Button);
        mProgressbar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);
        mToastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "XXXXXXXXXXXXXXX", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg1 = mHandler.obtainMessage();
                        msg1.what = 0;
                        mHandler.sendMessage(msg1);
                        for (int i = 0; i < 11; i++) {
                            sleep();
                            Message msg2 = mHandler.obtainMessage();
                            msg2.what = 1;
                            msg2.obj = i * 10;
                            mHandler.sendMessage(msg2);
                        }
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                        Message msg3 = mHandler.obtainMessage();
                        msg3.what = 2;
                        msg3.obj = bitmap;
                        mHandler.sendMessage(msg3);
                    }
                }).start();
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mProgressbar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mProgressbar.setProgress((Integer) msg.obj);
                    /*if ((Integer) msg.obj == 100) {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                        mImageview.setImageBitmap(bitmap);*/
                    break;
                case 2:
                    mImageview.setImageBitmap((Bitmap) msg.obj);
                    mProgressbar.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


