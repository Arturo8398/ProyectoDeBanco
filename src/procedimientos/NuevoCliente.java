package procedimientos;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
    	getContentPane().setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        setTitle("Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 819, 408);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#D5D2CA"));
        setResizable(false);
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        setIconImage(icono.getImage());

        JLabel titleLabel = new JLabel("Nuevo Cliente");
        titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
        titleLabel.setForeground(Color.decode("#003049"));
        Dimension titleSize = titleLabel.getPreferredSize();

        int titleX = (getWidth() - titleSize.width) / 2;
        int titleY = 50;
        titleLabel.setBounds(254, 64, 326, 47);
        getContentPane().add(titleLabel);

        RoundButton btnSalir = new RoundButton("Salir");
        btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSalir.setBounds(706, 338, 89, 23);
        btnSalir.setForeground(Color.decode("#003049"));
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
        textNOM.setBounds(183, 201, 102, 20);
        getContentPane().add(textNOM);
        textNOM.setColumns(10);

        textAP = new JTextField();
        textAP.setBounds(295, 201, 102, 20);
        getContentPane().add(textAP);
        textAP.setColumns(10);

        textCOR = new JTextField();
        textCOR.setBounds(407, 201, 102, 20);
        getContentPane().add(textCOR);
        textCOR.setColumns(10);

        textTEL = new JTextField();
        textTEL.setBounds(519, 201, 102, 20);
        getContentPane().add(textTEL);
        textTEL.setColumns(10);

        comboBoxTIP = new JComboBox<>();
        comboBoxTIP.addItem("Personal");
        comboBoxTIP.addItem("Hipotecario");
        comboBoxTIP.setBounds(631, 200, 102, 20);
        getContentPane().add(comboBoxTIP);
        
        
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

        RoundButton btnINSERTAR = new RoundButton("Insertar");
        btnINSERTAR.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnINSERTAR.setBounds(230, 250, 116, 23);
        btnINSERTAR.setForeground(Color.decode("#003049"));
        btnINSERTAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                String nombre = textNOM.getText();
                String apellidos = textAP.getText();
                String correo = textCOR.getText();
                String telefono = textTEL.getText();
                String tipoCuenta = comboBoxTIP.getSelectedItem().toString();
                
                DAOCliente c = new DAOCliente();
                Cliente nc = new Cliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta);

                try {
					c.create(nc);
				} catch (SQLException e1) {
					
				}
                DAOArchivosTXT guardarTXT = new DAOArchivosTXT("Clientes.txt");
                try {
                	//Guardar en TXT
                	System.out.println("Datos guardados en un TXT");
					guardarTXT.create(new Cliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta));
					DAOArchivosTXT array = new DAOArchivosTXT("Clientes.txt");
	                try {
	                	ArrayList<Cliente> lista = array.readAll();
	                	Generic<Cliente> ordenarLista = new Generic<>(lista);
	                	ordenarLista.recorrerLista();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error al cargar la lista");
					}
	                //Guardar en Excel
	                System.out.println("Datos guardados en un Excel");
	                DAOExcel ex = new DAOExcel();
	                ex.create(new Cliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta));
	                ex.readAll();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al insertar en el archivo txt");
				}
                limpiarCampos();
            }
        });
        getContentPane().add(btnINSERTAR);

        RoundButton btnSELECCIONAR = new RoundButton("Seleccionar");
        btnSELECCIONAR.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSELECCIONAR.setBounds(453, 250, 116, 23);
        btnSELECCIONAR.setForeground(Color.decode("#003049"));
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

                    String mensaje = "ID Usuario: " + clienteSeleccionado.getId() +
                            "\nNombre: " + clienteSeleccionado.getNombre() +
                            "\nApellidos: " + clienteSeleccionado.getApellidos() +
                            "\nCorreo: " + clienteSeleccionado.getCorreo() +
                            "\nTeléfono: " + clienteSeleccionado.getTelefono() +
                            "\nTipo de Cuenta: " + clienteSeleccionado.getTipoCuenta();

                    JOptionPane.showMessageDialog(NuevoCliente.this, mensaje);
                } else {
                    JOptionPane.showMessageDialog(NuevoCliente.this, "Cliente no encontrado");
                }
                
                DAOArchivosTXT select = new DAOArchivosTXT("Clientes.txt");
                try {
					Banco clienteSelect = select.read(textID_US.getText());
					System.out.println(clienteSelect.toString());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "El cliente no se encuentra en el archivo");
				}
            }
        });
        getContentPane().add(btnSELECCIONAR);

        RoundButton btnELIMINAR = new RoundButton("Eliminar");
        btnELIMINAR.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnELIMINAR.setForeground(Color.decode("#003049"));
        btnELIMINAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                Conexion.eliminarCliente(idUsuario);
                DAOArchivosTXT borrarCliente = new DAOArchivosTXT("Clientes.txt");
                try {
					borrarCliente.delete(textID_US.getText());
				} catch (SQLException e1) {
					System.out.println("Cliente no encontrado");
				}
                limpiarCampos();
            }
        });
        btnELIMINAR.setBounds(230, 285, 116, 23);
        getContentPane().add(btnELIMINAR);

        RoundButton btnACTUALIZAR = new RoundButton("Actualizar");
        btnACTUALIZAR.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnACTUALIZAR.setForeground(Color.decode("#003049"));
        btnACTUALIZAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUsuario = textID_US.getText();
                String nombre = textNOM.getText();
                String apellidos = textAP.getText();
                String correo = textCOR.getText();
                String telefono = textTEL.getText();
                String tipoCuenta = comboBoxTIP.getSelectedItem().toString();

                Conexion.actualizarCliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta);
                
                DAOArchivosTXT update = new DAOArchivosTXT("Clientes.txt");
                try {
					update.update(new Cliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al actualizar en el archivo txt");
				}
                
                limpiarCampos();
            }
        });
        btnACTUALIZAR.setBounds(453, 285, 116, 23);
        getContentPane().add(btnACTUALIZAR);

        JLabel lblID_US = new JLabel("ID Usuario");
        lblID_US.setForeground(Color.decode("#003049"));
        lblID_US.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblID_US.setBounds(90, 177, 102, 14);
        getContentPane().add(lblID_US);

        JLabel lblNOM = new JLabel("Nombre");
        lblNOM.setForeground(Color.decode("#003049"));
        lblNOM.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblNOM.setBounds(205, 177, 102, 14);
        getContentPane().add(lblNOM);

        JLabel lblAP = new JLabel("Apellidos");
        lblAP.setForeground(Color.decode("#003049"));
        lblAP.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblAP.setBounds(317, 177, 102, 14);
        getContentPane().add(lblAP);

        JLabel lblCOR = new JLabel("Correo");
        lblCOR.setForeground(Color.decode("#003049"));
        lblCOR.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblCOR.setBounds(436, 177, 73, 14);
        getContentPane().add(lblCOR);

        JLabel lblTEL = new JLabel("Teléfono");
        lblTEL.setForeground(Color.decode("#003049"));
        lblTEL.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblTEL.setBounds(541, 178, 94, 12);
        getContentPane().add(lblTEL);

        JLabel lblTIP = new JLabel("Tipo de Cuenta");
        lblTIP.setForeground(Color.decode("#003049"));
        lblTIP.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblTIP.setBounds(631, 176, 139, 14);
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
