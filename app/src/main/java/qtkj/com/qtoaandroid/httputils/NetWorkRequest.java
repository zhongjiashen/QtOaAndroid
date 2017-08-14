package qtkj.com.qtoaandroid.httputils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import qtkj.com.qtoaandroid.httputils.api.ApiService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class NetWorkRequest {
    public Context mContext;

    private Retrofit mRetrofit;

    private OkHttpClient mOkHttpClient;
    private static NetWorkRequest sInstance = new NetWorkRequest();
    public ApiService apiService;
    public static NetWorkRequest getInstance() {
        return sInstance;
    }
    public NetWorkRequest() {

    }
    /**
     * 初始化Retrofit
     *
     * @param context
     */
    public NetWorkRequest init(Context context, String baseURL) {
        this.mContext = context;
        synchronized (NetWorkRequest.this) {
            ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new SafeTypeAdapterFactory()).create();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(scalarsConverterFactory)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            apiService = mRetrofit.create(ApiService.class);
        }
        return this;
    }
    private final static class SafeTypeAdapterFactory<T> implements TypeAdapterFactory {
        @Override
        public TypeAdapter create(Gson gson, final TypeToken type) {
            final TypeAdapter delegate = gson.getDelegateAdapter(this, type);
            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    try {
                        delegate.write(out, value);
                    } catch (IOException e) {
                        delegate.write(out, "");
                    }
                }


                @Override
                public T read(JsonReader in) throws IOException {
                    try {
                        return (T) delegate.read(in);
                    } catch (IOException e) {
                        in.skipValue();
                        return null;
                    } catch (IllegalStateException e) {
                        in.skipValue();
                        return null;
                    } catch (JsonSyntaxException e) {
                        in.skipValue();
                        if (type.getType() instanceof Class) {
                            try {
                                return (T) ((Class) type.getType()).newInstance();
                            } catch (Exception e1) {

                            }
                        }
                        return null;
                    }
                }
            };
        }
    }
}
