package com.example.k.rainsky;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticeListView = (ListView) findViewById(R.id.listView);
        noticeList=new ArrayList<Notice>();
        adapter=new NoticeListAdapter(getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);

        final Button sortButton=(Button) findViewById(R.id.sortButton);
        final Button editButton=(Button) findViewById(R.id.editButton);
        final Button naviButton=(Button) findViewById(R.id.naviButton);



        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        naviButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

     new BackGroundTask().execute();
    }

    class BackGroundTask extends AsyncTask<Void,Void,String> {

        String target;

        @Override
        protected void onPreExecute() {

                target = "http://183.101.242.171/web/NoticeList.php";

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
           return null;
        }
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String address, item;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        address = object.getString("address");
                        item = object.getString("item");
                        Notice notice = new Notice(address,item);
                        noticeList.add(notice);
                      noticeListView.setAdapter(adapter);
                       adapter.notifyDataSetChanged();
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
}
