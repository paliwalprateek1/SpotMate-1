package com.example.prateek.spotmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    EditText user;
    EditText passwd;

    String password=MD5_hash("pandey");
    String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/**
 * This piece of code is executed when the user clicks on the new user icon.
 */
        ImageButton reg=(ImageButton)findViewById(R.id.newUser);
        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent userReg=new Intent(MainActivity.this, Register.class);
                startActivity(userReg);
            }
        });

        user=(EditText)findViewById(R.id.usr);
        passwd=(EditText)findViewById(R.id.pass);
        Button login=(Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                test=MD5_hash(passwd.getText().toString());
                if(user.getText().toString().equals("ayush") && test.equals(password)){
                    Intent i=new Intent(MainActivity.this, home.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(MainActivity.this, test , Toast.LENGTH_SHORT).show();
            }

        });
    }

    public String MD5_hash(String input) {
        try {
            // Create MD5 Hash
            MessageDigest hash = java.security.MessageDigest.getInstance("MD5");
            hash.update(input.getBytes());
            byte hashData[] = hash.digest();

            // Create Hexadecimal Hash String
            StringBuilder hashVal = new StringBuilder();
            for (int i=0; i<hashData.length; i++)
                hashVal.append(Integer.toHexString(0xFF & hashData[i]));
            return hashVal.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
