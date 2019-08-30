package com.villas.advertisingApp.di.components

import android.app.Application
import com.villas.advertisingApp.config.AdvertisingAplication
import com.villas.advertisingApp.di.builder.ActivityBuilder
import com.villas.advertisingApp.di.builder.FragmentBuilder
import com.villas.advertisingApp.di.modules.AppModule
import com.villas.advertisingApp.di.modules.BleModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    FragmentBuilder::class,
    AppModule::class,
    BleModule::class
])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AdvertisingAplication)

}