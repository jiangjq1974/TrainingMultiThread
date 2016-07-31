package com.example.lsx.trainingmulitiplethread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
                new LoadImageTask().execute();
            }
        });
    }

    private void showimage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                sleep();
                mImageview.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageview.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class LoadImageTask extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            for (int i = 0; i <11 ; i++) {
                sleep();
                publishProgress(i*10);
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            Log.d(TAG, "Thread: "+Thread.currentThread().getName());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageview.setImageBitmap(bitmap);
            Log.d(TAG, "Thread: "+Thread.currentThread().getName());
            mProgressbar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressbar.setProgress(values[0]);
        }
    }
}
