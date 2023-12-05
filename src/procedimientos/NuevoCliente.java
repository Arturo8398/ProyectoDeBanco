package procedimientos;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import procedimientos.Cliente;  
import procedimientos.Conexion; 

public class NuevoCliente extends JFrame {

    private JTextField textID_US;
    private JTextField textNOM;
    private JTextField textAP;
    private JTextField textCOR;
    private JComboBox<String> comboBoxTIP;
    private JTextField textTEL;

    public NuevoCliente() {
        setTitle("Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(188, 143, 143));
        setResizable(false);

        JLabel titleLabel = new JLabel("Nuevo Cliente");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 67));
        titleLabel.setForeground(new Color(0, 0, 0));
        Dimension titleSize = titleLabel.getPreferredSize();

        int titleX = (getWidth() - titleSize.width) / 2;
        int titleY = 50;
        titleLabel.setBounds(296, 21, titleSize.width, titleSize.height);
        getContentPane().add(titleLabel);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(805, 500, 89, 23);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnSalir);

        textID_US = new JTextField();
        textID_US.setBounds(71, 201, 102, 20);
        getContentPane().add(textID_US);
        textID_US.setColumns(10);

        textNOM = new JTextField();
        textNOM.setBounds(203, 201, 102, 20);
        getContentPane().add(textNOM);
        textNOM.setColumns(10);

        textAP = new JTextField();
        textAP.setBounds(340, 201, 102, 20);
        getContentPane().add(textAP);
        textAP.setColumns(10);

        textCOR = new JTextField();
        textCOR.setBounds(480, 201, 102, 20);
        getContentPane().add(textCOR);
        textCOR.setColumns(10);

        textTEL = new JTextField();
        textTEL.setBounds(605, 201, 102, 20);
        getContentPane().add(textTEL);
        textTEL.setColumns(10);

        comboBoxTIP = new JComboBox<>();
        comboBoxTIP.addItem("Personal");
        comboBoxTIP.addItem("Hipotecario");
        comboBoxTIP.setBounds(743, 201, 102, 20);
        getContentPane().add(comboBoxTIP);

        JButton btnINSERTAR = new JButton("INSERTAR");
        btnINSERTAR.setBounds(296, 347, 116, 23);
        btnINSERTAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                String nombre = textNOM.getText();
                String apellidos = textAP.getText();
                String correo = textCOR.getText();
                String telefono = textTEL.getText();
                String tipoCuenta = comboBoxTIP.getSelectedItem().toString();

                Conexion.insertarCliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta);

                limpiarCampos();
            }
        });
        getContentPane().add(btnINSERTAR);

        JButton btnSELECCIONAR = new JButton("SELECCIONAR");
        btnSELECCIONAR.setBounds(534, 347, 116, 23);
        btnSELECCIONAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                
                // Utiliza el nuevo método seleccionarCliente de la clase Conexion
                Cliente clienteSeleccionado = Conexion.seleccionarCliente(idUsuario);

                if (clienteSeleccionado != null) {
                    // Actualiza los campos de texto con los datos del cliente seleccionado
                    textNOM.setText(clienteSeleccionado.getNombre());
                    textAP.setText(clienteSeleccionado.getApellidos());
                    textCOR.setText(clienteSeleccionado.getCorreo());
                    textTEL.setText(clienteSeleccionado.getTelefono());
                    comboBoxTIP.setSelectedItem(clienteSeleccionado.getTipoCuenta());

                    String mensaje = "ID Usuario: " + clienteSeleccionado.getIdUsuario() +
                            "\nNombre: " + clienteSeleccionado.getNombre() +
                            "\nApellidos: " + clienteSeleccionado.getApellidos() +
                            "\nCorreo: " + clienteSeleccionado.getCorreo() +
                            "\nTeléfono: " + clienteSeleccionado.getTelefono() +
                            "\nTipo de Cuenta: " + clienteSeleccionado.getTipoCuenta();

                    JOptionPane.showMessageDialog(NuevoCliente.this, mensaje);
                } else {
                    JOptionPane.showMessageDialog(NuevoCliente.this, "Cliente no encontrado");
                }
            }
        });
        getContentPane().add(btnSELECCIONAR);

        JButton btnELIMINAR = new JButton("ELIMINAR");
        btnELIMINAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                Conexion.eliminarCliente(idUsuario);

                limpiarCampos();
            }
        });
        btnELIMINAR.setBounds(296, 393, 116, 23);
        getContentPane().add(btnELIMINAR);

        JButton btnACTUALIZAR = new JButton("ACTUALIZAR");
        btnACTUALIZAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                String nombre = textNOM.getText();
                String apellidos = textAP.getText();
                String correo = textCOR.getText();
                String telefono = textTEL.getText();
                String tipoCuenta = comboBoxTIP.getSelectedItem().toString();

                Conexion.actualizarCliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta);

                limpiarCampos();
            }
        });
        btnACTUALIZAR.setBounds(534, 393, 116, 23);
        getContentPane().add(btnACTUALIZAR);

        JLabel lblID_US = new JLabel("ID_USUARIO");
        lblID_US.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblID_US.setBounds(71, 171, 102, 14);
        getContentPane().add(lblID_US);

        JLabel lblNOM = new JLabel("NOMBRE");
        lblNOM.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblNOM.setBounds(218, 171, 102, 14);
        getContentPane().add(lblNOM);

        JLabel lblAP = new JLabel("APELLIDOS");
        lblAP.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblAP.setBounds(348, 171, 102, 14);
        getContentPane().add(lblAP);

        JLabel lblCOR = new JLabel("CORREO");
        lblCOR.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblCOR.setBounds(498, 171, 73, 14);
        getContentPane().add(lblCOR);

        JLabel lblTEL = new JLabel("TELEFONO");
        lblTEL.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblTEL.setBounds(617, 173, 94, 12);
        getContentPane().add(lblTEL);

        JLabel lblTIP = new JLabel("TIPO DE CUENTA");
        lblTIP.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblTIP.setBounds(733, 173, 139, 14);
        getContentPane().add(lblTIP);
    }

    private void limpiarCampos() {
        textID_US.setText("");
        textNOM.setText("");
        textAP.setText("");
        textCOR.setText("");
        textTEL.setText("");
        comboBoxTIP.setSelectedIndex(0); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NuevoCliente frame = new NuevoCliente();
            frame.setVisible(true);
        });
    }
}
