package com.oshamahue.githubtrending
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.oshamahue.githubtrending.com.oshamahue.githubtrending.di.appModule
import org.koin.android.ext.android.startKoin

class GitHubTrendingApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin(this, listOf(appModule))
    }
}