package Pacman;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class pacman extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DrawingPanel drawingPanel;
	private player pacman;
	private List<player> paredes = new ArrayList<>();
	
	Timer timer;

	JLabel lblNewLabel_1;
	
	int lastPress = 0;

	// Variables de los ejes "x" y "y" del pacman
	private int ejeX;
	private int ejeY;

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

		pacman = new player(255, 150, 50, 50, Color.YELLOW);

		drawingPanel = new DrawingPanel();
		drawingPanel.setFocusable(true);
		drawingPanel.addKeyListener(this);
		contentPane.add(drawingPanel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 128));
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Pacman");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblNewLabel, BorderLayout.WEST);
		
		lblNewLabel_1 = new JLabel("0:0");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(lblNewLabel_1, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 128));
		contentPane.add(panel_2, BorderLayout.SOUTH);

		// Boton reiniciar
		JButton btnNewButton = new JButton("Reiniciar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ejeX = 255;
//				ejeY = 150;
				drawingPanel.repaint();
				drawingPanel.requestFocusInWindow();
			}
		});
		panel_2.add(btnNewButton);

		int delay = 10; // milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// ...Perform a task...
				update();
			}
		};
		new Timer(delay, taskPerformer).start();

		paredes.add(new player(30, 30, 70, 300, Color.pink));
		paredes.add(new player(450, 30, 70, 300, Color.pink));
		
	    timer = new Timer(100, taskPerfomer);


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
			g2d.setColor(pacman.c);
			g2d.fillOval(pacman.x, pacman.y, pacman.w, pacman.h);

			for (player player : paredes) {
				g2d.setColor(player.c);
				g2d.fillRect(player.x, player.y, player.w, player.h);
			}

		}
		
	}
	
	
	private int seg = 1;
	
	// temporizador
	ActionListener taskPerfomer = new ActionListener() {
		@Override
	public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String[] split_string = lblNewLabel_1.getText().split(":");
			int mil = Integer.parseInt(split_string[1]);
			mil += 1;

			if (mil >= 10) {
				seg++;
				mil = 1;
			}
			
			lblNewLabel_1.setText(seg+":"+mil+"");
		}
		
	};

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	Boolean collision = false;

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("numero: " + e.getKeyCode());

		timer.start();
		lastPress = e.getKeyCode();
		update();
	}

	public void update() {
				
	    collision = false;
		for (player pared : paredes) {
			if (pacman.colision(pared)) {
				collision = true;
				break;
			}
		}	

		if (lastPress == 37 || lastPress == 65) {
			// Izquierda
			if (!collision) {
				pacman.x -= 1;
			} else {
				pacman.x += 1;
//				System.out.println("cdc");
			}

			if (pacman.x == -45) {
				pacman.x = 550;
			}

		} else if (lastPress == 39 || lastPress == 68) {
			// Derecha

			if (!collision) {
				pacman.x += 1;
			} else {
				pacman.x -= 1;
			}

			if (pacman.x == 570) {
				pacman.x = -25;
			}
		} else if (lastPress == 40 || lastPress == 83) {
			// Abajo

			if (!collision) {
				pacman.y += 1;
			} else {
				pacman.y -= 1;
			}

			if (pacman.y == 365) {
				pacman.y = -25;
			}
		} else if (lastPress == 38 || lastPress == 87) {
			// Arriba

			if (!collision) {
				pacman.y -= 1;
			} else {
				pacman.y += 1;
			}

			if (pacman.y == -25) {
				pacman.y = 365;
			}
		}

		drawingPanel.repaint();
	}

	class player {

		private int x, y, w, h;
		private Color c;

		public player(int x, int y, int w, int h, Color c) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.c = c;
		}

		public Boolean colision(player target) {
			if (this.x < target.x + target.w &&

					this.x + this.w > target.x &&

					this.y < target.y + target.h &&

					this.y + this.h > target.y) {

				return true;
			}

			return false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
