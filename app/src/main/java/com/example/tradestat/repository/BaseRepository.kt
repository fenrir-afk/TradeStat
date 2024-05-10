package com.example.tradestat.repository

import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

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
    fun getDayStatistic(day: DaysOfWeek): List<Trade>
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
}