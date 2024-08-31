package com.aykutcincik.retrofitjava.service;

import com.aykutcincik.retrofitjava.model.CyrptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CyrptoApi {


    //GET ,POST ,UPDATE,DELETE

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Call<List<CyrptoModel>> getData();


}
