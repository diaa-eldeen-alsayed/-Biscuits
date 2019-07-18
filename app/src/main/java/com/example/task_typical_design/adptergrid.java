package com.example.task_typical_design;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class adptergrid extends ArrayAdapter<items> {
    Context context;
    private SQLiteDatabase mDb;



    public adptergrid(@NonNull Context context, List<items>products) {
        super(context, 0,products);
        this.context=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final items item=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item,parent,false);
        }
        final ImageView product_picture=(ImageView) convertView.findViewById(R.id.imageView_grid_product);
        Glide.with(context)
                .asBitmap()
                .load(item.getImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                       product_picture.setImageBitmap(resource);
                    }
                });
        TextView name=(TextView)convertView.findViewById(R.id.textView_name_grid_product);
        name.setText(item.getName());
        TextView price=(TextView)convertView.findViewById(R.id.textView2_price_gride_product);
        price.setText(item.getPrice());
        Button addcart=(Button)convertView.findViewById(R.id.button_add_to_cart_grid_product);
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDbhelper dbhelper = new productDbhelper(context);
                mDb = dbhelper.getWritableDatabase();
               // Toast.makeText(context,"add cart",Toast.LENGTH_LONG).show();
                ContentValues cv = new ContentValues();
                cv.put("name", item.getName());
                cv.put("price", item.getPrice());
                cv.put("image", item.getImage());
                cv.put("q", 1);
                Cursor row  = mDb.query("products"
                        ,
                        null,
                        "name=?",
                        new String[]{item.getName()},
                        null,
                        null,
                        null
                );
                if (row != null && row.moveToFirst()) {


                    Log.i("name", row.getString(row.getColumnIndex("name")));
                    Log.i("price", row.getString(row.getColumnIndex("price")));
                    Log.i("q", row.getString(row.getColumnIndex("q")));
                    Log.i("image", row.getString(row.getColumnIndex("image")));
                    int x=   Integer.valueOf(row.getString(row.getColumnIndex("q")))+1;
                    ContentValues cv2 = new ContentValues();
                    cv2.put("q", String.valueOf(x));

                    Log.i("x=",x+"");
                    mDb.update("products",cv2, "name=?", new String[]{item.getName()});
                    Toast.makeText(context, "add cart again", Toast.LENGTH_LONG).show();

                }
                else {

                    long i = mDb.insert("products", null, cv);
                    if (i != -1) {
                        Toast.makeText(context, "add cart", Toast.LENGTH_LONG).show();
                        Cursor cursor = mDb.query("products"
                                ,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);


                    }
                }


            }
        });

            return convertView;

    }
}
