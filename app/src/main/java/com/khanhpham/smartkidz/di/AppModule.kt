package com.khanhpham.smartkidz.di

import android.app.Application
import com.khanhpham.smartkidz.api_endpoint.AppUserServiceApi
import com.khanhpham.smartkidz.api_endpoint.HistoryApi
import com.khanhpham.smartkidz.data.models.History
import com.khanhpham.smartkidz.helpers.NetworkInterceptor
import com.khanhpham.smartkidz.repository.SmartKidRepository
import com.khanhpham.smartkidz.repository.SmartKidRepositoryImpl
import com.khanhpham.smartkidz.ui.history.HistoryAdapter
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun networkInterceptor(app: Application): NetworkInterceptor{
        return NetworkInterceptor(app)
    }

    @Provides
    fun app(): Application{
        return app
    }

    @Provides
    @Singleton
    fun appUserServiceApi(app: Application, networkInterceptor: NetworkInterceptor): AppUserServiceApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(AppUserServiceApi.END_POINT_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(AppUserServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun smartKidRepository(app: Application, appUserServiceApi: AppUserServiceApi): SmartKidRepository{
        return SmartKidRepositoryImpl(appUserServiceApi)
    }

    @Provides
    @Singleton
    fun provideHistoryAdapter(historyList: ArrayList<History>): HistoryAdapter{
        return HistoryAdapter(historyList)
    }

    @Provides
    @Singleton
    fun provideHistoryList(): ArrayList<History>{
        return ArrayList()
    }
}