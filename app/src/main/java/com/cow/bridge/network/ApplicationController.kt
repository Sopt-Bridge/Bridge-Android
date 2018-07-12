package com.cow.bridge.network

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jihaeseong on 2017. 2. 13..
 */
class ApplicationController : Application() {

    private var api: ServerInterface? = null
    val serverInterface: ServerInterface?
        get() {
            api = buildServerInterface()
            return api
        }

    override fun onCreate() {
        super.onCreate()
        ApplicationController.instance = this
        Realm.init(applicationContext)
        val realmCOnfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmCOnfiguration)

    }

    fun buildServerInterface(): ServerInterface? {

        synchronized(ApplicationController::class.java) {

            if (api == null) {

                val retrofit = Retrofit.Builder()
                        .baseUrl(endpoint)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                api = retrofit.create(ServerInterface::class.java)
            }
        }
        return api
    }

    companion object {

        var instance: ApplicationController? = null
            private set
        var endpoint = String.format("http://13.124.201.59:3007")

        fun imageUrl(contentsIdx : Int, number : Int) = "https://s3.ap-northeast-2.amazonaws.com/ryudd/Bridge/src/img/img_${contentsIdx}_${number}.jpeg"

        fun videoThumbnailUrl(contentsIdx: Int) = "https://s3.ap-northeast-2.amazonaws.com/ryudd/Bridge/src/thumbnail/img_thumbnail_${contentsIdx}.jpeg"

        fun videoUrl(contentsIdx: Int) = "https://s3.ap-northeast-2.amazonaws.com/ryudd/Bridge/src/video/video_${contentsIdx}.mp4"

    }

}