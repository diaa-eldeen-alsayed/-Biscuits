package com.example.task_typical_design;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Jsonplaceholderapi {
    String BASE_URL="https://204.93.167.45/~helix/items_with_cat_or_subcat/";
    @GET("ar/9")
    Call<List<catagory>> getcategorybyid() ;


}
