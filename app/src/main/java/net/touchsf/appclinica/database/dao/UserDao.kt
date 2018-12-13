package net.touchsf.appclinica.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import net.touchsf.appclinica.database.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Insert
    fun insertAll(users: List<User>)

    @Insert
    fun insert(users: User)

    @Query("SELECT * FROM users WHERE documentType LIKE :documentType AND documentNumber LIKE :documentNumber")
    fun findByDocumentTypeAndNumber(documentType: String, documentNumber: String): User

    @Query("SELECT * FROM users WHERE uid = :userId")
    fun findByUserId(userId: Int) : User

}