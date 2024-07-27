package com.example.presentation
import com.example.domain.login.entity.User
import com.example.presentation.data.model.DaysOfWeek
import com.example.presentation.data.model.Instrument
import com.example.presentation.data.model.NoteCard
import com.example.presentation.data.model.Quotes
import com.example.presentation.data.model.Results
import com.example.presentation.data.model.Strategy
import com.example.presentation.data.model.Trade
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun addTrade(trade: Trade)
    fun deleteTrade(trade: Trade)
    fun getTradesSortedByDateDescending():List<Trade>
    fun getTradesSortedByDateAscending():List<Trade>
    fun getSortedByStrategiesList(strategy: String):List<Trade>
    fun getTradesByResult(result: String): List<Trade>
    fun getSortedByInstrumentList(instrument: String):List<Trade>
    fun getShortPos(): Int
    fun getLongPos(): Int
    fun getWinNumber(): Int
    fun getDefNumber(): Int
    fun getDayStatistic(): Flow<List<Trade>>
    fun countTradesByResultAndDate(results: Results, day: DaysOfWeek): Int

    //Strategy part

    fun getAllStrategies(): List<Strategy>

    fun addStrategy(strategy: Strategy)
    fun deleteStrategy(name: String)

    //instrument part


    fun getAllInstruments(): List<Instrument>

    fun addInstrument(instrument: Instrument)
    fun deleteInstrument(name: String)

    //Note part
    fun getAllNotes(): List<NoteCard>

    fun addNote(noteCard: NoteCard)
    fun updateNote(noteCard: NoteCard)
    fun deleteNote(noteCard: NoteCard)

    //login and register part
    fun getUser(email:String,pass:String): User?
    fun insertUser(user: User)
    fun getAllUsers(): List<User>
    fun getForexData(quotePair: String, time: String): Flow<Quotes>
}