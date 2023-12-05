package procedimientos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Menu extends JFrame {
    public Menu() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 533, 410);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#D5D2CA"));

        JLabel titleLabel = new JLabel("Menú");
        titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 37));
        titleLabel.setForeground(Color.decode("#003049"));
        Dimension titleSize = titleLabel.getPreferredSize();

        int titleX = (getWidth() - titleSize.width) / 2;
        int titleY = 50;
        titleLabel.setBounds(207, 59, 125, 43);
        getContentPane().add(titleLabel);

        RoundButton btnNuevoCliente = new RoundButton("Nuevo Cliente");
        btnNuevoCliente.setForeground(Color.decode("#003049"));
        btnNuevoCliente.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnNuevoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirNuevoCliente();
            }
        });
        btnNuevoCliente.setBounds(178, 112, 159, 23);
        getContentPane().add(btnNuevoCliente);

        RoundButton btnCredito = new RoundButton("Crédito");
        btnCredito.setForeground(Color.decode("#003049"));
        btnCredito.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnCredito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCredito();
            }
        });
        btnCredito.setBounds(178, 145, 159, 23);
        getContentPane().add(btnCredito);

        RoundButton btnConsultaFondos = new RoundButton("Consulta de Fondos");
        btnConsultaFondos.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnConsultaFondos.setForeground(Color.decode("#003049"));
        btnConsultaFondos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirConsultaFondos();
            }
        });
        btnConsultaFondos.setBounds(178, 178, 159, 23);
        getContentPane().add(btnConsultaFondos);

        RoundButton btnRetiros = new RoundButton("Retiros");
        btnRetiros.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnRetiros.setForeground(Color.decode("#003049"));
        btnRetiros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRetiros();
            }
        });
        btnRetiros.setBounds(178, 211, 159, 23);
        getContentPane().add(btnRetiros);

        RoundButton btnDeposito = new RoundButton("Depósito");
        btnDeposito.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnDeposito.setForeground(Color.decode("#003049"));
        btnDeposito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirDeposito();
            }
        });
        btnDeposito.setBounds(178, 244, 159, 23);
        getContentPane().add(btnDeposito);

        RoundButton btnSalir = new RoundButton("Salir");
        btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSalir.setBounds(420, 340, 89, 23);
        btnSalir.setForeground(Color.decode("#003049"));
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnSalir);
        
        JLabel lblNewLabel = new JLabel("Banco AJEDE");
        lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        lblNewLabel.setForeground(Color.decode("#003049"));
        lblNewLabel.setBounds(49, 23, 89, 13);
        getContentPane().add(lblNewLabel);
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
        setIconImage(icono.getImage());
        
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
    }

    private void abrirNuevoCliente() {
        SwingUtilities.invokeLater(() -> {
            NuevoCliente nuevoCliente = new NuevoCliente();
            nuevoCliente.setVisible(true);
        });
    }

    private void abrirCredito() {
        SwingUtilities.invokeLater(() -> {
            Credito credito = new Credito();
            credito.setVisible(true);
        });
    }

    private void abrirConsultaFondos() {
        SwingUtilities.invokeLater(() -> {
            ConsultaFondos consultaFondos = new ConsultaFondos();
            consultaFondos.setVisible(true);
        });
    }

    private void abrirRetiros() {
        SwingUtilities.invokeLater(() -> {
            Retiro retiro = new Retiro();
            retiro.setVisible(true);
        });
    }

    private void abrirDeposito() {
        SwingUtilities.invokeLater(() -> {
            Deposito deposito = new Deposito();
            deposito.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }
}
