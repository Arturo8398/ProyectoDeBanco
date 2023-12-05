package procedimientos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Retiro extends JFrame {

    private JComboBox<String> comboBoxClientes;
    private JComboBox<String> comboBoxTipoCuenta;  // Nueva ComboBox para el tipo de cuenta
    private JTextField txtMontoRetiro;
    private JButton btnRetirar;
    private JButton btnSalir;

    public Retiro() {
        setTitle("Retiro");
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

        JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta:");
        lblTipoCuenta.setBounds(20, 60, 120, 16);
        getContentPane().add(lblTipoCuenta);

        // Nueva ComboBox para el tipo de cuenta
        comboBoxTipoCuenta = new JComboBox<>();
        comboBoxTipoCuenta.addItem("Hipotecario");
        comboBoxTipoCuenta.addItem("Personal");
        comboBoxTipoCuenta.setBounds(150, 57, 120, 22);
        getContentPane().add(comboBoxTipoCuenta);

        JLabel lblMontoRetiro = new JLabel("Monto de Retiro:");
        lblMontoRetiro.setBounds(20, 90, 120, 16);
        getContentPane().add(lblMontoRetiro);

        txtMontoRetiro = new JTextField();
        txtMontoRetiro.setBounds(150, 87, 170, 22);
        getContentPane().add(txtMontoRetiro);

        btnRetirar = new JButton("Realizar Retiro");
        btnRetirar.setBounds(20, 130, 150, 25);
        btnRetirar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarRetiro();
            }
        });
        getContentPane().add(btnRetirar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 130, 100, 25);
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

    private boolean esCuentaHipotecaria(String idUsuario) {
        try {
            String consulta = "SELECT tipo_cuenta FROM Banco.Clientes WHERE ID_USUARIO = ?";
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

    private boolean esCuentaPersonal(String idUsuario) {
        try {
            String consulta = "SELECT tipo_cuenta FROM Banco.Clientes WHERE ID_USUARIO = ?";
            try (Connection conexion = Conexion.obtenerConexion();
                 PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, idUsuario);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        String tipoCuenta = resultSet.getString("tipo_cuenta");
                        return "Personal".equals(tipoCuenta);
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

    private void actualizarDatosDespuesRetiro(String idUsuario, double montoRetiro) throws SQLException {
        String tipoCuenta = obtenerTipoCuenta(idUsuario);

        if ("Hipotecario".equals(tipoCuenta)) {
            actualizarDatosDespuesRetiroHipotecario(idUsuario, montoRetiro);
        } else if ("Personal".equals(tipoCuenta)) {
            actualizarDatosDespuesRetiroPersonal(idUsuario, montoRetiro);
        }
    }

    private String obtenerTipoCuenta(String idUsuario) throws SQLException {
        String consulta = "SELECT tipo_cuenta FROM Banco.Clientes WHERE ID_USUARIO = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, idUsuario);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("tipo_cuenta");
                } else {
                    return "";
                }
            }
        }
    }

    private void actualizarDatosDespuesRetiro(String idUsuario, double montoRetiro, String tipoCuenta) throws SQLException {
        if ("Hipotecario".equals(tipoCuenta)) {
            actualizarDatosDespuesRetiroHipotecario(idUsuario, montoRetiro);
        } else if ("Personal".equals(tipoCuenta)) {
            actualizarDatosDespuesRetiroPersonal(idUsuario, montoRetiro);
        }
    }

    private void actualizarDatosDespuesRetiroHipotecario(String idUsuario, double montoRetiro) throws SQLException {
        String updateQuery = "UPDATE Banco.Credito_Hipotecario SET valor = valor - ? WHERE id_usuario = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, montoRetiro);
            pstmt.setString(2, idUsuario);
            pstmt.executeUpdate();
        }
    }

    private void actualizarDatosDespuesRetiroPersonal(String idUsuario, double montoRetiro) throws SQLException {
        String updateQuery = "UPDATE Banco.credito_personal SET ingresos = ingresos - ? WHERE id_usuario = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, montoRetiro);
            pstmt.setString(2, idUsuario);
            pstmt.executeUpdate();
        }
    }
    private void realizarRetiro() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();
            String tipoCuenta = comboBoxTipoCuenta.getSelectedItem().toString();
            double montoRetiro = Double.parseDouble(txtMontoRetiro.getText());

            if (Conexion.existeCliente(idUsuario)) {
                if ((tipoCuenta.equals("Hipotecario") && esCuentaHipotecaria(idUsuario)) ||
                    (tipoCuenta.equals("Personal") && esCuentaPersonal(idUsuario))) {

                    actualizarDatosDespuesRetiro(idUsuario, montoRetiro, tipoCuenta);
                    JOptionPane.showMessageDialog(this, "Retiro exitoso", "Retiro", JOptionPane.INFORMATION_MESSAGE);

                    // Después de realizar el retiro, actualiza la lista de clientes
                    cargarClientesEnComboBox();
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de cuenta no reconocido para el cliente seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar el retiro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void salir() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Retiro frame = new Retiro();
            frame.setVisible(true);
        });
    }
}
