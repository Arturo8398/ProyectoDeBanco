package procedimientos;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ConsultaFondos extends JFrame {
    private JLabel titleLabel;

    public ConsultaFondos() {
        setTitle("Consulta de Fondos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(188, 143, 143));
        setResizable(false);
        
                titleLabel = new JLabel("Consulta de Fondos");
                titleLabel.setBounds(193, 80, 616, 78);
                getContentPane().add(titleLabel);
                titleLabel.setForeground(Color.BLACK);
                titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 67));
                
                        JButton btnConsultarFondosHipotecarios = 	new JButton("Consultar Fondos Hipotecarios");
                        btnConsultarFondosHipotecarios.setBounds(380, 304, 183, 23);
                        getContentPane().add(btnConsultarFondosHipotecarios);
                        
                                JButton btnConsultarFondosPersonales = new JButton("Consultar Fondos Personales");
                                btnConsultarFondosPersonales.setBounds(380, 231, 183, 23);
                                getContentPane().add(btnConsultarFondosPersonales);
                                
                                        JButton btnSalir = new JButton("Salir");
                                        btnSalir.setBounds(844, 491, 89, 23);
                                        getContentPane().add(btnSalir);
                                        btnSalir.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                dispose();
                                            }
                                        });
                                btnConsultarFondosPersonales.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        abrirFormularioConsultaFondosPersonales();
                                    }
                                });
                        btnConsultarFondosHipotecarios.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                abrirFormularioConsultaFondosHipotecarios();
                            }
                        });
    }

    private void abrirFormularioConsultaFondosPersonales() {
        SwingUtilities.invokeLater(() -> {
            ConsultaFondosPersonales frame = new ConsultaFondosPersonales();
            frame.setVisible(true);
        });
    }

    private void abrirFormularioConsultaFondosHipotecarios() {
        SwingUtilities.invokeLater(() -> {
            ConsultaFondosHipotecarios frame = new ConsultaFondosHipotecarios();
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaFondos frame = new ConsultaFondos();
            frame.setVisible(true);
        });
    }
}
