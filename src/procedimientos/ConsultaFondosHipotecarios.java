package procedimientos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaFondosHipotecarios extends JFrame {
    private JComboBox<String> comboBoxClientes;
    private JButton btnConsultar;
    private JButton btnSalir;
    private JLabel lblResultado;

    public ConsultaFondosHipotecarios() {
        setTitle("Consulta Fondos Hipotecario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 356, 200);
        getContentPane().setLayout(null);

        JLabel lblID_USUARIO = new JLabel("ID USUARIO:");
        lblID_USUARIO.setBounds(20, 30, 90, 16);
        getContentPane().add(lblID_USUARIO);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(120, 27, 200, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);

        btnConsultar = new JButton("Consultar Fondos");
        btnConsultar.setBounds(20, 101, 150, 25);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarFondosHipotecario();
            }
        });
        getContentPane().add(btnConsultar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(200, 101, 100, 25);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);

        lblResultado = new JLabel("");
        lblResultado.setBounds(20, 130, 400, 16);
        getContentPane().add(lblResultado);
    }

    private void cargarClientesEnComboBox() {
        String consulta = "SELECT ID_USUARIO FROM clientes";
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

    private void consultarFondosHipotecario() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();

            if (Conexion.existeCliente(idUsuario) && esCuentaHipotecaria(idUsuario)) {
                double fondos = obtenerFondosHipotecario(idUsuario);
                String infoCliente = obtenerInformacionCliente(idUsuario);

                String mensaje = "Cliente: " + infoCliente + "\nFondos Hipotecarios: $" + fondos;
                JOptionPane.showMessageDialog(this, mensaje, "Consulta Fondos Hipotecarios", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado o no es una cuenta hipotecaria", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar fondos hipotecarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private double obtenerFondosHipotecario(String idUsuario) throws SQLException {
        String consulta = "SELECT SUM(valor) AS fondos FROM credito_hipotecario WHERE id_usuario = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, idUsuario);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("fondos");
                } else {
                    return 0.0;
                }
            }
        }
    }

    private String obtenerInformacionCliente(String idUsuario) throws SQLException {
        String consulta = "SELECT NOMBRE, APELLIDOS FROM clientes WHERE ID_USUARIO = ?";
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, idUsuario);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("NOMBRE");
                    String apellidos = resultSet.getString("APELLIDOS");
                    return nombre + " " + apellidos;
                } else {
                    return "";
                }
            }
        }
    }

    private void salir() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaFondosHipotecarios frame = new ConsultaFondosHipotecarios();
            frame.setVisible(true);
        });
    }
}
