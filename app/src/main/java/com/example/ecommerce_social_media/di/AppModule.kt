package com.example.ecommerce_social_media.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce_social_media.data.AppContants
import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.api.AuthInterceptor
import com.example.ecommerce_social_media.data.api.TokenProvider
import com.example.ecommerce_social_media.ui.repository.AuthRepository
import com.example.ecommerce_social_media.ui.repository.PostRepository
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit (tokenProvider: TokenProvider) : Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val authInterceptor = AuthInterceptor(tokenProvider)

        val httpClient = OkHttpClient().newBuilder().apply{
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
        }.apply { readTimeout(60,TimeUnit.SECONDS) }.build()


        val moshi  = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl(AppContants.APP_BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService (retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesTokenProvider (sharedPreferences: SharedPreferences) : TokenProvider {
        return TokenProvider(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(apiService: ApiService) : AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("your_preferences_name", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun providesPostRepository(apiService: ApiService): PostRepository {
        return PostRepository(apiService)
    }
}

class Rfc3339DateJsonAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull<Date>()
            null
        } else dateFormat.parse(reader.nextString())
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(dateFormat.format(value))
    }
}