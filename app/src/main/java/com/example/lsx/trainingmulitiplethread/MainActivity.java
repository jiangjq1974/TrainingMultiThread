package com.example.lsx.trainingmulitiplethread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageview;
    private Button mLoadimagebutton;
    private Button mToastbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageview = (ImageView) findViewById(R.id.Main_Activity_Image_View);
        mLoadimagebutton = (Button) findViewById(R.id.Main_Activity_Load_Image_Button);
        mToastbutton = (Button) findViewById(R.id.Main_Activity_Toast_Button);
        mToastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "XXXXXXXXXXXXXXX", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showimage();
            }
        });
    }

    private void showimage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mImageview.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageview.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }
}
