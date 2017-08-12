package com.example.stream.jx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.stream.stream_core.app.StreamCore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(StreamCore.getApplicationContext(), "The Context has been arrived", Toast.LENGTH_LONG).show();
    }
}
