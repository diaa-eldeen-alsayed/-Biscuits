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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class shoppcart_adapter extends RecyclerView.Adapter<shoppcart_adapter.MyViewHolder> {
   Context context;
   Cursor cursor;
    int q,p,t;
    String name;
    private SQLiteDatabase mDb;

    public shoppcart_adapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.shopping_cart_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (!cursor.moveToPosition(position))
            return;
         name =cursor.getString(cursor.getColumnIndex("name"));
        holder.name.setText(cursor.getString(cursor.getColumnIndex("name")));
        holder.price.setText(cursor.getString(cursor.getColumnIndex("price")));
        Glide.with(context)
                .asBitmap()
                .load(cursor.getString(cursor.getColumnIndex("image")))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.product_picture.setImageBitmap(resource);
                    }
                });
        holder.quantity.setText(cursor.getString(cursor.getColumnIndex("q")));
         q= Integer.valueOf(cursor.getString(cursor.getColumnIndex("q")));
         p=Integer.valueOf(cursor.getString(cursor.getColumnIndex("price")));
         t=p*q;
        holder.Total.setText(String.valueOf(t));
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  if ( cursor.moveToPosition(position)){
                      Log.i("name=",cursor.getString(cursor.getColumnIndex("name")));
                      name=cursor.getString(cursor.getColumnIndex("name"));
                      q= Integer.valueOf(cursor.getString(cursor.getColumnIndex("q")));
                      Log.i("q=",cursor.getString(cursor.getColumnIndex("q")));

                      p=Integer.valueOf(cursor.getString(cursor.getColumnIndex("price")));



                if(q>1)
                    --q;
                t=p*q;
                productDbhelper dbhelper = new productDbhelper(context);
                mDb = dbhelper.getWritableDatabase();
                      Cursor row  = mDb.query("products"
                              ,
                              null,
                              "name=?",
                              new String[]{name},
                              null,
                              null,
                              null
                      );
                      if (row != null && row.moveToFirst()) {
                          ContentValues cv2 = new ContentValues();
                          Log.i("q=", q + "");
                          cv2.put("q", String.valueOf(q));
                          int u= mDb.update("products", cv2, "name=?", new String[]{name});



                          Log.i("q=", u+ "");
                         cursor= mDb.query("products"
                                  ,
                                  null,
                                  null,
                                  null,
                                  null,
                                  null,
                                  null
                          );
                          cursor.moveToPosition(position);
                           q = Integer.valueOf(cursor.getString(cursor.getColumnIndex("q")));
                          Log.i("q=", q + "");

                          holder.Total.setText(String.valueOf(t));
                          holder.quantity.setText(String.valueOf(q));
                      }
            }}
        });
        holder.increase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if ( cursor.moveToPosition(position)){
                    Log.i("name=",cursor.getString(cursor.getColumnIndex("name")));
                    name=cursor.getString(cursor.getColumnIndex("name"));
                    q= Integer.valueOf(cursor.getString(cursor.getColumnIndex("q")));
                    Log.i("q=",cursor.getString(cursor.getColumnIndex("q")));

                    p=Integer.valueOf(cursor.getString(cursor.getColumnIndex("price")));




                        ++q;
                    t=p*q;
                    productDbhelper dbhelper = new productDbhelper(context);
                    mDb = dbhelper.getWritableDatabase();
                    Cursor row  = mDb.query("products"
                            ,
                            null,
                            "name=?",
                            new String[]{name},
                            null,
                            null,
                            null
                    );
                    if (row != null && row.moveToFirst()) {
                        ContentValues cv2 = new ContentValues();
                        Log.i("q=", q + "");
                        cv2.put("q", String.valueOf(q));
                        int u= mDb.update("products", cv2, "name=?", new String[]{name});



                        Log.i("q=", u+ "");
                        cursor= mDb.query("products"
                                ,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        );
                        cursor.moveToPosition(position);
                        q = Integer.valueOf(cursor.getString(cursor.getColumnIndex("q")));
                        Log.i("q=", q + "");

                        holder.Total.setText(String.valueOf(t));
                        holder.quantity.setText(String.valueOf(q));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView product_picture;
        TextView name , price , quantity,Total;
        Button decrease, increase;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_picture= itemView.findViewById(R.id.imageView_cart_product);
            name=itemView.findViewById(R.id.textView2cart_nameproduct);
            price=itemView.findViewById(R.id.textView3cart_price);
            quantity=itemView.findViewById(R.id.quantity);
            Total=itemView.findViewById(R.id.textView3total_price);
            decrease=itemView.findViewById(R.id.button3_dcrease);
            increase=itemView.findViewById(R.id.button4_increase);

        }
    }
}
