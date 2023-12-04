package procedimientos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormularioCreditoPersonal extends JFrame {

    private JComboBox<String> comboBoxClientes;
    private JTextField textFieldIngresos;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnMostrar;
    private JButton btnSalir;

    public FormularioCreditoPersonal() {
        setTitle("Credito Personal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        getContentPane().setLayout(null);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(129, 27, 200, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);

        JLabel lblIngresos = new JLabel("Ingresos:");
        lblIngresos.setBounds(29, 70, 61, 16);
        getContentPane().add(lblIngresos);

        textFieldIngresos = new JTextField();
        textFieldIngresos.setBounds(129, 67, 200, 22);
        getContentPane().add(textFieldIngresos);
        textFieldIngresos.setColumns(10);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(29, 120, 97, 25);
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCredito();
            }
        });
        getContentPane().add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(136, 120, 97, 25);
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCredito();
            }
        });
        getContentPane().add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(243, 120, 97, 25);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCredito();
            }
        });
        getContentPane().add(btnEliminar);

        btnMostrar = new JButton("Mostrar");
        btnMostrar.setBounds(350, 120, 97, 25);
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCreditos();
            }
        });
        getContentPane().add(btnMostrar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(377, 225, 97, 25);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        getContentPane().add(btnSalir);
        
        JLabel lblID_USUARIO = new JLabel("ID USUARIO:");
        lblID_USUARIO.setBounds(29, 30, 90, 16);
        getContentPane().add(lblID_USUARIO);
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

    private void guardarCredito() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();
            String tipoCuenta = obtenerTipoCuenta(idUsuario);

            if ("Personal".equals(tipoCuenta)) {
                double ingresos = Double.parseDouble(textFieldIngresos.getText());
                insertarCreditoPersonal(idUsuario, ingresos);
                JOptionPane.showMessageDialog(this, "Crédito Personal guardado exitosamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se puede guardar el crédito para cuentas no personales.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos para Ingresos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerTipoCuenta(String idUsuario) {
        Connection conexion = null;
        String tipoCuenta = "";
        try {
            conexion = Conexion.obtenerConexion();
            String consulta = "SELECT tipo_cuenta FROM clientes WHERE ID_USUARIO = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, idUsuario);
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    tipoCuenta = resultSet.getString("tipo_cuenta");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener el tipo de cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tipoCuenta;
    }

    private void insertarCreditoPersonal(String idUsuario, double ingresos) {
        Connection conexion = null;
        try {
            conexion = Conexion.obtenerConexion();

            String consulta = "INSERT INTO credito_personal (id_usuario, ingresos) VALUES (?, ?)";
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, idUsuario);
                pstmt.setDouble(2, ingresos);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el crédito personal.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void actualizarCredito() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();
            double ingresos = Double.parseDouble(textFieldIngresos.getText());

            Connection conexion = null;
            try {
                conexion = Conexion.obtenerConexion();

                String consulta = "UPDATE credito_personal SET ingresos = ? WHERE id_usuario = ?";
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setDouble(1, ingresos);
                    pstmt.setString(2, idUsuario);

                    int filasAfectadas = pstmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(this, "Crédito Personal actualizado exitosamente.");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Credito no encontrado para el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar el crédito personal.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos para Ingresos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCredito() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();

            Connection conexion = null;
            try {
                conexion = Conexion.obtenerConexion();

                String consulta = "DELETE FROM credito_personal WHERE id_usuario = ?";
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, idUsuario);

                    int filasAfectadas = pstmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(this, "Crédito Personal eliminado exitosamente.");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Credito no encontrado para el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el crédito personal.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos para Ingresos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarCreditos() {
        Connection conexion = null;
        try {
            conexion = Conexion.obtenerConexion();

            String consulta = "SELECT * FROM credito_personal";
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                ResultSet resultSet = pstmt.executeQuery();

                StringBuilder mensaje = new StringBuilder("Lista de Créditos Personales:\n");

                while (resultSet.next()) {
                    String idUsuario = resultSet.getString("id_usuario");
                    double ingresos = resultSet.getDouble("ingresos");

                    mensaje.append("ID USUARIO: ").append(idUsuario).append(", Ingresos: ").append(ingresos).append("\n");
                }

                JOptionPane.showMessageDialog(this, mensaje.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al mostrar los créditos personales.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void limpiarCampos() {
        textFieldIngresos.setText("");
    }

    private void salir() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormularioCreditoPersonal frame = new FormularioCreditoPersonal();
            frame.setVisible(true);
        });
    }
}
