package net.touchsf.appclinica.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import net.touchsf.appclinica.database.entity.Date

@Dao
public interface DateDao {

    @Query("SELECT * FROM dates")
    fun getAll(): List<Date>

}