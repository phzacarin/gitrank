package com.zacarin.gitrank.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zacarin.gitrank.api.RepositoryRepository
import com.zacarin.gitrank.api.RepositoryResult
import com.zacarin.gitrank.model.Item
import com.zacarin.gitrank.model.Repository
import com.zacarin.gitrank.utils.ConnectionUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * View Model for the [Repository] list main activity.
 */
class RepositoryViewModel(private val repository: RepositoryRepository) : ViewModel(), CoroutineScope {

    private val tag = this::class.java.simpleName

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    var pageLoaded = 1
    var showLoading = MutableLiveData<Boolean>()
    var showErrorMessage = MutableLiveData<Boolean>()
    var showErrorToast = MutableLiveData<Boolean>()
    var showNoDataMessage = MutableLiveData<Boolean>()
    val repositoryList = MutableLiveData<ArrayList<Item>>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun onTryAgainClicked() {
        loadRepositories(pageLoaded)
    }

    fun loadRepositories(page: Int) {
        Log.d(tag, "Load repos")
        if (ConnectionUtils.isInternetAccessAvailable()) {
            Log.d(tag, "Attempting to load repository data, page = $page")
            showLoading.value = true
            showErrorMessage.value = false
            showErrorToast.value = false
            showNoDataMessage.value = false

            launch {
                val result = withContext(Dispatchers.IO) { repository.getRepositories(page) }
                showLoading.postValue(false)
                handleResult(result)
            }
        } else {
            showError()
        }
    }

    private fun handleResult(result: RepositoryResult<Repository>) {
        when (result) {
            is RepositoryResult.Success -> {
                Log.d(tag, "Data was successfully loaded")
                handleSuccessfulResult(result)
            }
            is RepositoryResult.Error -> {
                Log.d(tag, "Error loading data: ${result.exception.message}")
                showError()
            }
        }
    }

    private fun handleSuccessfulResult(result: RepositoryResult.Success<Repository>) {
        if (result.data.items.isEmpty()) {
            showNoDataMessage.value = true
        } else {
            pageLoaded ++
            updateRepositoryList(result.data.items)
        }
    }

    private fun updateRepositoryList(items: List<Item>) {
        var updatedList: ArrayList<Item> = ArrayList()
        if (repositoryList.value == null) {
            updatedList = ArrayList(items)
        } else {
            repositoryList.value?.also {
                if (it.isNotEmpty()) { updatedList = it }
                updatedList.addAll(items)
            }
        }
        repositoryList.postValue(updatedList)
    }

    private fun showError() {
        if (repositoryList.value != null) {
            repositoryList.value?.also {
                if (it.isEmpty()) showErrorMessage.value = true
                else showErrorToast.value = true
            }
        } else {
            showErrorMessage.value = true
        }
    }
}
