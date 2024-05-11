package com.example.tradestat.ui.trade
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tradestat.data.FakeRepository
import com.example.tradestat.data.model.Trade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat

@OptIn(ExperimentalCoroutinesApi::class)
class TradeViewModelTest {
    // Set the main coroutine dispatcher for unit testing
    private val testDispatcher = TestCoroutineDispatcher()
    // Set the main coroutine scope for unit testing
    private val testScope = TestCoroutineScope(testDispatcher)
    // Mock any dependencies using Mockito
    @Mock
    lateinit var observer: Observer<List<Trade>>
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()




    private lateinit var trades: MutableList<Trade>
    private lateinit var viewModel: TradeViewModel
    private lateinit var repository: FakeRepository

    @Before
    fun setup() {
        // Initialize Mockito
        MockitoAnnotations.openMocks(this)
        // Provide the test dispatcher to Coroutines
        Dispatchers.setMain(testDispatcher)
        repository = FakeRepository()
        viewModel = TradeViewModel(Application(), repository)
        trades = mutableListOf(
            Trade(0, "Short", "Monday", "Strategy1", "Victory", "Instrument1", "9/05/2024 15:15:25", ""),
            Trade(1, "Long", "Monday", "Strategy1", "Defeat", "Instrument1", "9/05/2024 15:16:25", ""),
            Trade(2, "Long", "Wednesday", "Strategy1", "Victory", "Instrument1", "9/05/2024 15:17:25", ""),
            Trade(3, "Long", "Friday", "Strategy2", "Defeat", "Instrument1", "9/05/2024 15:18:25", ""),
            Trade(4, "Long", "Monday", "Strategy1", "Victory", "Instrument2", "9/05/2024 15:19:25", ""),
            Trade(5, "Short", "Monday", "Strategy2", "Victory", "Instrument1", "9/05/2024 15:20:25", "")
        )
        runBlocking {
            trades.forEach {
                viewModel.addTrade(it)
            }
        }
    }
    @After
    fun cleanup() {
        // Clean up Coroutines
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun addTrade() = runBlocking{
        val trade = Trade(6, "Long", "Friday", "Strategy2", "Victory", "Instrument1", "9/05/2024 15:21:25", "")
        var job = async {
            viewModel.addTrade(trade)
        }
        job.await()
        assert(repository.trades.size == 7)
    }
    @Test
    fun deleteData() = runBlocking{
        val trade =  Trade(5,"Short","Monday","Strategy2","Victory","Instrument1","9/05/2024 15:20:25","")
        var job = async {
            viewModel.deleteTrade(trade)
        }
        job.await()
        assert(repository.trades.size == 6)
    }
    @Test
    fun getDescendingList() = runBlocking{
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        var sortedArr = viewModel.sortedTradeList.value?.toMutableList()
        sortedArr?.sortBy { sdf.parse(it.ADDate) }
        viewModel.sortedTradeList.observeForever(observer)
        Mockito.verify(observer).onChanged(sortedArr!!.toList())
        viewModel.updateListByDateDescending()
    }

}
