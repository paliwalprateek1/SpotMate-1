package com.example.prateek.spotmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText uname;
    EditText usr;
    EditText ph;
    EditText key;
    EditText rekey;
    String username;
    String userid;
    String phone;
    String passwd;
    String repasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uname = (EditText) findViewById(R.id.name);
        usr = (EditText) findViewById(R.id.uid);
        ph = (EditText) findViewById(R.id.phno);
        key = (EditText) findViewById(R.id.pass);
        rekey = (EditText) findViewById(R.id.repass);


        Button regist = (Button) findViewById(R.id.reg);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = uname.getText().toString();
                userid = usr.getText().toString();
                phone = ph.getText().toString();
                passwd = key.getText().toString();
                repasswd = rekey.getText().toString();

                if (username.length() == 0 || userid.length() == 0 || phone.length() == 0 || passwd.length() == 0 || repasswd.length() == 0) {
                    Toast.makeText(Register.this, "None of the fields can be left blank", Toast.LENGTH_SHORT).show();
                } else if (passwd.equals(repasswd)) {
                    Intent reg = new Intent(Register.this, home.class);
                    startActivity(reg);
                } else {
                    Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}



