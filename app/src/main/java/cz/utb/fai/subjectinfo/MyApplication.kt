package cz.utb.fai.subjectinfo

import android.app.Application
import cz.utb.fai.subjectinfo.api.StagApiService
import cz.utb.fai.subjectinfo.database.getDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MyApplication : Application() {
    val apiService: StagApiService by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://stag-ws.utb.cz/ws/services/rest2/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON serialization/deserialization
            .build()

        retrofit.create(StagApiService::class.java)
    }

    val repository: Repository by lazy {
        Repository(apiService, getDatabase(this))
    }

    override fun onCreate() {
        super.onCreate()
    }
}