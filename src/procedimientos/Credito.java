package procedimientos;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Credito extends JFrame {
    private JLabel titleLabel;

    public Credito() {
        setTitle("Gestión de Créditos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(188, 143, 143));

        setResizable(false);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(805, 500, 89, 23);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnSalir);

        JButton btnCreditoPersonal = new JButton("Crédito Personal");
        btnCreditoPersonal.setBounds(399, 339, 150, 23);
        btnCreditoPersonal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioCreditoPersonal();
            }
        });
        getContentPane().add(btnCreditoPersonal);

        JButton btnCreditoHipotecario = new JButton("Crédito Hipotecario");
        btnCreditoHipotecario.setBounds(399, 233, 150, 23);
        btnCreditoHipotecario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFormularioCreditoHipotecario();
            }
        });
        getContentPane().add(btnCreditoHipotecario);

        titleLabel = new JLabel("Crédito");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 67));
        titleLabel.setBounds(363, 75, 246, 78);
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
