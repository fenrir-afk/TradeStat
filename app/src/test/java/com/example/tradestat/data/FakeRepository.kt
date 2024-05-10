package com.example.tradestat.data

import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import com.example.tradestat.repository.BaseRepository

class FakeRepository(): BaseRepository {
    override fun addTrade(trade: Trade) {
        TODO("Not yet implemented")
    }

    override fun deleteTrade(trade: Trade) {
        TODO("Not yet implemented")
    }

    override fun getTradesSortedByDateDescending(): List<Trade> {
        TODO("Not yet implemented")
    }

    override fun getTradesSortedByDateAscending(): List<Trade> {
        TODO("Not yet implemented")
    }

    override fun getSortedByStrategiesList(strategy: String): List<Trade> {
        TODO("Not yet implemented")
    }

    override fun getTradesByResult(result: String): List<Trade> {
        TODO("Not yet implemented")
    }

    override fun getSortedByInstrumentList(instrument: String): List<Trade> {
        TODO("Not yet implemented")
    }

    override fun getShortPos(): Int {
        TODO("Not yet implemented")
    }

    override fun getLongPos(): Int {
        TODO("Not yet implemented")
    }

    override fun getWinNumber(): Int {
        TODO("Not yet implemented")
    }

    override fun getDefNumber(): Int {
        TODO("Not yet implemented")
    }

    override fun getDayStatistic(day: DaysOfWeek): List<Trade> {
        TODO("Not yet implemented")
    }

    override fun countTradesByResultAndDate(results: Results, day: DaysOfWeek): Int {
        TODO("Not yet implemented")
    }

    override fun getAllStrategies(): List<Strategy> {
        TODO("Not yet implemented")
    }

    override fun addStrategy(strategy: Strategy) {
        TODO("Not yet implemented")
    }

    override fun deleteStrategy(name: String) {
        TODO("Not yet implemented")
    }

    override fun getAllInstruments(): List<Instrument> {
        TODO("Not yet implemented")
    }

    override fun addInstrument(instrument: Instrument) {
        TODO("Not yet implemented")
    }

    override fun deleteInstrument(name: String) {
        TODO("Not yet implemented")
    }

    override fun getAllNotes(): List<NoteCard> {
        TODO("Not yet implemented")
    }

    override fun addNote(noteCard: NoteCard) {
        TODO("Not yet implemented")
    }

    override fun updateNote(noteCard: NoteCard) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(noteCard: NoteCard) {
        TODO("Not yet implemented")
    }

}