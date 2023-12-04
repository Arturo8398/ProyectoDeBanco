package procedimientos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DAOCreditoHipotecario implements DAO<CreditoHipotecario> {

	@Override
	public int create(CreditoHipotecario dato) throws SQLException {

		String idUsuario = dato.getId();
		String direccion = dato.getDireccion();
		String valor = dato.getValor();
		try {

			if (Conexion.existeCliente(idUsuario) && esCuentaHipotecaria(idUsuario)) {
				String consulta = "INSERT INTO credito_hipotecario (id_usuario, direccion, valor) VALUES (?, ?, ?)";
				try (Connection conexion = Conexion.obtenerConexion();
						PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
					pstmt.setString(1, idUsuario);
					pstmt.setString(2, direccion);
					pstmt.setString(3, valor);

					pstmt.executeUpdate();
				}
				JOptionPane.showMessageDialog(null, "Crédito Hipotecario guardado exitosamente.");
			} else {
				JOptionPane.showMessageDialog(null, "Cliente no encontrado o no es una cuenta hipotecario", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Ingrese un valor válido para el Valor de la Hipoteca.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar el crédito hipotecario.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return 0;
	}

	@Override
	public CreditoHipotecario read(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(CreditoHipotecario dato) throws SQLException {
		String idUsuario = dato.getId();
		String direccion = dato.getDireccion();
		String valor = dato.getValor();
		
		try {

			String consulta = "UPDATE credito_hipotecario SET direccion = ?, valor = ? WHERE id_usuario = ?";
			try (Connection conexion = Conexion.obtenerConexion();
					PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
				pstmt.setString(1, direccion);
				pstmt.setString(2, valor);
				pstmt.setString(3, idUsuario);

				int filasAfectadas = pstmt.executeUpdate();
				if (filasAfectadas > 0) {
					JOptionPane.showMessageDialog(null, "Crédito Hipotecario actualizado exitosamente.");
				} else {
					JOptionPane.showMessageDialog(null, "Crédito no encontrado para el ID proporcionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Ingrese valores válidos para ID, Dirección y Valor de la Hipoteca.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar el crédito hipotecario.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return 0;
	}

	@Override
	public int delete(String Id) throws SQLException {
		try {
        	

            String consulta = "DELETE FROM credito_hipotecario WHERE id_usuario = ?";
            try (Connection conexion = Conexion.obtenerConexion();
                 PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, Id);

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Crédito Hipotecario eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Crédito no encontrado para el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor válido para ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el crédito hipotecario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
		return 0;
	}

	@Override
	public ArrayList<CreditoHipotecario> readAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean esCuentaHipotecaria(String idUsuario) {
		try {
			String consulta = "SELECT tipo_cuenta FROM clientes WHERE ID_USUARIO = ?";
			try (Connection conexion = Conexion.obtenerConexion();
					PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
				pstmt.setString(1, idUsuario);
				try (ResultSet resultSet = pstmt.executeQuery()) {
					if (resultSet.next()) {
						String tipoCuenta = resultSet.getString("tipo_cuenta");
						return "Hipotecario".equals(tipoCuenta);
					} else {
						return false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
