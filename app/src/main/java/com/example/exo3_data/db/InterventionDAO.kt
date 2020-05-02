package com.example.exo3_data.db

import androidx.room.*


@Dao
interface InterventionDAO {

    @Insert
    suspend fun addIntervention(intervention: Intervention)

    @Query("SELECT * FROM intervention ORDER BY id DESC")
    suspend fun getAllInterventions() : List<Intervention>

    @Query("SELECT * FROM intervention WHERE interventionDate = :interventionDate")
    suspend fun getInterventionByDate(interventionDate:String) :List<Intervention>

    @Insert
    suspend fun addMultipleInterventions(vararg intervention: Intervention)

    @Update
    suspend fun updateIntervention(intervention: Intervention)

    @Delete
    suspend fun deleteIntervention(intervention:Intervention)
}