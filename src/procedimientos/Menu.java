package procedimientos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    public Menu() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(188, 143, 143));

        JLabel titleLabel = new JLabel("Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 37));
        titleLabel.setForeground(new Color(0, 0, 0));
        Dimension titleSize = titleLabel.getPreferredSize();

        int titleX = (getWidth() - titleSize.width) / 2;
        int titleY = 50;
        titleLabel.setBounds(titleX, titleY, titleSize.width, titleSize.height);
        getContentPane().add(titleLabel);

        JButton btnNuevoCliente = new JButton("Nuevo Cliente");
        btnNuevoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirNuevoCliente();
            }
        });
        btnNuevoCliente.setBounds(426, 142, 159, 23);
        getContentPane().add(btnNuevoCliente);

        JButton btnCredito = new JButton("Crédito");
        btnCredito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCredito();
            }
        });
        btnCredito.setBounds(426, 212, 159, 23);
        getContentPane().add(btnCredito);

        JButton btnConsultaFondos = new JButton("Consulta de Fondos");
        btnConsultaFondos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirConsultaFondos();
            }
        });
        btnConsultaFondos.setBounds(426, 283, 159, 23);
        getContentPane().add(btnConsultaFondos);

        JButton btnRetiros = new JButton("Retiros");
        btnRetiros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRetiros();
            }
        });
        btnRetiros.setBounds(426, 351, 159, 23);
        getContentPane().add(btnRetiros);

        JButton btnDeposito = new JButton("Depósito");
        btnDeposito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirDeposito();
            }
        });
        btnDeposito.setBounds(426, 421, 159, 23);
        getContentPane().add(btnDeposito);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(805, 500, 89, 23);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnSalir);
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
