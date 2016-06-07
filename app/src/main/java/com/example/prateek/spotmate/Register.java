package com.example.prateek.spotmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button regist=(Button)findViewById(R.id.reg);
            regist.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                        Intent i = new Intent(Register.this, home.class);
                        startActivity(i);
                    }
            });
        }

}
