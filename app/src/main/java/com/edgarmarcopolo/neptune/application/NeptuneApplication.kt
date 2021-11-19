package com.edgarmarcopolo.neptune.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.edgarmarcopolo.neptune.application.di.Injectable
import com.edgarmarcopolo.neptune.application.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class NeptuneApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder().application(this).build().inject(this)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity, savedInstanceState: Bundle?
            ) {
                handleActivity(activity)
            }

            override fun onActivityStarted(
                activity: Activity
            ) {
            }

            override fun onActivityResumed(
                activity: Activity
            ) {
            }

            override fun onActivityPaused(
                activity: Activity
            ) {
            }

            override fun onActivityStopped(
                activity: Activity
            ) {
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                outState: Bundle
            ) {
            }

            override fun onActivityDestroyed(
                activity: Activity
            ) {
            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(object :
                    FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentPreAttached(
                        fm: FragmentManager, f: Fragment, context: Context
                    ) {
                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }
                    }
                }, true)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}