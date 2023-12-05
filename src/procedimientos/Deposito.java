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

public class Deposito extends JFrame {

    private JComboBox <String> comboBoxClientes;
    private JTextField txtMontoDeposito;
    private RoundButton btnDepositar;
    private RoundButton btnSalir;

    public Deposito() {
        setTitle("Depósito");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 461, 277);
        getContentPane().setLayout(null);
        setBackground(Color.decode("#D5D2CA"));
        getContentPane().setBackground(Color.decode("#D5D2CA"));
        
        JLabel lblID_USUARIO = new JLabel("ID Usuario:");
        lblID_USUARIO.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblID_USUARIO.setBounds(20, 95, 90, 16);
        lblID_USUARIO.setForeground(Color.decode("#003049"));
        getContentPane().add(lblID_USUARIO);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(137, 92, 170, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);

        JLabel lblMontoDeposito = new JLabel("Monto de Depósito:");
        lblMontoDeposito.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblMontoDeposito.setBounds(20, 126, 120, 16);
        lblMontoDeposito.setForeground(Color.decode("#003049"));
        getContentPane().add(lblMontoDeposito);

        txtMontoDeposito = new JTextField();
        txtMontoDeposito.setBounds(137, 124, 170, 22);
        getContentPane().add(txtMontoDeposito);
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        setIconImage(icono.getImage());
        
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

        btnDepositar = new RoundButton("Realizar Depósito");
        btnDepositar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnDepositar.setBounds(150, 156, 150, 25);
        btnDepositar.setForeground(Color.decode("#003049"));
        btnDepositar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarDeposito();
            }
        });
        getContentPane().add(btnDepositar);
        

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
        
        lblHacerDepsito = new JLabel("Hacer Depósito");
        lblHacerDepsito.setForeground(new Color(0, 48, 73));
        lblHacerDepsito.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        lblHacerDepsito.setBounds(137, 22, 175, 47);
        getContentPane().add(lblHacerDepsito);
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

        String updateQuery = "UPDATE credito_personal SET ingresos = ingresos + ? WHERE id_usuario = ?";
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
    private JLabel lblHacerDepsito;

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
