package com.aykutcincik.retrofitjava.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.aykutcincik.retrofitjava.R;
import com.aykutcincik.retrofitjava.model.CyrptoModel;
import com.aykutcincik.retrofitjava.service.CyrptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CyrptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com";
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        Gson gson  = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();

        loadData();

    }

    private void   loadData(){

        CyrptoApi cyrptoApi = retrofit.create(CyrptoApi.class);
        Call<List<CyrptoModel>>  call = cyrptoApi.getData();

        call.enqueue(new Callback<List<CyrptoModel>>() {
            @Override
            public void onResponse(Call<List<CyrptoModel>> call, Response<List<CyrptoModel>> response) {

                if (response.isSuccessful()){
                    List<CyrptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);


                    for (CyrptoModel cyrptoModel : cryptoModels){

                        System.out.println(cyrptoModel.currency);
                   
                    }



                }
            }

            @Override
            public void onFailure(Call<List<CyrptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}