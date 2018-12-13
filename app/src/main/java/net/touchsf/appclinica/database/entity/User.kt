package net.touchsf.appclinica.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true) var uid: Int = 0,
        @ColumnInfo(name = "documentType") var documentType: String? = null,
        @ColumnInfo(name = "documentNumber") var documentNumber: String? = null,
        @ColumnInfo(name = "password") var password: String? = null,
        @ColumnInfo(name = "names") var names: String? = null,
        @ColumnInfo(name = "firstLastName") var firstLastName: String? = null,
        @ColumnInfo(name = "secondLastName") var secondLastName: String? = null,
        @ColumnInfo(name = "civilState") var civilState: String? = null,
        @ColumnInfo(name = "birthday") var birthday: String? = null,
        @ColumnInfo(name = "placeBirth") var placeBirth: String? = null
)