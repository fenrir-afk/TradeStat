package com.example.tradestat.repository

import com.example.tradestat.data.TradeDatabase
import com.example.tradestat.data.model.DaysOfWeek
import com.example.tradestat.data.model.Directions
import com.example.tradestat.data.model.Instrument
import com.example.tradestat.data.model.NoteCard
import com.example.tradestat.data.model.Results
import com.example.tradestat.data.model.Strategy
import com.example.tradestat.data.model.Trade

open class TradesRepository(private val db: TradeDatabase):BaseRepository {
    //Trade part
    override fun addTrade(trade: Trade){
        db.getTradeDao().insert(trade)
    }
    override fun deleteTrade(trade: Trade){
        db.getTradeDao().delete(trade)
    }
    override fun getTradesSortedByDateDescending():List<Trade>{
        return db.getTradeDao().sortByDate()
    }
    override fun getTradesSortedByDateAscending():List<Trade>{
        return db.getTradeDao().getAll()
    }
    override fun getSortedByStrategiesList(strategy: String):List<Trade>{
        return db.getTradeDao().sortByStrategy(strategy)
    }


    override fun getTradesByResult(result: String): List<Trade> {
        return db.getTradeDao().getTradesByResult(result)
    }

    override fun getSortedByInstrumentList(instrument: String):List<Trade>{
        return db.getTradeDao().sortByInstrument(instrument)
    }
    override fun getShortPos(): Int {
        return db.getTradeDao().countTradesByDirection(Directions.Short.name)
    }

    override fun getLongPos(): Int {
        return  db.getTradeDao().countTradesByDirection(Directions.Long.name)
    }

    override fun getWinNumber(): Int {
        return db.getTradeDao().countTradesByResult(Results.Victory.name)
    }

    override fun getDefNumber(): Int {
        return  db.getTradeDao().countTradesByResult(Results.Defeat.name)
    }

    override fun getDayStatistic(day:DaysOfWeek): List<Trade> {
        return  db.getTradeDao().getTradesByDay(day.name)
    }
    override fun countTradesByResultAndDate(results: Results, day:DaysOfWeek): Int {
        return  db.getTradeDao().countTradesByResultAndDate(results.name,day.name)
    }



    //Strategy part


    override fun getAllStrategies(): List<Strategy> {
        return db.getStrategyDao().getAllStrategies()
    }

    override fun addStrategy(strategy: Strategy) {
        db.getStrategyDao().insertStrategy(strategy)
    }
    override fun deleteStrategy(name: String) {
        db.getStrategyDao().deleteStrategyByName(name)
    }



    //instrument part


    override fun getAllInstruments(): List<Instrument> {
        return db.getInstrumentDao().getAllInstruments()
    }

    override fun addInstrument(instrument: Instrument) {
        db.getInstrumentDao().insertInstrument(instrument)
    }
    override fun deleteInstrument(name: String) {
        db.getInstrumentDao().deleteInstrumentByName(name)
    }

    //Note part
    override fun getAllNotes(): List<NoteCard> {
        return db.getNoteDao().getAllNotes()
    }

    override fun addNote(noteCard: NoteCard) {
        db.getNoteDao().insertNote(noteCard)
    }
    override fun updateNote(noteCard: NoteCard) {
        db.getNoteDao().update(noteCard)
    }
    override fun deleteNote(noteCard: NoteCard) {
        db.getNoteDao().delete(noteCard)
    }

}