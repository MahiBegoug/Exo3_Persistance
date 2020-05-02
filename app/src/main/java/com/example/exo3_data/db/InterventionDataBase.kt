package com.example.exo3_data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Intervention::class],version = 1)
abstract class InterventionDataBase : RoomDatabase(){

    abstract fun getInterventionDao() :InterventionDAO

    companion object {

        @Volatile private var instance : InterventionDataBase? =null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {


            instance ?:buildDateBase(context).also {
                instance = it
            }

        }

        private fun buildDateBase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            InterventionDataBase::class.java,
            "intervention.db"
        ).build()

    }



}