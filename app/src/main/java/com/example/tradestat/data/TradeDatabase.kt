package com.example.tradestat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

@Database(version = 2, entities = [Trade::class,Strategy::class], exportSchema = false)
abstract class TradeDatabase: RoomDatabase() {
    abstract fun getTradeDao():TradesDao
    abstract fun getStrategyDao(): StrategiesDao

    companion object{
        @Volatile
        private var INSTANSE: TradeDatabase? = null

        fun getDatabase(context:Context):TradeDatabase{
            var tempInstance = INSTANSE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TradeDatabase::class.java,
                    "trade_database"
                ).build()
                INSTANSE = instance
                return  instance
            }
        }
    }
}