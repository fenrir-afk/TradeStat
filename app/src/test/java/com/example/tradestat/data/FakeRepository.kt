package com.example.tradestat.data

import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade
import com.example.tradestat.repository.BaseRepository
import java.text.SimpleDateFormat

class FakeRepository(): BaseRepository {
    var trades:MutableList<Trade> = mutableListOf()
    private val strategies:MutableList<Strategy> = mutableListOf()
    private val instruments:MutableList<Instrument> = mutableListOf()
    private val notes:MutableList<NoteCard> = mutableListOf()
    override fun addTrade(trade: Trade) {
        trades.add(trade)
        strategies.add(Strategy(0,trade.strategy))
        instruments.add(Instrument(0,trade.instrument))
    }

    override fun deleteTrade(trade: Trade) {
        trades.remove(trade)
    }

    override fun getTradesSortedByDateDescending(): List<Trade> {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        trades.sortByDescending {
            sdf.parse(it.ADDate)
        }
        return trades
    }

    override fun getTradesSortedByDateAscending(): List<Trade> {
        val sortedTrades = trades.toMutableList() // Создаем копию списка trades
        sortedTrades.sortBy { it.ADDate } // Сортируем копию списка
        return sortedTrades // Возвращаем отсортированный список
    }

    override fun getSortedByStrategiesList(strategy: String): List<Trade> {
        val arr = trades.filter { it.strategy == strategy }
        return arr
    }

    override fun getTradesByResult(result: String): List<Trade> {
        val arr = trades.filter { it.tradeResult == result }
        return arr
    }

    override fun getSortedByInstrumentList(instrument: String): List<Trade> {
        val arr = trades.filter { it.instrument == instrument }
        return arr
    }

    override fun getShortPos(): Int {
        val arr = trades.count{ it.tradeDirection == "Short" }
        return arr
    }

    override fun getLongPos(): Int {
        val arr = trades.count{ it.tradeDirection == "Long" }
        return arr
    }

    override fun getWinNumber(): Int {
        val arr = trades.count{ it.tradeResult == "Victory" }
        return arr
    }

    override fun getDefNumber(): Int {
        val arr = trades.count{ it.tradeResult == "Defeat" }
        return arr
    }

    override fun getDayStatistic(day: DaysOfWeek): List<Trade> {
        val arr = trades.filter{ it.tradeDate == day.name }
        return arr
    }

    override fun countTradesByResultAndDate(results: Results, day: DaysOfWeek): Int {
        val arr = trades.count { it.tradeResult == results.name && it.tradeDate == day.name }
        return arr
    }

    override fun getAllStrategies(): List<Strategy> {
        return strategies
    }

    override fun addStrategy(strategy: Strategy) {
        strategies.add(strategy)
    }

    override fun deleteStrategy(name: String) {
        strategies.remove(Strategy(0,name))
    }

    override fun getAllInstruments(): List<Instrument> {
        return instruments
    }

    override fun addInstrument(instrument: Instrument) {
        instruments.add(instrument)
    }

    override fun deleteInstrument(name: String) {
        instruments.remove(Instrument(0,name))
    }

    override fun getAllNotes(): List<NoteCard> {
       return notes
    }

    override fun addNote(noteCard: NoteCard) {
        notes.add(noteCard)
    }

    override fun updateNote(noteCard: NoteCard) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(noteCard: NoteCard) {
        notes.remove(noteCard)
    }

}