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
import javax.swing.JOptionPane;

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
import java.awt.GridLayout;

public class pacman extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DrawingPanel drawingPanel;
	private player pacman;
	private List<player> paredes = new ArrayList<>();
	private List<player> bolitas = new ArrayList<>();
	JLabel lblNewLabel_2; // se muestra el puntaje
	Boolean collisionBolita = false;// para saber si colisiona
	private int seg = 1;
	int mil = 0;

	int anguloInicial = 30; //se guardan los angulos del pacman
	int anguloExtension = 300; 

	Timer timer;

	JLabel lblNewLabel_1;

	int lastPress = 0;

	int score = 0;

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
		setBounds(100, 100, 604, 504);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		pacman = new player(160, 50, 40, 40, Color.YELLOW);

		drawingPanel = new DrawingPanel();
		drawingPanel.setFocusable(true);
		drawingPanel.addKeyListener(this);
		contentPane.add(drawingPanel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 128));
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(1, 3, 0, 0));

		lblNewLabel_2 = new JLabel("Puntos: " + score);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel = new JLabel("Pacman");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("0:0");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.add(lblNewLabel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 128));
		contentPane.add(panel_2, BorderLayout.SOUTH);

		// Boton reiniciar
		JButton btnNewButton = new JButton("Reiniciar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciar();
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

		paredesDibujo();

		bolitasDibujo();

		timer = new Timer(100, taskPerfomer);
	}

	public void reiniciar() {
		pacman.x = 160;
		pacman.y = 50;
		lastPress = 0;

		score = 0;
		lblNewLabel_2.setText("Puntos: " + score);

		bolitasDibujo();
		timer.stop();

		seg = 0;
		mil = 0;
		lblNewLabel_1.setText(seg + ":" + mil + "");

		drawingPanel.repaint();
		drawingPanel.requestFocusInWindow();
	}

	public void bolitasDibujo() {
		for (int i = 40; i < 550; i += 55) {
			for (int y = 30; y < 370; y += 40) {
				player bolitaTemporal = new player(i, y, 5, 5, Color.WHITE);
				boolean enPared = false;

				// Verificar si la bolita colisiona con alguna pared
				for (player pared : paredes) {
					if (bolitaTemporal.colision(pared)) {
						enPared = true;
						break;
					}
				}

				// Solo añadir si no está en una pared
				if (!enPared) {
					bolitas.add(bolitaTemporal);
				}
			}
		}
	}

	public void paredesDibujo() {
		// Paredes laterales (simétricas)
		paredes.add(new player(60, 60, 20, 270, Color.pink)); // Pared izquierda
		paredes.add(new player(495, 60, 20, 270, Color.pink)); // Pared derecha (ajustada para simetría)

		// Paredes horizontales superiores (simétricas)
		paredes.add(new player(132, 100, 90, 20, Color.pink)); // Izquierda
		paredes.add(new player(132, 200, 90, 20, Color.pink)); // centro
		paredes.add(new player(342, 100, 90, 20, Color.pink)); // Derecha

		// Pared vertical central
		paredes.add(new player(277, 60, 20, 100, Color.pink)); // Centrada
		paredes.add(new player(277, 230, 20, 100, Color.pink)); // Centrada

		// Paredes horizontales
		paredes.add(new player(132, 300, 90, 20, Color.pink)); // Izquierda
		paredes.add(new player(342, 200, 90, 20, Color.pink)); // Izquierda
		paredes.add(new player(342, 300, 90, 20, Color.pink)); // Derecha

		// Bordes del área de juego
		paredes.add(new player(0, 0, 10, 160, Color.pink)); // Borde izquierdo
		paredes.add(new player(0, 230, 10, 160, Color.pink)); // Borde izquierdo 2

		paredes.add(new player(570, 0, 10, 160, Color.pink)); // Borde derecho
		paredes.add(new player(570, 230, 10, 160, Color.pink)); // Borde derecho 2

		paredes.add(new player(0, 0, 250, 10, Color.pink)); // Borde superior
		paredes.add(new player(320, 0, 250, 10, Color.pink)); // Borde superior

		paredes.add(new player(0, 385, 250, 10, Color.pink)); // Borde inferior
		paredes.add(new player(320, 385, 260, 10, Color.pink)); // Borde inferior 2
	}

	class DrawingPanel extends JPanel {
		public DrawingPanel() {
			setBackground(Color.BLACK);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			// verifica si el puntaje es par o impar para que cambie la animacion
			boolean segundoPar = (score % 2) == 0;

			// Dibujo de pacman
			if (segundoPar) {
				g2d.setColor(pacman.c);
				g2d.fillArc(pacman.x, pacman.y, pacman.w, pacman.h, anguloInicial, anguloExtension);
			} else {
				// Boca más cerrada
				g2d.setColor(pacman.c);
				g2d.fillArc(pacman.x, pacman.y, pacman.w, pacman.h, 360, 360);
			}

			for (player player : paredes) {
				g2d.setColor(player.c);
				g2d.fillRect(player.x, player.y, player.w, player.h);
			}

			for (player player : bolitas) {
				g2d.setColor(player.c);
				g2d.fillOval(player.x, player.y, player.w, player.h);
			}

		}

	}

	// temporizador
	ActionListener taskPerfomer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String[] split_string = lblNewLabel_1.getText().split(":");
			mil = Integer.parseInt(split_string[1]);
			mil += 1;

			if (mil >= 10) {
				seg++;
				mil = 1;
			}

			lblNewLabel_1.setText(seg + ":" + mil + "");
		}

	};

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		timer.start();
		lastPress = e.getKeyCode();
		update();
	}

	Boolean collision = false;

	public void update() {

		int ejex = pacman.x;
		int ejey = pacman.y;

		if (lastPress == 37 || lastPress == 65) {
			// izquierda
			ejex -= 3;

			if (ejex <= -45) {
				ejex = 550;
			}

			anguloInicial = 210;
			anguloExtension = 300;

		} else if (lastPress == 39 || lastPress == 68) {
			// Derecha
			ejex += 3;

			if (ejex >= 570) {
				ejex = -25;
			}
			
			anguloInicial = 30;
			anguloExtension = 300;
		} else if (lastPress == 40 || lastPress == 83) {
			// Abajo

			ejey += 3;

			if (ejey >= 365) {
				ejey = -25;
			}
			
			anguloInicial = 300;
			anguloExtension = 300;
		} else if (lastPress == 38 || lastPress == 87) {
			// Arriba

			ejey -= 3;

			if (ejey <= -25) {
				ejey = 365;
			}
			
			anguloInicial = 120;
			anguloExtension = 300;
		}

		// hacemos un pacman temporal para saber si va a colisionar con una pared
		player tempPacman = new player(ejex, ejey, pacman.w, pacman.h, pacman.c);
		collision = false;
		for (player pared : paredes) {
			if (tempPacman.colision(pared)) {
				collision = true;
				break;
			}
		}
		
		// Solo se mueve si la colision es falsa
		if (!collision) {
			pacman.x = ejex;
			pacman.y = ejey;
		}

		player bolitaComida = null;// se gurda la bolita que colisiona

		for (player bolita : bolitas) {
			if (tempPacman.colision(bolita)) {
				collisionBolita = true;

				bolitaComida = bolita;
				break;
			}
		}

		// revisamos la colision de bolita que bolita no este null
		if (collisionBolita && bolitaComida != null) {
			bolitas.remove(bolitaComida);// se borra la bolita guardada
			score += 1;// se aumenta el puntaje
			lblNewLabel_2.setText("Puntos: " + score); // se actualiza la pantalla
		}

		// verificamos si el array esta vacio para saber que todas las bolitas fueron borradas
		if (bolitas.isEmpty()) {
			timer.stop();
			int opcion = JOptionPane.showOptionDialog(null,
					"Felicidades ganaste\nPuntos: " + score + "   Tiempo: " + seg + " segundos", "Fin del Juego",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					new String[] { "Reiniciar", "Salir" }, "Reiniciar");
			if (opcion == JOptionPane.YES_OPTION) {
				reiniciar();
			} else {
				System.exit(0);
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
