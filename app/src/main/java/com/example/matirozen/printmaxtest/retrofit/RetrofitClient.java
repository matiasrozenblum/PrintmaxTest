package com.example.matirozen.printmaxtest.retrofit;

import com.example.matirozen.printmaxtest.BuildConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static ObjectMapper mapper = null;

    public static Retrofit createClient(String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(createClient())
                    .addConverterFactory(JacksonConverterFactory.create(obtainMapper()))
                    .build();
        }
        return retrofit;
    }

    public static ObjectMapper obtainMapper(){
        if (mapper == null) {
            mapper = new ObjectMapper();

            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        return mapper;
    }

    private static OkHttpClient createClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG){
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }
}
