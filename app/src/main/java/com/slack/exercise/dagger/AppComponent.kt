package com.slack.exercise.dagger

import android.content.Context
import com.slack.exercise.App
import com.slack.exercise.dataprovider.DenylistProvider
import com.slack.exercise.dataprovider.UserSearchResultDataProvider
import com.slack.exercise.ui.dagger.BindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 * Component providing Application scoped instances.
 */
@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, BindingModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun applicationContext(): Context

    fun userSearchResultDataProvider(): UserSearchResultDataProvider

    fun denylistProvider(): DenylistProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder // Pass the application instance to the builder

        fun build(): AppComponent
    }
}