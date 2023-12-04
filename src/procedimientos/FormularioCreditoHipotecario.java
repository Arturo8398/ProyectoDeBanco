
package procedimientos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormularioCreditoHipotecario extends JFrame {
    private JTextField textFieldDireccion;
    private JTextField textFieldValor;
    private JTextField textID_USUARIO;  
    private JComboBox<String> comboBoxClientes;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnMostrar;
    private JButton btnSalir;

    public FormularioCreditoHipotecario() {
        setTitle("Crédito Hipotecario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        getContentPane().setLayout(null);
        
        JLabel lblID_USUARIO = new JLabel("ID USUARIO:");
        lblID_USUARIO.setBounds(29, 30, 90, 16);
        getContentPane().add(lblID_USUARIO);

        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.setBounds(154, 27, 200, 22);
        cargarClientesEnComboBox();
        getContentPane().add(comboBoxClientes);
        
        textID_USUARIO = new JTextField();
        textID_USUARIO.setBounds(154, 30, 200, 16);
        getContentPane().add(textID_USUARIO);

        JLabel lblDireccion = new JLabel("Dirección Hipoteca:");
        lblDireccion.setBounds(21, 70, 123, 16);
        getContentPane().add(lblDireccion);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(154, 67, 200, 22);
        getContentPane().add(textFieldDireccion);
        textFieldDireccion.setColumns(10);

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setBounds(29, 110, 61, 16);
        getContentPane().add(lblValor);

        textFieldValor = new JTextField();
        textFieldValor.setBounds(154, 107, 200, 22);
        getContentPane().add(textFieldValor);
        textFieldValor.setColumns(10);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(29, 160, 97, 25);
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCreditoHipotecario();
            }
        });
        getContentPane().add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(136, 160, 97, 25);
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCreditoHipotecario();
            }
        });
        getContentPane().add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(243, 160, 97, 25);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCreditoHipotecario();
            }
        });
        getContentPane().add(btnEliminar);

        btnMostrar = new JButton("Mostrar");
        btnMostrar.setBounds(350, 160, 97, 25);
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCreditosHipotecarios();
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

 // ... (código previo)

    private void guardarCreditoHipotecario() {
        try {
            String idUsuario = comboBoxClientes.getSelectedItem().toString();
            String direccion = textFieldDireccion.getText();
            double valor = Double.parseDouble(textFieldValor.getText());

            if (Conexion.existeCliente(idUsuario) && esCuentaHipotecaria(idUsuario)) {
                String consulta = "INSERT INTO credito_hipotecario (id_usuario, direccion, valor) VALUES (?, ?, ?)";
                try (Connection conexion = Conexion.obtenerConexion();
                     PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, idUsuario);
                    pstmt.setString(2, direccion);
                    pstmt.setDouble(3, valor);

                    pstmt.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Crédito Hipotecario guardado exitosamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado o no es una cuenta hipotecario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido para el Valor de la Hipoteca.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el crédito hipotecario.", "Error", JOptionPane.ERROR_MESSAGE);
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

    // ... (resto del código)


    private void actualizarCreditoHipotecario() {
        try {
        	
        	String idUsuario = comboBoxClientes.getSelectedItem().toString();
             String direccion = textFieldDireccion.getText();
             double valor = Double.parseDouble(textFieldValor.getText());

             String consulta = "UPDATE credito_hipotecario SET direccion = ?, valor = ? WHERE id_usuario = ?";
             try (Connection conexion = Conexion.obtenerConexion();
                  PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                 pstmt.setString(1, direccion);
                 pstmt.setDouble(2, valor);
                 pstmt.setString(3, idUsuario);

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Crédito Hipotecario actualizado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Crédito no encontrado para el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos para ID, Dirección y Valor de la Hipoteca.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el crédito hipotecario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCreditoHipotecario() {
        try {
        	String idUsuario = comboBoxClientes.getSelectedItem().toString();

            String consulta = "DELETE FROM credito_hipotecario WHERE id_usuario = ?";
            try (Connection conexion = Conexion.obtenerConexion();
                 PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, idUsuario);

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Crédito Hipotecario eliminado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Crédito no encontrado para el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido para ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el crédito hipotecario.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Error al mostrar los créditos hipotecarios.", "Error", JOptionPane.ERROR_MESSAGE);
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