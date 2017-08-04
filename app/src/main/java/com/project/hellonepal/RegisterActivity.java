package com.project.hellonepal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email, address, phone;

    AQuery aQuery;

    String url = "http://192.168.100.15:88/ourservice/service.php?task=insertUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);

        aQuery = new AQuery(this);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processRegister();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    public void processRegister() {
        String usernameValue = username.getText().toString();
        String passwordValue = password.getText().toString();
        String emailValue = email.getText().toString();
        String addressValue = address.getText().toString();
        String phoneValue = phone.getText().toString();


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", usernameValue);
        params.put("password", passwordValue);
        params.put("email", emailValue);
        params.put("address", addressValue);
        params.put("phone", phoneValue);

        aQuery.ajax(url, params, org.json.JSONObject.class, new AjaxCallback<org.json.JSONObject>() {
            @Override
            public void callback(String url, org.json.JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("response", url + " response" + object);
                try {
                    if (object.getString("message").contains("User Inserted"))
                        Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
