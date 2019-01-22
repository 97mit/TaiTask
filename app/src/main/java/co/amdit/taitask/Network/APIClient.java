package co.amdit.taitask.Network;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static ApiInterface apiInterface;
    public static ApiInterface getClient() {
        if (apiInterface == null) {
            apiInterface = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface.class);
        }
        return apiInterface;
    }

}
