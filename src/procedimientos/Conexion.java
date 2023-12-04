
package procedimientos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/Banco";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	static Connection obtenerConexion() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al establecer la conexión a la base de datos", e);
		}
		return connection;
	}

	public static void realizarRetiro(String idUsuario, double montoRetiro) {
		try (Connection connection = obtenerConexion()) {
			if (!existeCuenta(idUsuario)) {
				mostrarMensajeError("No existe la cuenta para el ID de usuario proporcionado");
				return;
			}

			double saldoActual = obtenerSaldo(idUsuario);

			if (montoRetiro > saldoActual) {
				mostrarMensajeError("Saldo insuficiente para realizar el retiro");
				return;
			}

			double nuevoSaldo = saldoActual - montoRetiro;
			actualizarSaldo(idUsuario, nuevoSaldo);

			JOptionPane.showMessageDialog(null, "Retiro exitoso. Nuevo saldo: " + nuevoSaldo);
		} catch (SQLException e) {
			mostrarMensajeError("Error al realizar el retiro");
		}
	}

	private static boolean existeCuenta(String idUsuario) throws SQLException {
		String query = "SELECT COUNT(*) FROM cuentas WHERE ID_USUARIO = ?";
		try (Connection connection = obtenerConexion();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, idUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

	public static boolean existeCliente(String idUsuario) {
		String query = "SELECT COUNT(*) FROM clientes WHERE ID_USUARIO = ?";
		try (Connection connection = obtenerConexion();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, idUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al verificar la existencia del cliente", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	private static double obtenerSaldo(String idUsuario) throws SQLException {
		String query = "SELECT SALDO FROM cuentas WHERE ID_USUARIO = ?";
		try (Connection connection = obtenerConexion();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, idUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getDouble("SALDO");
				}
			}
		}
		return 0.0;
	}

	private static void actualizarSaldo(String idUsuario, double nuevoSaldo) throws SQLException {
		String query = "UPDATE cuentas SET SALDO = ? WHERE ID_USUARIO = ?";
		try (Connection connection = obtenerConexion();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDouble(1, nuevoSaldo);
			statement.setString(2, idUsuario);
			statement.executeUpdate();
		}
	}

	public static void insertarCliente(String idUsuario, String nombre, String apellidos, String correo, String telefono, String tipoCuenta) {
        
    }

	public static void eliminarCliente(String idUsuario) {
		try (Connection connection = obtenerConexion()) {
			String query = "DELETE FROM clientes WHERE ID_USUARIO = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, idUsuario);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al eliminar cliente de la base de datos", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static ResultSet obtenerDatosCliente(String idUsuario) {
		ResultSet resultSet = null;
		try (Connection connection = obtenerConexion()) {
			String query = "SELECT NOMBRE, APELLIDOS, CORREO, TELEFONO, TIPO_CUENTA FROM clientes WHERE ID_USUARIO = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, idUsuario);
				resultSet = statement.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al obtener datos del cliente", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return resultSet;
	}

	public static Cliente seleccionarCliente(String idUsuario) {
		Cliente cliente = null;
		try (Connection connection = obtenerConexion()) {
			String query = "SELECT * FROM clientes WHERE ID_USUARIO = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, idUsuario);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						String nombre = resultSet.getString("NOMBRE");
						String apellidos = resultSet.getString("APELLIDOS");
						String correo = resultSet.getString("CORREO");
						String telefono = resultSet.getString("TELEFONO");
						String tipoCuenta = resultSet.getString("TIPO_CUENTA");

						cliente = new Cliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al seleccionar cliente", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return cliente;
	}

	public static String obtenerTipoCuenta(String idUsuario) {
		String tipoCuenta = null;
		String query = "SELECT TIPO_CUENTA FROM Clientes WHERE id_usuario = ?";

		try (Connection connection = obtenerConexion();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, idUsuario);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					tipoCuenta = resultSet.getString("TIPO_CUENTA");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al obtener el tipo de cuenta", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return tipoCuenta;
	}

	public static void actualizarCliente(String idUsuario, String nombre, String apellidos, String correo,
			String telefono, String tipoCuenta) {
		try (Connection connection = obtenerConexion()) {
			String query = "UPDATE clientes SET NOMBRE=?, APELLIDOS=?, CORREO=?, TELEFONO=?, TIPO_CUENTA=? WHERE ID_USUARIO=?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, nombre);
				statement.setString(2, apellidos);
				statement.setString(3, correo);
				statement.setString(4, telefono);
				statement.setString(5, tipoCuenta);
				statement.setString(6, idUsuario);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar cliente en la base de datos", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
