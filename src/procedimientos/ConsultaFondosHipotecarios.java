package procedimientos;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaFondosHipotecarios extends JFrame {
    private JComboBox<String> comboBoxClientes;
    private RoundButton btnConsultar;
    private RoundButton btnSalir;
    private JLabel lblResultado;
    private JLabel lblFondoHipotecario;

    public ConsultaFondosHipotecarios() {
        setTitle("Consulta Fondos Hipotecario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 461, 277);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#D5D2CA"));

        JLabel lblID_USUARIO = new JLabel("ID Usuario:");
        lblID_USUARIO.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblID_USUARIO.setBounds(20, 101, 90, 16);
        lblID_USUARIO.setForeground(Color.decode("#003049"));
        getContentPane().add(lblID_USUARIO);
        
        JLabel lblNewLabel = new JLabel("Banco AJEDE");
        lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblNewLabel.setForeground(Color.decode("#003049"));
        lblNewLabel.setBounds(49, 23, 89, 13);
        getContentPane().add(lblNewLabel);
        
        try {
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource("/imagenes/logo.png"));
            Image imagen = bufferedImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon iconoRedimensionado = new ImageIcon(imagen);
            JLabel lblLogo = new JLabel(iconoRedimensionado);
            lblLogo.setBounds(10, 10, 43, 37);
            getContentPane().add(lblLogo);
            getContentPane().add(lblLogo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        comboBoxClientes.setBounds(135, 98, 200, 22);
        comboBoxClientes.setForeground(Color.decode("#003049"));
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);

        btnConsultar = new RoundButton("Consultar Fondos");
        btnConsultar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnConsultar.setBounds(164, 130, 150, 25);
        btnConsultar.setForeground(Color.decode("#003049"));
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarFondosHipotecario();
            }
        });
        getContentPane().add(btnConsultar);

        btnSalir = new RoundButton("Salir");
        btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSalir.setBounds(337, 205, 100, 25);
        btnSalir.setForeground(Color.decode("#003049"));
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);

        lblResultado = new JLabel("");
        lblResultado.setBounds(20, 130, 400, 16);
        lblResultado.setForeground(Color.decode("#003049"));
        getContentPane().add(lblResultado);
        
        lblFondoHipotecario = new JLabel("Fondo Hipotecario");
        lblFondoHipotecario.setForeground(new Color(0, 48, 73));
        lblFondoHipotecario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        lblFondoHipotecario.setBounds(135, 41, 205, 47);
        getContentPane().add(lblFondoHipotecario);
    }

    private void cargarClientesEnComboBox() {
        String consulta = "SELECT ID_USUARIO FROM clientes WHERE tipo_cuenta = 'Hipotecario'";
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
