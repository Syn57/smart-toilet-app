package com.ta.smile.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ta.smile.database.feces.color.FecesColorDao
import com.ta.smile.database.feces.color.FecesColorData
import com.ta.smile.database.feces.color.FecesColorDatabase
import com.ta.smile.database.feces.form.FecesFormDao
import com.ta.smile.database.feces.form.FecesFormData
import com.ta.smile.database.feces.form.FecesFormDatabase
import com.ta.smile.database.urine.UrineColorDao
import com.ta.smile.database.urine.UrineColorData
import com.ta.smile.database.urine.UrineColorDatabase
import com.ta.smile.network.ApiConfig
import com.ta.smile.network.ApiService
import com.ta.smile.network.FecesUrineResponse
import retrofit2.await
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application, apiService: ApiService) {
    private val mApiService = apiService
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val mFecesColorDao: FecesColorDao
    private val mFecesFormDao: FecesFormDao
    private val mUrineColorDao: UrineColorDao

    init {
        val dbFw = FecesColorDatabase.getDatabase(application)
        mFecesColorDao = dbFw.fecesColorDao()

        val dbFb = FecesFormDatabase.getDatabase(application)
        mFecesFormDao = dbFb.fecesFormDao()

        val dbUc = UrineColorDatabase.getDatabase(application)
        mUrineColorDao = dbUc.urineColorDao()
    }

    suspend fun getDataFecesUrine(uid: String?): FecesUrineResponse {
        return mApiService.getDataFecesUrine(uid).await()
    }

    // Feces Color Functions
    fun getFecesColorData(): LiveData<List<FecesColorData>> = mFecesColorDao.getFecesColorData()

    fun getFecesColorDataNL(): FecesColorData = mFecesColorDao.getLatestDataNL()

    fun getLatestFecesColorData(): LiveData<FecesColorData> = mFecesColorDao.getLatestData()

    fun insertFecesColorData(fecesColor: FecesColorData){
        executorService.execute { mFecesColorDao.insert(fecesColor) }
    }
    fun deleteFecesColorData(fecesColor: FecesColorData){
        executorService.execute { mFecesColorDao.delete(fecesColor) }
    }

    fun deleteInitialDataFc(createdAt: String){
        executorService.execute{ mFecesColorDao.deleteInitialFc(createdAt) }
    }
    fun emptyFecesCheck(): LiveData<Boolean> = mFecesColorDao.isEmpty()

    //Feces Form Functions
    fun getFecesFormData(): LiveData<List<FecesFormData>> = mFecesFormDao.getFecesFormData()

    fun getLatestFecesFormData(): LiveData<FecesFormData> = mFecesFormDao.getLatestFecesFormData()

    fun insertFecesFormData(fecesForm: FecesFormData){
        executorService.execute { mFecesFormDao.insert(fecesForm) }
    }
    fun deleteFecesFormData(fecesForm: FecesFormData){
        executorService.execute { mFecesFormDao.delete(fecesForm) }
    }

    fun getFecesFormDataNL(): FecesFormData = mFecesFormDao.getLatestDataFecesFormNL()

    fun deleteInitialDataFf(createdAt: String){
        executorService.execute{ mFecesFormDao.deleteInitialFc(createdAt) }
    }

    //Urine Color Functions
    fun getUrineColorData(): LiveData<List<UrineColorData>> = mUrineColorDao.getUrineColorData()

    fun getLatestUrineColorData(): LiveData<UrineColorData> = mUrineColorDao.getLatestUrineColorData()

    fun insertUrineColorData(urineColor: UrineColorData){
        executorService.execute { mUrineColorDao.insert(urineColor) }
    }
    fun deleteUrineColorData(urineColor: UrineColorData){
        executorService.execute { mUrineColorDao.delete(urineColor) }
    }

    fun getUrineColorDataNL(): UrineColorData = mUrineColorDao.getLatestDataUrineColorNL()

    fun deleteInitialDataUc(createdAt: String){
        executorService.execute{ mUrineColorDao.deleteInitialUc(createdAt) }
    }
}