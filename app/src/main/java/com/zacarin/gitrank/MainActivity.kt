package com.zacarin.gitrank

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zacarin.gitrank.databinding.RepositoryListBinding
import com.zacarin.gitrank.view.InfiniteScrollListener
import com.zacarin.gitrank.view.RepositoryAdapter
import com.zacarin.gitrank.viewmodel.RepositoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Main activity responsible to handle the main RecyclerView.
 */
class MainActivity : AppCompatActivity() {

    private val tag = this::class.java.simpleName

    private val repositoryViewModel: RepositoryViewModel by viewModel()
    private lateinit var binding: RepositoryListBinding
    private val repositoryAdapter: RepositoryAdapter by lazy {
        RepositoryAdapter()
    }
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private val infiniteScrollListener: InfiniteScrollListener by lazy {
        object : InfiniteScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                loadMore()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.repository_list)
        binding.apply {
            viewModel = repositoryViewModel
            lifecycleOwner = this@MainActivity
        }
        binding.rvProducts.apply {
            layoutManager = linearLayoutManager
            adapter = repositoryAdapter
            addOnScrollListener(infiniteScrollListener)
        }
        setupViewModel()
    }

    private fun loadMore() {
        with(repositoryViewModel) {
            Log.d(tag, "Load more items")
            loadRepositories(pageLoaded)
        }
    }

    private fun setupViewModel() {
        repositoryViewModel.apply {
            repositoryList.observe(this@MainActivity, {
                if (it.isNotEmpty()) {
                    repositoryAdapter.updateData(repositoryList.value)
                }
            })
            showErrorToast.observe(this@MainActivity, {
                if (it) { showLoadErrorToast() }
            })
        }
        loadMore()
    }

    private fun showLoadErrorToast() {
        Toast.makeText(
            this@MainActivity, resources.getText(R.string.load_error_toast),
            Toast.LENGTH_LONG
        ).show()
    }
}
