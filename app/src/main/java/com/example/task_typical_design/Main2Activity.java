package com.example.task_typical_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {



    GridView gridView;
    adptergrid gridadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        productDbhelper dbhelper = new productDbhelper(this);
        gridView=(GridView)findViewById(R.id.grid_view);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Jsonplaceholderapi.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Jsonplaceholderapi json_api = retrofit.create(Jsonplaceholderapi.class);

        Call<List<catagory>> call = json_api.getcategorybyid();
        call.enqueue(new Callback<List<catagory>>() {
            @Override
            public void onResponse(Call<List<catagory>> call, Response<List<catagory>> response) {
                List<catagory> catagories = response.body();
                setTitle(catagories.get(0).getName());



                // use this setting to improve performance if you know that changes



                //  json_string.append(catagories.get(0).getName());
                ArrayList<items> products = catagories.get(0).getItems();
                gridadapter=new adptergrid(getApplicationContext(),products);
                gridView.setAdapter(gridadapter);



            }

            @Override
            public void onFailure(Call<List<catagory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }

}
