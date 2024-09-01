package com.aykutcincik.retrofitjava.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aykutcincik.retrofitjava.R;
import com.aykutcincik.retrofitjava.adapter.RecyclerViewAdapter;
import com.aykutcincik.retrofitjava.model.CyrptoModel;
import com.aykutcincik.retrofitjava.service.CyrptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CyrptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        recyclerView = findViewById(R.id.recyclerView);


        Gson gson  = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();

        loadData();

    }

    private void   loadData(){


        final CyrptoApi cyrptoApi = retrofit.create(CyrptoApi.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cyrptoApi.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: handleResponse));



        /*
        Call<List<CyrptoModel>>  call = cyrptoApi.getData();

        call.enqueue(new Callback<List<CyrptoModel>>() {
            @Override
            public void onResponse(Call<List<CyrptoModel>> call, Response<List<CyrptoModel>> response) {

                if (response.isSuccessful()){
                    List<CyrptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }


            @Override
            public void onFailure(Call<List<CyrptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        */

    }

private  void handleResponse(List<CyrptoModel> cyrptoModelList){
    cryptoModels = new ArrayList<>(cyrptoModelList);

    //RecyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
    recyclerView.setAdapter(recyclerViewAdapter);

}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}

