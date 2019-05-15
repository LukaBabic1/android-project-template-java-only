package template.android.com.data.dao;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import template.android.com.data.dao.database.AppDatabase;
import template.android.com.data.dao.model.ExampleDbModel;

public final class ExampleDaoTest {

    private static final String DATABASE_NAME = "test_database";

    private AppDatabase appDatabase;
    private ExampleDao exampleDao;

    @Before
    public void setUp() throws Exception {
        appDatabase = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(), AppDatabase.class, DATABASE_NAME)
                          .build();

        exampleDao = appDatabase.exampleDao();

        exampleDao.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        InstrumentationRegistry.getTargetContext()
                               .deleteDatabase(DATABASE_NAME);
    }

    @Test
    public void testDbEmptyWhenInitialized() {
        final List<ExampleDbModel> exampleDbModels = exampleDao.getAll();

        Assert.assertNotNull(exampleDbModels);
        Assert.assertTrue(exampleDbModels.isEmpty());
    }

    @Test
    public void testInsertingInstance() {
        exampleDao.insert(new ExampleDbModel("luka"),
                          new ExampleDbModel("matko"),
                          new ExampleDbModel("martina"));

        final ExampleDbModel exampleDbModelFromDao = exampleDao.getByName("luka");
        Assert.assertEquals(1, exampleDbModelFromDao.id);
        Assert.assertEquals("luka", exampleDbModelFromDao.name);
    }
}
