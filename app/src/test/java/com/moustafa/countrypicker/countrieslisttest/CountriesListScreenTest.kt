package com.moustafa.countrypicker.countrieslisttest

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.moustafa.countrypicker.models.Country
import com.moustafa.countrypicker.repository.Repository
import com.moustafa.countrypicker.repository.network.StateMonitor
import com.moustafa.countrypicker.ui.countrieslist.CountriesListViewModel
import com.moustafa.countrypicker.utils.CoroutineRule
import com.moustafa.countrypicker.utils.LiveDataTestUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test

/**
 * Some tests to make sure the RecipeViewModel is emitting the right states for the UI
 * to be then rendered
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
class CountriesListScreenTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    internal lateinit var recipesListViewModel: CountriesListViewModel

    private val currentViewModelState
        get() = LiveDataTestUtil.getValue(recipesListViewModel.countriesListStateLiveData)

    @Test
    fun `get countries list triggers loading state`() = coroutinesTestRule.runBlockingTest {
        val repository = object : Repository {
            override suspend fun fetchCountriesList(onError: (Exception) -> Unit): List<Country>? =
                emptyList()
        }
        coroutinesTestRule.pauseDispatcher()

        recipesListViewModel = CountriesListViewModel(repository)

        assertThat(currentViewModelState.stateMonitor is StateMonitor.Loading).isTrue()
        coroutinesTestRule.resumeDispatcher()

    }

    @Test
    fun `get countries list and return success`() = coroutinesTestRule.runBlockingTest {
        val repository = object : Repository {
            override suspend fun fetchCountriesList(onError: (Exception) -> Unit): List<Country>? =
                listOf(
                    Country(name = "Sweden"),
                    Country(name = "Denmark"),
                    Country(name = "Norway"),
                    Country(name = "Netherlands"),
                    Country(name = "Germany")
                )


        }
        coroutinesTestRule.pauseDispatcher()

        recipesListViewModel = CountriesListViewModel(repository)

        assertThat(currentViewModelState.stateMonitor is StateMonitor.Loading).isTrue()

        coroutinesTestRule.resumeDispatcher()

        assertThat(currentViewModelState.stateMonitor is StateMonitor.Loaded).isTrue()
        assertThat((currentViewModelState.stateMonitor as StateMonitor.Loaded).result)
            .hasSize(5)
    }

    @Test
    fun `get countries list and return failure`() = coroutinesTestRule.runBlockingTest {
        val repository = object : Repository {
            override suspend fun fetchCountriesList(onError: (Exception) -> Unit): List<Country>? {
                withContext(Dispatchers.Main) {
                    onError(NetworkErrorException("No internet"))
                }
                return null
            }


        }
        coroutinesTestRule.pauseDispatcher()

        recipesListViewModel = CountriesListViewModel(repository)

        assertThat(currentViewModelState.stateMonitor is StateMonitor.Loading).isTrue()

        coroutinesTestRule.resumeDispatcher()

        assertThat(currentViewModelState.stateMonitor is StateMonitor.Failed).isTrue()
        assertThat((currentViewModelState.stateMonitor as StateMonitor.Failed).failed)
            .isInstanceOf(NetworkErrorException::class.java)
    }

    @Test
    fun `filter countries list and return result`() = coroutinesTestRule.runBlockingTest {
        val repository = object : Repository {
            override suspend fun fetchCountriesList(onError: (Exception) -> Unit): List<Country>? =
                listOf(
                    Country(name = "Sweden"),
                    Country(name = "Denmark"),
                    Country(name = "Norway"),
                    Country(name = "Netherlands"),
                    Country(name = "Germany")
                )
        }

        recipesListViewModel = CountriesListViewModel(repository)

        coroutinesTestRule.pauseDispatcher()
        recipesListViewModel.filterCountries("netherlands")

        assertThat((currentViewModelState.stateMonitor as StateMonitor.Loaded).result)
            .hasSize(1)
        assertThat((currentViewModelState.stateMonitor as StateMonitor.Loaded).result[0].name)
            .isEqualTo("Netherlands")

        coroutinesTestRule.resumeDispatcher()
    }

    @Test
    fun `filter countries list and return empty`() = coroutinesTestRule.runBlockingTest {
        val repository = object : Repository {
            override suspend fun fetchCountriesList(onError: (Exception) -> Unit): List<Country>? =
                listOf(
                    Country(name = "Sweden"),
                    Country(name = "Denmark"),
                    Country(name = "Norway"),
                    Country(name = "Netherlands"),
                    Country(name = "Germany")
                )
        }

        recipesListViewModel = CountriesListViewModel(repository)

        coroutinesTestRule.pauseDispatcher()
        recipesListViewModel.filterCountries("Lebanon")

        assertThat((currentViewModelState.stateMonitor as StateMonitor.Loaded).result)
            .hasSize(0)

        coroutinesTestRule.resumeDispatcher()
    }
}