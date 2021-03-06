package com.project.hellonepal;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    EditText username, password;

    String url = "https://subhamdhakal23.000webhostapp.com/service.php?task=login";
    SharedPreferences preferences;

    AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        aQuery = new AQuery(this);
        preferences = getSharedPreferences("userinfo",0);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();

            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        findViewById(R.id.explore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
    }


    public void processLogin() {
        String usernameValue = username.getText().toString();
        String passwordValue = password.getText().toString();


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", usernameValue);
        params.put("password", passwordValue);

        aQuery.ajax(url, params, org.json.JSONObject.class, new AjaxCallback<org.json.JSONObject>() {
            @Override
            public void callback(String url, org.json.JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("response", url + " response" + object);

                try {
                    if (object.getString("message").contains("Login successful")) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        preferences.edit().putString("userId",object.getJSONObject("result").getString("id")).apply();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}