package com.fadli.aplikasistoryapp.ui.authentication

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadli.aplikasistoryapp.dataclass.Login
import com.fadli.aplikasistoryapp.dataclass.Register
import com.fadli.aplikasistoryapp.api.RetrofitService
import com.fadli.aplikasistoryapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(val context: Context) : ViewModel() {

    val loginResult = MutableLiveData<Login>()
    val registerResult = MutableLiveData<Register>()
    val tempEmail = MutableLiveData("")
    var loading = MutableLiveData(View.GONE)
    val error = MutableLiveData("")

    private val TAG = AuthViewModel::class.simpleName

    fun register(name: String, email: String, password: String) {
        loading.postValue(View.VISIBLE)
        val client = RetrofitService.getApiService().register(name, email, password)
        client.enqueue(object : Callback<Register> {
            override fun onResponse(call: Call<Register>, response: Response<Register>) {
                when (response.code()) {
                    400 -> error.postValue(context.getString(R.string.API_error_email_invalid))
                    201 -> registerResult.postValue(response.body())
                    else -> error.postValue("ERROR ${response.code()} : ${response.errorBody()}")
                }
                loading.postValue(View.GONE)
            }

            override fun onFailure(call: Call<Register>, t: Throwable) {
                loading.postValue(View.GONE)
                Log.e(TAG, "onFailure Call: ${t.message}")
                error.postValue(t.message)
            }
        })
    }

    fun login(email: String, password: String) {
        tempEmail.postValue(email)
        loading.postValue(View.VISIBLE)
        val client = RetrofitService.getApiService().login(email, password)
        client.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                when (response.code()) {
                    200 -> loginResult.postValue(response.body())
                    400 -> error.postValue(context.getString(R.string.API_error_email_invalid))
                    401 -> error.postValue(context.getString(R.string.API_error_unauthorized))
                    else -> error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
                loading.postValue(View.GONE)
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                loading.postValue(View.GONE)
                Log.e(TAG, "onFailure Call: ${t.message}")
                error.postValue(t.message)
            }
        })
    }

}