package com.example.tradestat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

@Database(version = 3, entities = [Trade::class,Strategy::class,Instrument::class], exportSchema = false)
abstract class TradeDatabase: RoomDatabase() {
    abstract fun getTradeDao():TradesDao
    abstract fun getStrategyDao(): StrategiesDao
    abstract fun getInstrumentDao(): InstrumentDao

    companion object{
        @Volatile
        private var INSTANSE: TradeDatabase? = null
        /**
         * In this method we are creating an instance of db
         * */
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