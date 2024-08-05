package com.example.presentation

import com.example.domain.model.DaysOfWeek
import com.example.domain.model.Instrument
import com.example.domain.model.NoteCard
import com.example.domain.model.Quotes
import com.example.domain.model.Results
import com.example.domain.model.Strategy
import com.example.domain.model.Trade
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun addTrade(trade: Trade)
    fun deleteTrade(trade: Trade)
    fun getTradesSortedByDateDescending():List<Trade>
    fun getTradesSortedByDateAscending():List<Trade>
    fun getSortedByStrategiesList(strategy: String):List<Trade>
    fun getSortedByInstrumentList(instrument: String):List<Trade>

    fun getTradesByResult(result: String): List<Trade>
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