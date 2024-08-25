package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.instrument.dao.InstrumentDao
import com.example.data.entity.InstrumentDb
import com.example.data.user.dao.UserDao
import com.example.data.entity.UserDb
import com.example.data.note.dao.NoteDao
import com.example.data.entity.NoteCardDb
import com.example.data.strategy.dao.StrategiesDao
import com.example.data.entity.StrategyDb
import com.example.data.trade.dao.TradesDao
import com.example.data.entity.TradeDb
import com.example.data.util.Converters


@Database(
    version = 6,
    entities = [TradeDb::class, StrategyDb::class, InstrumentDb::class, NoteCardDb::class, UserDb::class],
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class TradeDatabase : RoomDatabase() {
    abstract fun instrumentDao(): InstrumentDao
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun tradesDao(): TradesDao
    abstract fun strategiesDao(): StrategiesDao
}

class Migration5To6 : Migration(5,6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE trade_table DROP COLUMN images")
    }
}