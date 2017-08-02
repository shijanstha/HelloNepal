package com.project.hellonepal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShareMyRouteActivity extends AppCompatActivity {


    ListView listView;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_my_route);

        listView = (ListView) findViewById(R.id.listview);

        databaseHelper = new DatabaseHelper(this);
        listView.setAdapter(new RouteListAdapter(this,0,databaseHelper.getRoutes()));

    }



    public class RouteListAdapter extends ArrayAdapter<RouteInfo>{

        Context context;

        public RouteListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<RouteInfo>list) {
            super(context, resource,list);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout,null);
            TextView title = (TextView) view.findViewById(R.id.title);

            final RouteInfo info = getItem(position);
            title.setText(info.name);
            view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            view.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShareMyRouteActivity.this,MapsActivityShowRoute.class);
                    intent.putExtra("name",info.name);
                    intent.putExtra("points",info.points);
                    startActivity(intent);

                }
            });
            return view;
        }
    }
}
