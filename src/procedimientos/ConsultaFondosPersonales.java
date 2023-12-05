package procedimientos;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Image;

public class ConsultaFondosPersonales extends JFrame {

    private JComboBox<String> comboBoxClientes;
    private RoundButton btnConsultar;
    private RoundButton btnSalir;
    private JLabel lblResultado;
    private JLabel lblFondoPersonal;

    public ConsultaFondosPersonales() {
        setTitle("Consulta Fondos Personales");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 461, 277);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#D5D2CA"));

        JLabel lblID_USUARIO = new JLabel("ID Usuario:");
        lblID_USUARIO.setBounds(20, 127, 90, 16);
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
        comboBoxClientes.setBounds(120, 124, 200, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);

        btnConsultar = new RoundButton("Consultar Fondos Personales");
        btnConsultar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnConsultar.setBounds(120, 156, 200, 25);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarFondosPersonales();
            }
        });
        getContentPane().add(btnConsultar);

        btnSalir = new RoundButton("Salir");
        btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSalir.setBounds(357, 205, 80, 25);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);

        lblResultado = new JLabel("");
        lblResultado.setBounds(20, 130, 400, 16);
        getContentPane().add(lblResultado);
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        setIconImage(icono.getImage());
        
        lblFondoPersonal = new JLabel("Fondo Personal");
        lblFondoPersonal.setForeground(new Color(0, 48, 73));
        lblFondoPersonal.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        lblFondoPersonal.setBounds(135, 48, 205, 47);
        getContentPane().add(lblFondoPersonal);
    }

    private void cargarClientesEnComboBox() {
        String consulta = "SELECT ID_USUARIO FROM Clientes WHERE tipo_cuenta = 'Personal'";
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

    private void consultarFondosPersonales() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();

            if (Conexion.existeCliente(idUsuario) && esCuentaPersonal(idUsuario)) {
                double fondos = obtenerFondosPersonales(idUsuario);
                String infoCliente = obtenerInformacionCliente(idUsuario);

                String mensaje = "Cliente: " + infoCliente + "\nFondos Personales: $" + fondos;
                JOptionPane.showMessageDialog(this, mensaje, "Consulta Fondos Personales", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado o no es una cuenta personal", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar fondos personales.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private double obtenerFondosPersonales(String idUsuario) throws SQLException {
        String consulta = "SELECT SUM(ingresos) AS fondos FROM Banco.credito_personal WHERE id_usuario = ?";
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
        String consulta = "SELECT NOMBRE, APELLIDOS FROM Banco.Clientes WHERE ID_USUARIO = ?";
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
            ConsultaFondosPersonales frame = new ConsultaFondosPersonales();
            frame.setVisible(true);
        });
    }
}
