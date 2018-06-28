package com.example.k.rainsky;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.skt.Tmap.TMapTapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements Serializable {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private ArrayList<Notice> noticeList;


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

       new BackGroundTask().execute();

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    sortButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    editButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    naviButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                final AlertDialog dialog;
                dialog=builder.setMessage("정렬 중입니다....")
                        .create();
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        noticeList.clear();
                        adapter=new NoticeListAdapter(getApplicationContext(),noticeList);
                        noticeListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        new BackGroundTask2().execute();
                    }
                }, 5000);
                }

        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                editButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                naviButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        naviButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                sortButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                editButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                naviButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                Fragment fragment=new NaviFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("noticeList",noticeList);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager =getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,fragment);
                fragmentTransaction.commit();

            }
        });


    }

    class BackGroundTask extends AsyncTask<Void,Void,String> {

        String target;
        Intent intent = getIntent();
       String id=intent.getStringExtra("id");
        @Override
        protected void onPreExecute() {

            try {
                target = "http://multipledestination.online/web/NoticeList.php?id=" + URLEncoder.encode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            if(this.isCancelled()){
                return null;
            }
            else {
                try {
                    URL url = new URL(target);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String temp = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((temp = bufferedReader.readLine()) != null) {
                        stringBuilder.append(temp + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
                try {

                    JSONObject jo = new JSONObject(result);
                    JSONArray jsonArray = jo.getJSONArray("response");
                    int count = 0;
                    String address, item;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        address = object.getString("address");
                        item = object.getString("item");
                        Notice notice = new Notice(address,item);
                        noticeList.add(notice);
                        adapter.notifyDataSetChanged();
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        protected void onCancelled() {

            super.onCancelled();
        }

        }
    class BackGroundTask2 extends AsyncTask<Void,Void,String> {

        String target;
        Intent intent = getIntent();
        String id=intent.getStringExtra("id");
        @Override
        protected void onPreExecute() {

            try {
                target = "http://multipledestination.online/web/NoticeList2.php?id=" + URLEncoder.encode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp =null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
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
            super.onPostExecute(result);
            try {

                JSONObject jo = new JSONObject(result);
                JSONArray jsonArray = jo.getJSONArray("response");
                int count = 0;
                String address, item;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    address = object.getString("address");
                    item = object.getString("item");
                    Notice notice = new Notice(address,item);
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
