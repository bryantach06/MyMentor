package com.capstone.mymentor.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.mymentor.data.response.MentorResultResponseItem
import com.capstone.mymentor.data.retrofit.ApiConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class HomeViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth

    private val _listMentors = MutableLiveData<List<MentorResultResponseItem>>()
    val listMentors : LiveData<List<MentorResultResponseItem>> = _listMentors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getMentor()
    }

    private fun getMentor() {
        auth = Firebase.auth
        val email = auth.currentUser?.email

        _isLoading.value = true
        val client = ApiConfig.getApiService().getMentor(email.toString())
        client.enqueue(object : Callback<List<MentorResultResponseItem>> {
            override fun onResponse(
                call: Call<List<MentorResultResponseItem>>,
                response: Response<List<MentorResultResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listMentors.value = response.body()
                    Log.d(TAG, email.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MentorResultResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}