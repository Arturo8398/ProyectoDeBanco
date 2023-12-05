package procedimientos;

import java.awt.Color;
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

public class ConsultaFondos extends JFrame {
	private JLabel titleLabel;

	public ConsultaFondos() {
		setTitle("Consulta de Fondos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 642, 325);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.decode("#D5D2CA"));
		setResizable(false);

		titleLabel = new JLabel("Consulta de Fondos");
		titleLabel.setBounds(109, 38, 427, 64);
		getContentPane().add(titleLabel);
		titleLabel.setForeground(Color.decode("#003049"));
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));

		RoundButton btnConsultarFondosHipotecarios = new RoundButton("Consultar Fondos Hipotecarios");
		btnConsultarFondosHipotecarios.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		btnConsultarFondosHipotecarios.setBounds(208, 145, 216, 23);
		btnConsultarFondosHipotecarios.setForeground(Color.decode("#003049"));
		getContentPane().add(btnConsultarFondosHipotecarios);
		
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

		RoundButton btnConsultarFondosPersonales = new RoundButton("Consultar Fondos Personales");
		btnConsultarFondosPersonales.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		btnConsultarFondosPersonales.setBounds(208, 112, 216, 23);
		btnConsultarFondosPersonales.setForeground(Color.decode("#003049"));
		getContentPane().add(btnConsultarFondosPersonales);

		RoundButton btnSalir = new RoundButton("Salir");
		btnSalir.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		btnSalir.setBounds(529, 255, 89, 23);
		getContentPane().add(btnSalir);
		btnSalir.setForeground(Color.decode("#003049"));
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
