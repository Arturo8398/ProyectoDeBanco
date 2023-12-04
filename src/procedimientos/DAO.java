package procedimientos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO<T> {
    public int create(T dato) throws SQLException;
    public T read(String id) throws SQLException;
    public int update(T dato) throws SQLException;
    public int delete(String Id) throws SQLException;
    public ArrayList<T> readAll() throws SQLException;
}
