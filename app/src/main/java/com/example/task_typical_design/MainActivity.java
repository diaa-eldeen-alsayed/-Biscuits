package com.example.task_typical_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private productsadapters adapter_products;
    private shoppcart_adapter adapter_card;
    private SQLiteDatabase mDb;
    private GridView gridView;
    private adptergrid adapter_grid;




  MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        gridView=(GridView)findViewById(R.id.grid_view);


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
                recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);


                //  json_string.append(catagories.get(0).getName());
                ArrayList<items> products = catagories.get(0).getItems();
                adapter_products = new productsadapters(getApplicationContext(), products);
                adapter_grid=new adptergrid(getApplicationContext(),products);
                recyclerView.setAdapter(adapter_products);
                getSupportActionBar().show();



            }

            @Override
            public void onFailure(Call<List<catagory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.app_bar_card: {

                gridView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

              //  Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                //startActivity(intent);
                productDbhelper dbhelper = new productDbhelper(this);
                mDb = dbhelper.getWritableDatabase();
                Cursor cursor= getAllproduct() ;
                adapter_card = new shoppcart_adapter(getApplicationContext(), cursor);
                recyclerView.setAdapter(adapter_card);


                Log.i("menu=","cart");
                return true;
            }
            case R.id.app_bar_recycle_view:{
                gridView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter_products);


                Log.i("menu=","Recycle");
                return true;

            }
            case R.id.app_bar_gridview:{
                recyclerView.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(adapter_grid);

                Log.i("menu=","gride");

                return true;

            }




            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private Cursor getAllproduct() {
        return mDb.query("products"
                ,
                null,
                null,
                null,
                null,
                null,
                null
        );


    }


}