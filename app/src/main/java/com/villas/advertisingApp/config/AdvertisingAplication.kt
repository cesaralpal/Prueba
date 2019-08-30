package com.villas.advertisingApp.config

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.polidea.rxandroidble2.LogConstants
import com.polidea.rxandroidble2.LogOptions
import com.polidea.rxandroidble2.RxBleClient
import com.villas.advertisingApp.R
import com.villas.advertisingApp.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class AdvertisingAplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    companion object {
        lateinit var rxBleClient: RxBleClient
            private set
    }
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name(getString(R.string.db_name))
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        rxBleClient = RxBleClient.create(this)
        RxBleClient.updateLogOptions(LogOptions.Builder()
            .setLogLevel(LogConstants.INFO)
            .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
            .setUuidsLogSetting(LogConstants.UUIDS_FULL)
            .setShouldLogAttributeValues(true)
            .build())


    }


    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector




}