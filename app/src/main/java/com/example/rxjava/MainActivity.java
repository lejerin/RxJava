package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rxjava.data.AppDatabase;
import com.example.rxjava.data.StockUpdateRoom;
import com.example.rxjava.databinding.ActivityMainBinding;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
//                Observable.interval(0, 5, TimeUnit.SECONDS)
//                .flatMap(
//                        i -> Observable.<YahooStockResult>error(new
//                                RuntimeException("Oops"))
//                )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError(error -> {
//                    Log.d("app", "doOnError ");
//                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
//                })
//                .observeOn(Schedulers.io())
//                .map(r -> r.getResults().getQuote())
//                .flatMap(r -> Observable.fromIterable(r))
//                .map(r -> StockUpdate.create(r))
//                .doOnNext(this::saveStockUpdate)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(stockUpdate -> {
//                    Log.d("app", "new update " + stockUpdate.toString());
//                    stockDataAdapter.add(stockUpdate);
//                });

//        yahooService.yqlQuery()
//                .subscribeOn(Schedulers.io())
//                .toObservable()
//                .flatMap(r -> Observable.fromIterable(r))
//                .map(r -> StockUpdate.create(r))
//                .doOnNext(this::saveStockUpdate)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(stockUpdate -> {
//                    Log.d("app", "new update " + stockUpdate.toString());
//                    stockDataAdapter.add(stockUpdate);
//                });

        Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap(
                        i -> Observable.<YahooStockResult>error(new RuntimeException("Crash"))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Log.d("app", "doOnError ");
                    Toast.makeText(this, "We couldn't reach internet - falling back to local data",
                            Toast.LENGTH_SHORT)
                            .show();
                })
                .observeOn(Schedulers.io())
                .map(r -> r.getResults().getQuote())
                .flatMap(Observable::fromIterable)
                .map(StockUpdate::create)
                .doOnNext(this::saveStockUpdate)
                .onExceptionResumeNext(
//                        v2(StorIOFactory.get(this)
//                                .get()
//                                .listOfObjects(StockUpdate.class)
//                                .withQuery(Query.builder()
//                                        .table(StockUpdateTable.TABLE)
//                                        .orderBy("date DESC")
//                                        .limit(50)
//                                        .build())
//                                .prepare()
//                                .asRxObservable())
//                                .take(1)
//                                .flatMap(Observable::fromIterable)
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stockUpdate -> {
                    Log.d("APP", "New update " + stockUpdate.getStockSymbol());
                    stockDataAdapter.add(stockUpdate);
                }, error -> {
                    if (stockDataAdapter.getItemCount() == 0) {
                    }
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