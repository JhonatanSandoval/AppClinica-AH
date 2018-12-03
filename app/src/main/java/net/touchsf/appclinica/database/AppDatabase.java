package net.touchsf.appclinica.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.touchsf.appclinica.database.dao.UserDao;
import net.touchsf.appclinica.database.entity.User;

@Database(entities = {
        User.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
