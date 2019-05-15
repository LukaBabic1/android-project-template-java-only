package template.android.com.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import template.android.com.data.dao.model.ExampleDbModel;

@Dao
public interface ExampleDao {

    @Query("SELECT * FROM exampledbmodel WHERE id = :id")
    ExampleDbModel getById(int id);


    @Query("SELECT * FROM exampledbmodel WHERE name LIKE :name")
    ExampleDbModel getByName(String name);

    @Query("SELECT * FROM exampledbmodel")
    List<ExampleDbModel> getAll();

    @Insert
    void insert(ExampleDbModel model, ExampleDbModel... models);

    @Query("DELETE FROM exampleDbModel")
    void deleteAll();
}
