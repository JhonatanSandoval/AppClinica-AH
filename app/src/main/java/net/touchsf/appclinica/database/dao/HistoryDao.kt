package net.touchsf.appclinica.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import net.touchsf.appclinica.database.entity.History

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM histories WHERE user_id = :userId")
    fun getFromuser(userId: Int): List<History>

    @Insert
    fun insertDate(history: History)

}