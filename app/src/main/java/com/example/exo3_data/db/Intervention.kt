package com.example.exo3_data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "intervention")
data class Intervention(

    @ColumnInfo(name ="interventionDate") val interventionDate:String,

    @ColumnInfo(name ="nomPlombier") val nomPlombier:String,

    @ColumnInfo(name ="typeIntervention") val typeIntervention: String

) :Serializable {

    @PrimaryKey(autoGenerate = true) var id : Int = 0
}