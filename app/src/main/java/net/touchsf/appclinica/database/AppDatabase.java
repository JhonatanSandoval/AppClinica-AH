package net.touchsf.appclinica.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.touchsf.appclinica.database.dao.DateDao;
import net.touchsf.appclinica.database.dao.HistoryDao;
import net.touchsf.appclinica.database.dao.UserDao;
import net.touchsf.appclinica.database.entity.Date;
import net.touchsf.appclinica.database.entity.History;
import net.touchsf.appclinica.database.entity.User;

@Database(entities = {
        User.class,
        Date.class,
        History.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract DateDao dateDao();

    public abstract HistoryDao historyDao();

}
