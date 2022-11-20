package com.gabriel.augusto.emptio;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gabriel.augusto.emptio.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.gabriel.augusto.emptio.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}