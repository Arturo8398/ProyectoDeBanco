package procedimientos;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCreditoPersonal implements DAO<CreditoPersonal>{

	@Override
	public int create(CreditoPersonal dato) throws SQLException {
		
		return 0;
	}

	@Override
	public CreditoPersonal read(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(CreditoPersonal dato) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String Id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<CreditoPersonal> readAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
