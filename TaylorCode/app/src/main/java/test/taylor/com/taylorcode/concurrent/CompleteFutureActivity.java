package test.taylor.com.taylorcode.concurrent;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompleteFutureActivity extends AppCompatActivity {
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    Thread.sleep(1000);
                    Log.i("ttaylor", "future2 return");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return 10;
            }
        },executorService);

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                   Thread.sleep(2000);
                }  catch (InterruptedException e){
                    Log.i("ttaylor", "exception() of future2");
                }
                return 20;
            }
        },executorService);
        CompletableFuture.anyOf(completableFuture1, completableFuture2)
                .thenApplyAsync(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) {
                        Log.i("ttaylor", "anyOf.thenApply");
                        return (int) o;
                    }
                })
                .whenComplete(new BiConsumer<Object, Throwable>() {
                    @Override
                    public void accept(Object o, Throwable throwable) {
                        if(throwable instanceof CancellationException){
                            Log.i("ttaylor", "whenComplete.cancel exception");
                        }
                        Log.i("ttaylor", "when complete");
                        if(throwable != null) {
                            Log.i("ttaylor", "whenComplete o="+o);
                        }
//                        completableFuture2.cancel(true);
                    }
                });
    }
}