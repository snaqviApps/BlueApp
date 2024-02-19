package ghar.learn.blueapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ghar.learn.blueapp.domain.chat.IMyBluetoothController
import ghar.learn.blueapp.domain.chat.data.chat.MyBluetoothController
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesBluetoothController(@ApplicationContext context : Context) : IMyBluetoothController {
        return MyBluetoothController(context = context)
    }
}