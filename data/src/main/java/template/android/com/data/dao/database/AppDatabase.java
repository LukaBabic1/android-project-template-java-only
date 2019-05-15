package template.android.com.data.dao.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import template.android.com.data.dao.ExampleDao;
import template.android.com.data.dao.model.ExampleDbModel;

@Database(
        entities = {
                ExampleDbModel.class
        },
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExampleDao exampleDao();
}
