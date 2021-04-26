package com.example.themoviechallenge.module

import com.example.themoviechallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT = 60L

val networkModule = module {

    single { providerHttpLoggingInterceptor() }
    single { ApiInterceptor() }
    single { generateCustomClient(TIMEOUT) }
    single { providerIdentityOkHttpClient(get(), get()) }
    single { generateRetrofit(BuildConfig.BASE_URL, get()) }

}

fun providerHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return logging
}

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            return response
        } catch (e: Exception) {
            throw  e
        }
    }
}

private fun generateCustomClient(timeout: Long): OkHttpClient.Builder {
    return OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
}

fun providerIdentityOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiInterceptor: ApiInterceptor
): OkHttpClient {
    val client = generateCustomClient(TIMEOUT)
    client.addInterceptor { chain ->
        val builder = chain.request().newBuilder()
        //builder.addHeader(EXAMPLE_HEADER, EXAMPLE_HEADER_VALUE)
        chain.proceed(builder.build())
    }
    client.addInterceptor(httpLoggingInterceptor)
    client.addInterceptor(apiInterceptor)
    return client.build()
}

private fun generateRetrofit(url: String, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(url)
        .build()
}

