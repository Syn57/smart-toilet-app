package com.ta.smile.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ta.smile.database.feces.color.FecesColorData
import com.ta.smile.database.feces.form.FecesFormData
import com.ta.smile.database.urine.UrineColorData
import com.ta.smile.network.ApiConfig
import com.ta.smile.network.FecesUrineResponse
import com.ta.smile.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): ViewModel() {

    private val mRepository: Repository = Repository(application, ApiConfig.getApiServiceResult())

    private val _result = MutableLiveData<FecesUrineResponse>()
    val result: LiveData<FecesUrineResponse> = _result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _dataFc = MutableLiveData<FecesColorData>()
    var dataFc: LiveData<FecesColorData> = _dataFc

    private val _dataFf = MutableLiveData<FecesFormData>()
    var dataFf: LiveData<FecesFormData> = _dataFf

    private val _dataUc = MutableLiveData<UrineColorData>()
    var dataUc: LiveData<UrineColorData> = _dataUc

    fun getData(uid: String?) {
        GlobalScope.launch {
            _result.postValue(mRepository.getDataFecesUrine(uid))
        }
    }

    fun getData2(uid: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiServiceResult().getDataFecesUrine(uid)
        client.enqueue(object : Callback<FecesUrineResponse>{
            override fun onResponse(
                call: Call<FecesUrineResponse>,
                response: Response<FecesUrineResponse>
            ) {
                if (response.isSuccessful){
                    _result.value = response.body()
                    _isLoading.value = false
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
                else{
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Log.e(TAG, "onFailure: ${response.code()}")
                    Log.e(TAG, "onFailure: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<FecesUrineResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isLoading.value = false
            }

        })
    }

    // Feces color
    fun insertFecesColor(fecesColorData: FecesColorData){
        Log.d(TAG, "insertFecesColor: inserted")
        mRepository.insertFecesColorData(fecesColorData)
    }
    fun deleteInitialFc(createdAt: String){mRepository.deleteInitialDataFc(createdAt)}
    fun deleteFecesColor(fecesColorData: FecesColorData){ mRepository.deleteFecesColorData(fecesColorData) }
    fun getFecesColorData(): LiveData<List<FecesColorData>> = mRepository.getFecesColorData()
    fun getLatestFecesColor(): LiveData<FecesColorData> = mRepository.getLatestFecesColorData()
    fun fecesDataCheck(): LiveData<Boolean> = mRepository.emptyFecesCheck()

    fun getFecesColorDataNL() {
        GlobalScope.launch {
            _dataFc.postValue(mRepository.getFecesColorDataNL())

        }
    }

    fun getFecesFormDataNL() {
        GlobalScope.launch {
            _dataFf.postValue(mRepository.getFecesFormDataNL())

        }
    }

    fun getUrineColorDataNL() {
        GlobalScope.launch {
            _dataUc.postValue(mRepository.getUrineColorDataNL())

        }
    }


    //Feces form
    fun insertFecesForm(fecesFormData: FecesFormData){
        Log.d(TAG, "insertFecesForm: inserted")
        mRepository.insertFecesFormData(fecesFormData)
    }
    fun deleteInitialFf(createdAt: String){mRepository.deleteInitialDataFf(createdAt)}
    fun deleteFecesForm(fecesFormData: FecesFormData){ mRepository.deleteFecesFormData(fecesFormData) }
    fun getFecesFormData(): LiveData<List<FecesFormData>> = mRepository.getFecesFormData()
    fun getLatestFecesForm(): LiveData<FecesFormData> = mRepository.getLatestFecesFormData()

    //Urine color
    fun insertUrineColor(urineColorData: UrineColorData){ mRepository.insertUrineColorData(urineColorData) }
    fun deleteUrineColor(urineColorData: UrineColorData){ mRepository.deleteUrineColorData(urineColorData) }
    fun getUrineColorData(): LiveData<List<UrineColorData>> = mRepository.getUrineColorData()
    fun getLatestUrineColor(): LiveData<UrineColorData> = mRepository.getLatestUrineColorData()
    fun deleteInitialUc(createdAt: String){mRepository.deleteInitialDataUc(createdAt)}

    companion object{
        private const val TAG = "MainViewModel"
    }
}
