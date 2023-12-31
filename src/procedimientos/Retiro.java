package procedimientos;

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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Retiro extends JFrame {

    private JComboBox<String> comboBoxClientes;
    private JComboBox<String> comboBoxTipoCuenta;  // Nueva ComboBox para el tipo de cuenta
    private JTextField txtMontoRetiro;
    private RoundButton btnRetirar;
    private RoundButton btnSalir;
    private JLabel lblHacerRetiro;

    public Retiro() {
        setTitle("Retiro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 461, 277);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#D5D2CA"));

        JLabel lblID_USUARIO = new JLabel("ID Usuario:");
        lblID_USUARIO.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblID_USUARIO.setBounds(20, 84, 90, 16);
        getContentPane().add(lblID_USUARIO);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(133, 81, 170, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);
        
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

        JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta:");
        lblTipoCuenta.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblTipoCuenta.setBounds(20, 116, 120, 16);
        getContentPane().add(lblTipoCuenta);

        // Nueva ComboBox para el tipo de cuenta
        comboBoxTipoCuenta = new JComboBox<>();
        comboBoxTipoCuenta.addItem("Hipotecario");
        comboBoxTipoCuenta.addItem("Personal");
        comboBoxTipoCuenta.setBounds(133, 113, 170, 22);
        getContentPane().add(comboBoxTipoCuenta);

        JLabel lblMontoRetiro = new JLabel("Monto de Retiro:");
        lblMontoRetiro.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblMontoRetiro.setBounds(20, 147, 120, 16);
        getContentPane().add(lblMontoRetiro);

        txtMontoRetiro = new JTextField();
        txtMontoRetiro.setBounds(133, 145, 170, 22);
        getContentPane().add(txtMontoRetiro);
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        setIconImage(icono.getImage());

        btnRetirar = new RoundButton("Realizar Retiro");
        btnRetirar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnRetirar.setBounds(144, 177, 150, 25);
        btnRetirar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarRetiro();
            }
        });
        getContentPane().add(btnRetirar);

        btnSalir = new RoundButton("Salir");
        btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSalir.setBounds(337, 205, 100, 25);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);
        
        lblHacerRetiro = new JLabel("Hacer Retiro");
        lblHacerRetiro.setForeground(new Color(0, 48, 73));
        lblHacerRetiro.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        lblHacerRetiro.setBounds(153, 22, 150, 47);
        getContentPane().add(lblHacerRetiro);
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
