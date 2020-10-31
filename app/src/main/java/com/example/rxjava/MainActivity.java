package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.rxjava.data.AppDatabase;
import com.example.rxjava.data.StockUpdateRoom;
import com.example.rxjava.databinding.ActivityMainBinding;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LinearLayoutManager linearLayoutManager;
    private StockDataAdapter stockDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        binding.recyclerview.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(linearLayoutManager);

        stockDataAdapter = new StockDataAdapter();
        binding.recyclerview.setAdapter(stockDataAdapter);



        YahooService yahooService = new RetrofitYahooServiceFactory().create();
        yahooService.yqlQuery()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(r -> Observable.fromIterable(r))
                .map(r -> StockUpdate.create(r))
                .doOnNext(this::saveStockUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stockUpdate -> {
                    Log.d("app", "new update " + stockUpdate.toString());
                    stockDataAdapter.add(stockUpdate);
                });


//        Observable.just("hello! new text").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                binding.helloWorldSalute.setText(s);
//            }
//        });
//
//        Observable.just(
//                new StockUpdate("Google", 12.43, new Date()),
//                new StockUpdate("APPL", 645.1, new Date()),
//                new StockUpdate("TWTR", 1.43, new Date())
//        ).subscribe(stockDataAdapter::add);

    }

    private void saveStockUpdate(StockUpdate stockUpdate) {

        StockUpdateRoom stockUpdateRoom = new StockUpdateRoom();
        stockUpdateRoom.setSTOCK_SYMBOL(stockUpdate.getStockSymbol());
        stockUpdateRoom.setPRICE(stockUpdate.getPrice().toString());
        stockUpdateRoom.setDATE(stockUpdate.getDate().toString());

        Log.d("app", stockUpdateRoom.getSTOCK_SYMBOL());
        AppDatabase.getInstance(this).stockUpdateDao()
                .insert(stockUpdateRoom)
                .subscribe();
    }

}