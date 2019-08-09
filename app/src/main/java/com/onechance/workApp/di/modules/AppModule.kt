package com.onechance.workApp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Julio César Alcibar
 *         Description: Modulo utilizado para proveer un contexto a todos los componentes necesarios
 *         created on 18/07/2018
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}