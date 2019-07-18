package com.example.task_typical_design;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class productsadapters extends RecyclerView.Adapter<productsadapters.MyViewHolder>  {
    private ArrayList<items> products;
    Context context;
    private SQLiteDatabase mDb;

    public productsadapters(Context context,ArrayList<items> products) {
        this.products = products;
        this.context=context;
    }



    public  class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView product_picture;
       TextView name_product;
       TextView price_product;
       Button sub_button , add_button, add_cart;
       TextView quantity;
        int count ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_picture =(ImageView)itemView.findViewById(R.id.imageView_product);
            name_product=(TextView)itemView.findViewById(R.id.textView2_nameproduct);
            price_product=(TextView)itemView.findViewById(R.id.textView3_price);
            sub_button=(Button)itemView.findViewById(R.id.button3_dcrease);
            add_button=(Button)itemView.findViewById(R.id.button4_increase);
            add_cart=(Button)itemView.findViewById(R.id.button4_add_card);
            quantity=(TextView)itemView.findViewById(R.id.quantity);
            count=Integer.valueOf(quantity.getText().toString());




            //sub_button.setOnClickListener(this);
            //add_button.setOnClickListener(this);
            //add_cart.setOnClickListener(this);




        }

        /*@Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button3_dcrease:
                    if(count<=1){
                        break;
                    }
                    quantity.setText(String.valueOf(--count));
                    break;
                case R.id.button4_increase:

                    quantity.setText(String.valueOf(++count));
                    break;
                case R.id.button4_add_card: {
                    productDbhelper dbhelper= new productDbhelper(context);
                    mDb = dbhelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();


                    cv.put("name", name_product.getText().toString());
                    cv.put("price", price_product.getText().toString());
                    cv.put("image",String.valueOf(product_picture.getTag()));
                    Toast.makeText(context, "iamge="  +product_picture. , Toast.LENGTH_LONG).show();
                    cv.put("q","1");


                    long i= mDb.insert("products", null, cv);
                     if(i !=-1) {
                         Toast.makeText(context, "add to cart", Toast.LENGTH_LONG).show();

                         Cursor cursor = mDb.query(
                                 product_contract.ProductyEntry.TABLE_NAME,
                                 null,
                                 null,
                                 null,
                                 null,
                                 null,
                                 null);
                         Log.i("data=",cursor.toString());
                     }



                    break;
                }

                    default:
                        break;
            }

        }*/


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final items item=products.get(position);
        holder.name_product.setText(item.getName());
        holder.price_product.setText(item.getPrice());
        Glide.with(context)
                .asBitmap()
                .load(item.getImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.product_picture.setImageBitmap(resource);
                    }
                });
        holder.sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.count<=1){
                    return;
                }
                holder.quantity.setText(String.valueOf(--holder.count));

            }
        });
        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.quantity.setText(String.valueOf(++holder.count));

            }
        });
        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDbhelper dbhelper = new productDbhelper(context);
                mDb = dbhelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", item.getName());
                cv.put("price", item.getPrice());
                cv.put("image", item.getImage());
                cv.put("q", holder.quantity.getText().toString());



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
                        int x=   Integer.valueOf(row.getString(row.getColumnIndex("q")))+
                                Integer.valueOf(holder.quantity.getText().toString());
                       ContentValues cv2 = new ContentValues();
                        cv2.put("q", String.valueOf(x));

                        Log.i("x=",x+"");
                        mDb.update("products",cv2, "name=?", new String[]{item.getName()});
                    Toast.makeText(context, "add cart again", Toast.LENGTH_LONG).show();

                }

//                if (cursor.getCount() == 1) {
//
//
//                    cv.put("q", quantity);
//                    int r = mDb.update("products", cv, "name="+item.getName(), null);
//                    if (r > 0) {
//                        Toast.makeText(context, "update=" + quantity, Toast.LENGTH_LONG).show();
//                        if (cursor != null && cursor.moveToFirst() ) {
//
//                                Log.i("name=", cursor.getString(cursor.getColumnIndex("q")));
//
//
//                        }
//                    }
//                }
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
                                  }}


            }
        );

    }

    @Override
    public int getItemCount() {
        return products.size();
    }



}
