package com.khanhpham.smartkidz

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import com.khanhpham.smartkidz.di.AppComponent
import com.khanhpham.smartkidz.di.AppModule
import com.khanhpham.smartkidz.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary

class SmartKidzApplication : MultiDexApplication() {

    lateinit var component: AppComponent

    fun getAppComponent(): AppComponent{
        return component
    }

    companion object {
        lateinit var instance: SmartKidzApplication private set
    }

    operator fun get(context: Context): SmartKidzApplication {
        return context.applicationContext as SmartKidzApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (LeakCanary.isInAnalyzerProcess(this)){
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)

        AndroidThreeTen.init(this)
        LeakCanary.install(this)
    }

}