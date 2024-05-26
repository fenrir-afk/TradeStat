package com.example.tradestat.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tradestat.data.Converters
import com.example.tradestat.data.InstrumentDao
import com.example.tradestat.data.NoteDao
import com.example.tradestat.data.StrategiesDao
import com.example.tradestat.data.TradesDao
import com.example.tradestat.data.UserDao
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import com.example.tradestat.data.model.User


@Database(version = 4, entities = [Trade::class,Strategy::class,Instrument::class,NoteCard::class, User::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class TradeDatabase: RoomDatabase() {
    abstract fun getTradeDao(): TradesDao
    abstract fun getStrategyDao(): StrategiesDao
    abstract fun getInstrumentDao(): InstrumentDao
    abstract fun getNoteDao(): NoteDao
    abstract fun getUserDao(): UserDao

    companion object{
        @Volatile
        private var INSTANSE: TradeDatabase? = null
        /**
         * In this method we are creating an instance of db
         * */
        fun getDatabase(context:Context): TradeDatabase {
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