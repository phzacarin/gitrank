package com.zacarin.gitrank.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import com.zacarin.gitrank.api.RepositoryRepository
import com.zacarin.gitrank.model.Item
import com.zacarin.gitrank.model.Owner
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

private const val MOCK_ITEM_NAME = "name"
private const val MOCK_ITEM_FORK_COUNT = 10
private const val MOCK_ITEM_STAR_COUNT = 100
private const val MOCK_OWNER_AVATAR_URL = "https://avatars3.githubusercontent.com/u/32689599?v=4"
private const val MOCK_OWNER_LOGIN = "login"

class RepositoryViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: RepositoryRepository

    @Mock
    private lateinit var observerBoolean: Observer<Boolean>

    @Mock
    private lateinit var observerProducts: Observer<ArrayList<Item>>

    private lateinit var viewModel: RepositoryViewModel

    @Mock
    private val productList: List<Item> = listOf(
        Item(MOCK_ITEM_NAME, MOCK_ITEM_FORK_COUNT, MOCK_ITEM_STAR_COUNT,
            Owner(MOCK_OWNER_AVATAR_URL, MOCK_OWNER_LOGIN)
        )
    )

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setup() {
        viewModel = RepositoryViewModel(repository)
        viewModel.apply {
            showErrorMessage.observeForever(observerBoolean)
            showNoDataMessage.observeForever(observerBoolean)
            showErrorToast.observeForever(observerBoolean)
            showLoading.observeForever(observerBoolean)
            repositoryList.observeForever(observerProducts)
            runBlocking {
                doReturn(Single.just(productList)).whenever(repository).getRepositories()}
        }
    }

    @Test
    fun `When getting first page of repository list the list size is equal or more than 1`() {
        viewModel.loadRepositories(page = 1)
        viewModel.repositoryList.value?.also {
            assertThat(it.size >= 1)
        }
    }

    @Test
    fun `When repository list is fetched, a repository name should be returned`() {
        viewModel.loadRepositories(page = 1)
        assertThat((viewModel.repositoryList.value?.get(0)?.name) == MOCK_ITEM_NAME)
    }

    @Test
    fun `When repository list is fetched, forks count should be returned`() {
        viewModel.loadRepositories(page = 1)
        assertThat((viewModel.repositoryList.value?.get(0)?.forksCount) == MOCK_ITEM_FORK_COUNT)
    }

    @Test
    fun `When repository list is fetched, star count should be returned`() {
        viewModel.loadRepositories(page = 1)
        assertThat(
            (viewModel.repositoryList.value?.get(0)?.stargazersCount) == MOCK_ITEM_STAR_COUNT
        )
    }

    @Test
    fun `When repository list is fetched, owner's picture URL should be returned`() {
        viewModel.loadRepositories(page = 1)
        assertThat(
            (viewModel.repositoryList.value?.get(0)?.owner?.avatarUrl) == MOCK_OWNER_AVATAR_URL
        )
    }

    @Test
    fun `When repository list is loaded successfully, no "no data" message should be shown`() {
        viewModel.loadRepositories(page = 1)
        assertThat((viewModel.showNoDataMessage.value) == false)
    }

    @Test
    fun `When repository list is loaded successfully , no error message should be shown`() {
        viewModel.loadRepositories(page = 1)
        assertThat((viewModel.showErrorMessage.value) == false)
    }

    @Test
    fun `When repository list is loaded successfully, no loading spinner should be visible`() {
        viewModel.loadRepositories(page = 1)
        assertThat((viewModel.showLoading.value) == false)
    }
}