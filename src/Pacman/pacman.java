package Pacman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class pacman extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DrawingPanel drawingPanel;

	// Variables de los ejes "x" y "y" del pacman
	private int ejeX = 255;
	private int ejeY = 150;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pacman frame = new pacman();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public pacman() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		drawingPanel = new DrawingPanel();
		drawingPanel.setFocusable(true);
		drawingPanel.addKeyListener(this);
		contentPane.add(drawingPanel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 128));
		contentPane.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Pacman");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panel_1.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 128));
		contentPane.add(panel_2, BorderLayout.SOUTH);

		
		//Boton reiniciar
		JButton btnNewButton = new JButton("Reiniciar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejeX = 255;
				ejeY = 150;
				drawingPanel.repaint();
				drawingPanel.requestFocusInWindow();
			}
		});
		panel_2.add(btnNewButton);

	}

	class DrawingPanel extends JPanel {
		public DrawingPanel() {
			setBackground(Color.BLACK);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			// Dibujo de pacman
			g2d.setColor(Color.YELLOW);
			g2d.fillOval(ejeX, ejeY, 50, 50);

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("numero: " + e.getKeyCode());

		if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
			// Izquierda
			ejeX -= 5;
			if (ejeX == -45) {
				ejeX = 550;
			}
		} else if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
			// Derecha
			ejeX += 5;
			if (ejeX == 570) {
				ejeX = -25;
			}
		} else if (e.getKeyCode() == 40 || e.getKeyCode() == 83) {
			// Abajo
			ejeY += 5;
			if (ejeY == 365) {
				ejeY = -25;
			}
		} else if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
			// Arriba
			ejeY -= 5;
			if (ejeY == -25) {
				ejeY = 365;
			}
		}

		drawingPanel.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
