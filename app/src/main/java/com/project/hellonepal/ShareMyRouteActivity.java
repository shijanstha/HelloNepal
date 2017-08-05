package com.project.hellonepal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShareMyRouteActivity extends AppCompatActivity {


    ListView listView;

    DatabaseHelper databaseHelper;
    AQuery aQuery;

    String url = "http://192.168.100.8:88/ourservice/service.php?task=listUser";
    String insertRouteUrl = "http://192.168.100.8:88/ourservice/service.php?task=insertRoute";


    ArrayList<UserInfo> userList;

    String currentRouteName, currentRoutePoints;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_my_route);

        preferences = getSharedPreferences("userinfo", 0);
        listView = (ListView) findViewById(R.id.listview);

        databaseHelper = new DatabaseHelper(this);
        listView.setAdapter(new RouteListAdapter(this, 0, databaseHelper.getRoutes()));
        aQuery = new AQuery(this);

    }

    public void getUserList() {

        aQuery.ajax(url, org.json.JSONObject.class, new AjaxCallback<org.json.JSONObject>() {

            @Override
            public void callback(String url, org.json.JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("resonse", url + " response" + object);

                userList = new ArrayList<UserInfo>();
                try {
                    org.json.JSONArray array = object.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        UserInfo info = new UserInfo();
                        JSONObject obj = array.getJSONObject(i);
                        info.id = obj.getString("id");
                        info.username = obj.getString("username");
                        info.phone = obj.getString("phone");
                        userList.add(info);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showDialog();
            }
        });

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View view = LayoutInflater.from(this).inflate(R.layout.user_list_dialog_layout, null);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
        for (int i = 0; i < userList.size(); i++) {
            final UserInfo info = userList.get(i);
            View itemView = LayoutInflater.from(this).inflate(R.layout.userlist_item_layout, null);
            TextView username, phone;
            username = (TextView) itemView.findViewById(R.id.username);
            phone = (TextView) itemView.findViewById(R.id.phone);
            Log.i("username", "username:" + info.username);
            username.setText(info.username);
            phone.setText(info.phone);
            itemView.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareRoute(info.id);

                }
            });
            container.addView(itemView);
        }
        view.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setTitle("User list To share ");
        dialog.setContentView(view);
        dialog.show();

    }

    public void shareRoute(String to) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fromUser", preferences.getString("userId", ""));
        params.put("toUser", to);
        params.put("name", currentRouteName);
        params.put("points", currentRoutePoints);
        Log.i("params", "params:" + params.toString());
        aQuery.ajax(insertRouteUrl, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("response", url + " response:" + object);
                Toast.makeText(ShareMyRouteActivity.this, "Route shared", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadData(){
        listView.setAdapter(new RouteListAdapter(this, 0, databaseHelper.getRoutes()));
    }


    public class RouteListAdapter extends ArrayAdapter<RouteInfo> {

        Context context;

        public RouteListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<RouteInfo> list) {
            super(context, resource, list);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            TextView title = (TextView) view.findViewById(R.id.title);

            final RouteInfo info = getItem(position);
            title.setText(info.name);
            view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentRouteName = info.name;
                    currentRoutePoints = info.points;
                    getUserList();

                }
            });
            view.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShareMyRouteActivity.this, MapsActivityShowRoute.class);
                    intent.putExtra("name", info.name);
                    intent.putExtra("points", info.points);
                    startActivity(intent);

                }
            });
            view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHelper.deleteRoute(info.id);
                     loadData();
                }
            });
            return view;
        }
    }
}
