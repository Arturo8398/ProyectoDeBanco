package procedimientos;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Credito extends JFrame {
    private JLabel titleLabel;

    public Credito() {
        setTitle("Gestión de Créditos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 472, 352);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#D5D2CA"));

        setResizable(false);

        RoundButton btnSalir = new RoundButton("Salir");
        btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnSalir.setBounds(359, 282, 89, 23);
        btnSalir.setForeground(Color.decode("#003049"));
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnSalir);

        RoundButton btnCreditoPersonal = new RoundButton("Crédito Personal");
        btnCreditoPersonal.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnCreditoPersonal.setBounds(157, 151, 150, 23);
        btnCreditoPersonal.setForeground(Color.decode("#003049"));
        btnCreditoPersonal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioCreditoPersonal();
            }
        });
        getContentPane().add(btnCreditoPersonal);

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

        RoundButton btnCreditoHipotecario = new RoundButton("Crédito Hipotecario");
        btnCreditoHipotecario.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
        btnCreditoHipotecario.setBounds(157, 118, 150, 23);
        btnCreditoHipotecario.setForeground(Color.decode("#003049"));
        btnCreditoHipotecario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioCreditoHipotecario();
            }
        });
        getContentPane().add(btnCreditoHipotecario);

        titleLabel = new JLabel("Crédito");
        titleLabel.setForeground(Color.decode("#003049"));
        titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
        titleLabel.setBounds(157, 50, 186, 58);
        getContentPane().add(titleLabel);
    }

    private void abrirFormularioCreditoPersonal() {
        SwingUtilities.invokeLater(() -> {
            FormularioCreditoPersonal frame = new FormularioCreditoPersonal();
            frame.setVisible(true);

        });
    }

    private void abrirFormularioCreditoHipotecario() {
        SwingUtilities.invokeLater(() -> {
            FormularioCreditoHipotecario frame = new FormularioCreditoHipotecario();
            frame.setVisible(true);

        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Credito frame = new Credito();
            frame.setVisible(true);
        });
    }
}
