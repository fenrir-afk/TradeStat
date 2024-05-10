package com.example.tradestat.ui.trade

import android.app.Application
import com.example.tradestat.data.FakeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.Date

class TradeViewModelTest {
    private lateinit var repository:FakeRepository
   @Before
   fun setUp(){
       repository = FakeRepository()
       var viewModel = TradeViewModel(Application(),repository)
   }
    @Test
    fun `add trade and verify sorted list`(): Unit = runBlocking {

    }
}