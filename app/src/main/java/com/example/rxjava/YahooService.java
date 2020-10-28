package com.example.rxjava;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YahooService {

    @GET("product/")
    Single<List<YahooStockQuote>> yqlQuery();


}
