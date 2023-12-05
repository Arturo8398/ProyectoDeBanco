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

public class FormularioCreditoHipotecario extends JFrame {
    private JTextField textFieldDireccion;
    private JTextField textFieldValor;
    private JComboBox<String> comboBoxClientes;
    private RoundButton btnGuardar;
    private RoundButton btnActualizar;
    private RoundButton btnEliminar;
    private RoundButton btnMostrar;
    private RoundButton btnSalir;
    private JLabel lblCditoHipotecario;

    public FormularioCreditoHipotecario() {
        getContentPane().setBackground(Color.decode("#D5D2CA"));
        setTitle("Crédito Hipotecario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        getContentPane().setLayout(null);
        setBackground(Color.decode("#D5D2CA"));

        JLabel lblID_USUARIO = new JLabel("ID Usuario:");
        lblID_USUARIO.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblID_USUARIO.setBounds(29, 67, 90, 16);
        lblID_USUARIO.setForeground(Color.decode("#003049"));
        getContentPane().add(lblID_USUARIO);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(154, 64, 200, 22);
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

        JLabel lblDireccion = new JLabel("Dirección Hipoteca:");
        lblDireccion.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblDireccion.setBounds(29, 98, 123, 16);
        lblDireccion.setForeground(Color.decode("#003049"));
        getContentPane().add(lblDireccion);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(154, 96, 200, 22);
        getContentPane().add(textFieldDireccion);
        textFieldDireccion.setColumns(10);

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblValor.setBounds(29, 130, 61, 16);
        lblValor.setForeground(Color.decode("#003049"));
        getContentPane().add(lblValor);

        textFieldValor = new JTextField();
        textFieldValor.setBounds(154, 128, 200, 22);
        getContentPane().add(textFieldValor);
        textFieldValor.setColumns(10);

        btnGuardar = new RoundButton("Guardar");
        btnGuardar.setBounds(29, 160, 97, 25);
        btnGuardar.setForeground(Color.decode("#003049"));

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCreditoHipotecario();
            }
        });
        getContentPane().add(btnGuardar);

        btnActualizar = new RoundButton("Actualizar");
        btnActualizar.setBounds(136, 160, 97, 25);
        btnActualizar.setForeground(Color.decode("#003049"));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCreditoHipotecario();
            }
        });
        getContentPane().add(btnActualizar);

        btnEliminar = new RoundButton("Eliminar");
        btnEliminar.setBounds(243, 160, 97, 25);
        btnEliminar.setForeground(Color.decode("#003049"));
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCreditoHipotecario();
            }
        });
        getContentPane().add(btnEliminar);

        btnMostrar = new RoundButton("Mostrar");
        btnMostrar.setBounds(350, 160, 97, 25);
        btnMostrar.setForeground(Color.decode("#003049"));
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCreditosHipotecarios();
            }
        });
        getContentPane().add(btnMostrar);

        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        setIconImage(icono.getImage());

        btnSalir = new RoundButton("Salir");
        btnSalir.setBounds(377, 225, 97, 25);
        btnSalir.setForeground(Color.decode("#003049"));
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);

        lblCditoHipotecario = new JLabel("Crédito Hipotecario");
        lblCditoHipotecario.setForeground(new Color(0, 48, 73));
        lblCditoHipotecario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        lblCditoHipotecario.setBounds(154, 7, 205, 47);
        getContentPane().add(lblCditoHipotecario);
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

    private void guardarCreditoHipotecario() {
        String idUsuario = comboBoxClientes.getSelectedItem().toString();
        String direccion = textFieldDireccion.getText();
        double valor = Double.parseDouble(textFieldValor.getText());

        DAOCreditoHipotecario ch = new DAOCreditoHipotecario();
        CreditoHipotecario chipo = new CreditoHipotecario(idUsuario, direccion, String.valueOf(valor));
        try {
            ch.create(chipo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el crédito hipotecario");
        }
    }

    private void actualizarCreditoHipotecario() {
        String idUsuario = comboBoxClientes.getSelectedItem().toString();
        String direccion = textFieldDireccion.getText();
        double valor = Double.parseDouble(textFieldValor.getText());

        DAOCreditoHipotecario ch = new DAOCreditoHipotecario();
        CreditoHipotecario chipo = new CreditoHipotecario(idUsuario, direccion, String.valueOf(valor));
        try {
            ch.update(chipo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el crédito hipotecario");
        }
    }

    private void eliminarCreditoHipotecario() {
        String idUsuario = comboBoxClientes.getSelectedItem().toString();
        DAOCreditoHipotecario el = new DAOCreditoHipotecario();
        try {
            el.delete(idUsuario);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el crédito hipotecario");
        }
    }

    private void mostrarCreditosHipotecarios() {
        String consulta = "SELECT * FROM credito_hipotecario";
        try (Connection conexion = Conexion.obtenerConexion();
                PreparedStatement pstmt = conexion.prepareStatement(consulta);
                ResultSet resultSet = pstmt.executeQuery()) {
            StringBuilder mensaje = new StringBuilder("Lista de Créditos Hipotecarios:\n");

            while (resultSet.next()) {
                int idUsuario = resultSet.getInt("id_usuario");
                String direccion = resultSet.getString("direccion");
                double valor = resultSet.getDouble("valor");

                mensaje.append("ID USUARIO: ").append(idUsuario).append(", Dirección: ").append(direccion)
                        .append(", Valor: ").append(valor).append("\n");
            }

            JOptionPane.showMessageDialog(this, mensaje.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al mostrar los créditos hipotecarios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        textFieldDireccion.setText("");
        textFieldValor.setText("");
    }

    private void salir() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormularioCreditoHipotecario frame = new FormularioCreditoHipotecario();
            frame.setVisible(true);
        });
    }
}