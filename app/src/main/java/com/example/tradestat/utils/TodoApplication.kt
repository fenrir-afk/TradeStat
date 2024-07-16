package com.example.tradestat.utils

import android.app.Application
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.repository.TradesRepository

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 *
 * Also, sets up Timber in the DEBUG BuildConfig. Read Timber's documentation for production setups.
 */
class TodoApplication : Application() {

    // Depends on the flavor,
    val tradesRepository: TradesRepository
        get() = TradesRepository(TradeDatabase.getDatabase(this))
    val application: Application = this
    override fun onCreate() {
        super.onCreate()
    }
}