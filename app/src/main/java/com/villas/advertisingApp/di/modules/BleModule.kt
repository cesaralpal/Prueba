package com.villas.advertisingApp.di.modules

import com.polidea.rxandroidble2.RxBleClient
import com.villas.advertisingApp.config.AdvertisingAplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BleModule {
    @Singleton
    @Provides
    fun provideRxBleClient(application: AdvertisingAplication): RxBleClient {
        return RxBleClient.create(application)
    }
}