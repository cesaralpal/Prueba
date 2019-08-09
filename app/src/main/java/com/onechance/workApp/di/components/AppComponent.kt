package com.onechance.workApp.di.components

import android.app.Application
import com.onechance.workApp.config.PruebaAplication
import com.onechance.workApp.di.builder.ActivityBuilder
import com.onechance.workApp.di.builder.FragmentBuilder
import com.onechance.workApp.di.modules.AppModule
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
    AppModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: PruebaAplication)

}