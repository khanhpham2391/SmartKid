package com.khanhpham.smartkidz.di

import android.app.Application
import com.khanhpham.smartkidz.helpers.NetworkInterceptor
import com.khanhpham.smartkidz.repository.SmartKidRepository
import com.khanhpham.smartkidz.api_endpoint.AppUserServiceApi
import com.khanhpham.smartkidz.ui.game.gamePlay.PlayViewModel
import com.khanhpham.smartkidz.ui.game.result.ResultViewModel
import com.khanhpham.smartkidz.ui.history.HistoryActivity
import com.khanhpham.smartkidz.ui.history.HistoryAdapter
import com.khanhpham.smartkidz.ui.history.HistoryViewModel
import com.khanhpham.smartkidz.ui.splash.SplashActivity
import com.khanhpham.smartkidz.ui.splash.SplashViewModel
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)
    fun inject(activity: SplashActivity)
    fun inject(splashViewModel: SplashViewModel)
    fun inject(compositeDisposable: CompositeDisposable)
    fun inject(playViewModel: PlayViewModel)
    fun inject(resultViewModel: ResultViewModel)
    fun inject(historyViewModel: HistoryViewModel)
    fun inject(historyActivity: HistoryActivity)
    //fun inject(historyAdapter: HistoryAdapter)
    fun smartKidRepository(): SmartKidRepository
    fun appUserServiceApi(): AppUserServiceApi
    fun networkInterceptor(): NetworkInterceptor
}