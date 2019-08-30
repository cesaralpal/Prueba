package com.villas.advertisingApp.di.builder



import com.villas.advertisingApp.bluetoothConnection.FragmentListScannerModule
import com.villas.advertisingApp.bluetoothConnection.FragmentScannerModule
import com.villas.advertisingApp.bluetoothConnection.FragmentListScanner
import com.villas.advertisingApp.bluetoothConnection.FragmentScanner
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [(FragmentScannerModule::class)])
    abstract fun bindScannerFragment(): FragmentScanner

    @ContributesAndroidInjector(modules = [(FragmentListScannerModule::class)])
    abstract fun bindFragmentListScanner(): FragmentListScanner
}