package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField_number_processes;
	private JTextField textField_number_cores;
	private JTextField textField_quantum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreen frame = new HomeScreen();
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
	public HomeScreen() {
		setTitle("Simulador de escalonador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 312, 333);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_quantidade_cores_processes = new JPanel();
		panel_quantidade_cores_processes.setBounds(0, 0, 312, 137);
		panel_quantidade_cores_processes.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Escolha da quantidade de cores e processos", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel.add(panel_quantidade_cores_processes);
		panel_quantidade_cores_processes.setLayout(null);

		textField_number_processes = new JTextField();
		textField_number_processes.setColumns(10);
		textField_number_processes.setBounds(220, 59, 42, 20);
		panel_quantidade_cores_processes.add(textField_number_processes);

		JLabel label_number_processes = new JLabel("Numero de processos:");
		label_number_processes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_number_processes.setBounds(45, 55, 165, 25);
		panel_quantidade_cores_processes.add(label_number_processes);

		JLabel label_numero_cores = new JLabel("Numero de cores:");
		label_numero_cores.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_numero_cores.setBounds(45, 24, 133, 25);
		panel_quantidade_cores_processes.add(label_numero_cores);

		textField_number_cores = new JTextField();
		textField_number_cores.setColumns(10);
		textField_number_cores.setBounds(220, 28, 42, 20);
		panel_quantidade_cores_processes.add(textField_number_cores);

		JLabel lblQuantum = new JLabel("Quantum:");
		lblQuantum.setEnabled(false);
		lblQuantum.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblQuantum.setBounds(45, 91, 165, 25);
		panel_quantidade_cores_processes.add(lblQuantum);

		textField_quantum = new JTextField();
		textField_quantum.setEnabled(false);
		textField_quantum.setColumns(10);
		textField_quantum.setBounds(220, 95, 42, 20);
		panel_quantidade_cores_processes.add(textField_quantum);

		JPanel panel_tipo_escalonador = new JPanel();
		panel_tipo_escalonador.setBounds(0, 148, 312, 140);
		panel_tipo_escalonador.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Escolha do tipo de esalonador a ser gerado", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel.add(panel_tipo_escalonador);
		panel_tipo_escalonador.setLayout(null);

		JRadioButton rdbtnShortestJobFirst = new JRadioButton("Shortest Job First (SJF)");
		rdbtnShortestJobFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblQuantum.setEnabled(false);
				textField_quantum.setText("");
				textField_quantum.setEnabled(false);

			}
		});
		rdbtnShortestJobFirst.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnShortestJobFirst.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnShortestJobFirst.setBounds(6, 24, 300, 23);
		panel_tipo_escalonador.add(rdbtnShortestJobFirst);

		JRadioButton rdbtnRoundRobin = new JRadioButton("Round Robin");
		rdbtnRoundRobin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblQuantum.setEnabled(true);
				textField_quantum.setEnabled(true);

			}
		});
		rdbtnRoundRobin.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnRoundRobin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnRoundRobin.setBounds(6, 99, 300, 23);
		panel_tipo_escalonador.add(rdbtnRoundRobin);

		JRadioButton rdbtnLeastTimeToGo = new JRadioButton("Least Time to Go (LTG)");
		rdbtnLeastTimeToGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblQuantum.setEnabled(false);
				textField_quantum.setText("");
				textField_quantum.setEnabled(false);

			}
		});
		rdbtnLeastTimeToGo.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnLeastTimeToGo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnLeastTimeToGo.setBounds(6, 62, 300, 23);
		panel_tipo_escalonador.add(rdbtnLeastTimeToGo);

		ButtonGroup schedulers = new ButtonGroup();
		schedulers.add(rdbtnLeastTimeToGo);
		schedulers.add(rdbtnRoundRobin);
		schedulers.add(rdbtnShortestJobFirst);

		JButton btnNewButton = new JButton("Gerar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String cores = textField_number_cores.getText();
				String processos = textField_number_processes.getText();
				String quantum = textField_quantum.getText();

				// escolher a
				if (rdbtnShortestJobFirst.isSelected()) {
					MainScreen mainScreen = new MainScreen(Integer.parseInt(cores), Integer.parseInt(processos));
					mainScreen.setVisible(true);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dispose();
				}

				if (rdbtnLeastTimeToGo.isSelected()) {

					LtgScreen ltg = new LtgScreen(Integer.parseInt(cores), Integer.parseInt(processos));
					ltg.setVisible(true);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dispose();
				}

				if (rdbtnRoundRobin.isSelected()) {
					RbScreen rb = new RbScreen(Integer.parseInt(cores), Integer.parseInt(processos),
							Integer.parseInt(quantum));
					rb.setVisible(true);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dispose();
				}

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(103, 299, 99, 23);
		panel.add(btnNewButton);

	}
}
