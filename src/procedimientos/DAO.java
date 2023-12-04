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
class ClienteDAO implements DAO<Cliente>{

    @Override
    public int create(Cliente dato) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Cliente read(String id) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(Cliente dato) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(String Id) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<Cliente> readAll() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
