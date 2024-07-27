package com.example.tradestat.ui.trade
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.presentation.BaseRepository
import com.example.presentation.data.model.Trade
import com.example.presentation.ui.trade.TradeViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class TradeViewModelTest {
    // Set the main coroutine dispatcher for unit testing
    private val testDispatcher = StandardTestDispatcher()
    // Set the main coroutine scope for unit testing
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()
    @MockK
    private lateinit var mockRepository: BaseRepository
    private lateinit var viewModel: TradeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = TradeViewModel(mockRepository)
    }
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun addTrade() = runTest {
        val trade = Trade(6, "Long", "Friday", "Strategy2", "Victory", "Instrument1", "9/05/2024 15:21:25", "")
        coEvery { mockRepository.addTrade(any()) } just Runs
        launch { viewModel.addTrade(trade) }
        advanceUntilIdle()
        coVerify(exactly = 1) { mockRepository.addTrade(any()) }
    }
    @Test
    fun deleteTrade() = runTest {
        val trade = Trade(6, "Long", "Friday", "Strategy2", "Victory", "Instrument1", "9/05/2024 15:21:25", "")
        coEvery { mockRepository.deleteTrade(any()) } just Runs
        launch {
            viewModel.deleteTrade(trade)
        }
        advanceUntilIdle()
        coVerify(exactly = 1) { mockRepository.deleteTrade(any()) }
    }
    @Test
    fun updateDescending() = runTest {
        coEvery { mockRepository.getTradesSortedByDateDescending() } returns listOf()
        launch {
            viewModel.updateListByDateDescending()
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { mockRepository.getTradesSortedByDateDescending() }
    }
    @Test
    fun updateAscending() = runTest {
        coEvery { mockRepository.getTradesSortedByDateAscending() } returns listOf()
        launch {
            viewModel.updateListByDateAscending()
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { mockRepository.getTradesSortedByDateAscending() }
    }
}