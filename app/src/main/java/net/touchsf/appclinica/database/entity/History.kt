package net.touchsf.appclinica.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "histories")
data class History(
        @PrimaryKey(autoGenerate = true) var uid: Int = 0,
        @ColumnInfo(name = "user_id") var user_id: Int = 0,
        @ColumnInfo(name = "speciality") var speciality: String = "",
        @ColumnInfo(name = "doctor") var doctor: String = "",
        @ColumnInfo(name = "date") var date: String = "",
        @ColumnInfo(name = "diagnostic") var diagnostic: String = ""
)