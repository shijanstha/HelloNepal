package com.project.hellonepal;

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
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewMyRouteActivity extends AppCompatActivity {

    ListView listView;

    DatabaseHelper databaseHelper;
    AQuery aQuery;


    String url = "http://192.168.0.104:88/ourservice/service.php?task=listRoute&to=";

    ArrayList<ViewRouteInfo> routeList;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_my_route);
        preferences = getSharedPreferences("userinfo",0);
        listView = (ListView) findViewById(R.id.listview);

        aQuery = new AQuery(this);
        getSharedRoute();
    }

    public void getSharedRoute() {
        aQuery.ajax(url+preferences.getString("userId",""), JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("response", url + " response" + object);


                routeList = new ArrayList<ViewRouteInfo>();
                try {
                    JSONArray array = object.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        ViewRouteInfo info = new ViewRouteInfo();
                        JSONObject obj = array.getJSONObject(i);
                        info.id = obj.getString("id");
                        info.toUser = obj.getString("toUser");
                        info.fromUser = obj.getString("fromUser");
                        info.points = obj.getString("points");
                        info.username = obj.getString("username");
                        info.name = obj.getString("name");
                        info.phone = obj.getString("phone");
                        routeList.add(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView.setAdapter(new RouteListAdapter(ViewMyRouteActivity.this, 0, routeList));


            }
        });
    }

    public class RouteListAdapter extends ArrayAdapter<ViewRouteInfo> {

        Context context;

        public RouteListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<ViewRouteInfo> list) {
            super(context, resource, list);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView fromUser = (TextView) view.findViewById(R.id.fromUser);
            TextView phone = (TextView) view.findViewById(R.id.phone);

            final ViewRouteInfo info = getItem(position);
            title.setText(info.name);
            fromUser.setText("From User: "+info.username);
            phone.setText("Phone: "+info.phone);
            view.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewMyRouteActivity.this, MapsActivityShowRoute.class);
                    intent.putExtra("name", info.name);
                    intent.putExtra("points", info.points);
                    startActivity(intent);

                }
            });
            return view;
        }
    }
}
