package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.instrument.locale.dao.InstrumentDao
import com.example.data.instrument.locale.entity.Instrument
import com.example.data.login.locale.dao.UserDao
import com.example.data.login.locale.entity.UserDb
import com.example.data.note.locale.dao.NoteDao
import com.example.data.note.locale.entity.NoteCard
import com.example.data.strategy.locale.dao.StrategiesDao
import com.example.data.strategy.locale.entity.Strategy
import com.example.data.trade.locale.dao.TradesDao
import com.example.data.trade.locale.entity.Trade
import com.example.data.util.Converters


@Database(version = 4, entities = [Trade::class, Strategy::class, Instrument::class, NoteCard::class, UserDb::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class TradeDatabase : RoomDatabase() {
    abstract fun instrumentDao(): InstrumentDao
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun tradesDao(): TradesDao
    abstract fun strategiesDao(): StrategiesDao
}