//Jorge Monroy Peña 
package procedimientos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deposito extends JFrame {

    private JComboBox<String> comboBoxClientes;
    private JTextField txtMontoDeposito;
    private JButton btnDepositar;
    private JButton btnSalir;

    public Deposito() {
        setTitle("Depósito");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 356, 200);
        getContentPane().setLayout(null);

        JLabel lblID_USUARIO = new JLabel("ID USUARIO:");
        lblID_USUARIO.setBounds(20, 30, 90, 16);
        getContentPane().add(lblID_USUARIO);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(120, 27, 150, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);

        JLabel lblMontoDeposito = new JLabel("Monto de Depósito:");
        lblMontoDeposito.setBounds(20, 60, 120, 16);
        getContentPane().add(lblMontoDeposito);

        txtMontoDeposito = new JTextField();
        txtMontoDeposito.setBounds(150, 57, 170, 22);
        getContentPane().add(txtMontoDeposito);

        btnDepositar = new JButton("Realizar Depósito");
        btnDepositar.setBounds(20, 101, 150, 25);
        btnDepositar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarDeposito();
            }
        });
        getContentPane().add(btnDepositar);
        

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 101, 100, 25);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);
    }

    private void cargarClientesEnComboBox() {
        String consulta = "SELECT ID_USUARIO FROM Banco.Clientes";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                comboBoxClientes.addItem(resultSet.getString("ID_USUARIO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los clientes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarDeposito() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();
            double montoDeposito = Double.parseDouble(txtMontoDeposito.getText());

            if (Conexion.existeCliente(idUsuario)) {
                String tipoCuenta = Conexion.obtenerTipoCuenta(idUsuario);

                if ("Hipotecario".equals(tipoCuenta)) {
                    realizarDepositoHipotecario(idUsuario, montoDeposito);
                } else if ("Personal".equals(tipoCuenta)) {
                    realizarDepositoPersonal(idUsuario, montoDeposito);
                }

                JOptionPane.showMessageDialog(this, "Depósito exitoso", "Depósito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void realizarDepositoHipotecario(String idUsuario, double montoDeposito) throws SQLException {

        String updateQuery = "UPDATE Banco.Credito_Hipotecario SET valor = valor + ? WHERE id_usuario = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, montoDeposito);
            pstmt.setString(2, idUsuario);
            pstmt.executeUpdate();
        }
    }

    private void realizarDepositoPersonal(String idUsuario, double montoDeposito) throws SQLException {

        String updateQuery = "UPDATE Banco.credito_personal SET ingresos = ingresos + ? WHERE id_usuario = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, montoDeposito);
            pstmt.setString(2, idUsuario);
            pstmt.executeUpdate();
        }
    }
    ActionListener depositoListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            realizarDeposito();
        }
    };

    private void salir() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Deposito frame = new Deposito();
            frame.setVisible(true);
        });
    }
}
