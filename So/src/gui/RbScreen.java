package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.Processe;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RbScreen extends JFrame {

	private int core;
	private int processeNum;
	private int quantum;
	private int aux;
	private JPanel contentPane;
	private JTable table_core;
	DefaultTableModel coreModel = new DefaultTableModel();
	DefaultTableModel processeModel = new DefaultTableModel();
	DefaultTableModel execultedModel = new DefaultTableModel();
	private ArrayList<Processe> processes = new ArrayList<Processe>();
	private Processe[] processesInCore;
	private ArrayList<Processe> processesExecuted = new ArrayList<Processe>();
	private JTable table_processes;
	private JTable table_execulted;
	JLabel label_quantum;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// RbScreen frame = new RbScreen(2, 3, 5);
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public RbScreen(int core, int processe, int quantum) {
		setTitle("Simulador de escalonador - Round Robin");
		setCore(core);
		setProcesses(processe);
		setQuantum(quantum);
		setAux(quantum);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// criar cabeçalho dos cores
		for (int i = 0; i < core; i++) {
			coreModel.addColumn("core" + (i + 1));
		}

		// Criar cabeça~ho dos processos
		for (int i = 0; i < processe * 5; i++) {
			processeModel.addColumn("processos" + (i + 1));
		}

		for (int i = 0; i < processe * 5; i++) {
			execultedModel.addColumn("processos" + (i + 1));
		}

		JPanel panel_core = new JPanel();
		panel_core.setBounds(0, 0, 718, 112);
		panel_core.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Cores",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		contentPane.add(panel_core);
		panel_core.setLayout(null);

		JScrollPane scrollPane_core = new JScrollPane(table_core, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_core.setBounds(100, 10, 601, 89);
		panel_core.add(scrollPane_core);

		table_core = new JTable();
		table_core.setModel(coreModel);
		table_core.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_core.setViewportView(table_core);

		label_quantum = new JLabel("0");
		label_quantum.setBounds(78, 11, 17, 14);
		panel_core.add(label_quantum);

		JLabel lblQuantum = new JLabel("Quantum:");
		lblQuantum.setBounds(10, 11, 58, 14);
		panel_core.add(lblQuantum);

		JLabel lblProcessos = new JLabel("Processos:");
		lblProcessos.setBounds(10, 30, 75, 14);
		panel_core.add(lblProcessos);

		JLabel lblTempo = new JLabel("Tempo:");
		lblTempo.setBounds(10, 43, 75, 14);
		panel_core.add(lblTempo);

		JPanel panel_processes = new JPanel();
		panel_processes.setBounds(0, 111, 718, 112);
		panel_processes.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Processos",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		contentPane.add(panel_processes);
		panel_processes.setLayout(null);

		JScrollPane scrollPane_processes = new JScrollPane(table_processes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_processes.setBounds(89, 11, 619, 90);
		panel_processes.add(scrollPane_processes);

		table_processes = new JTable();
		table_processes.setModel(processeModel);
		table_processes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_processes.setViewportView(table_processes);

		JLabel label = new JLabel("Tempo:");
		label.setBounds(10, 43, 75, 14);
		panel_processes.add(label);

		JLabel label_1 = new JLabel("Processos:");
		label_1.setBounds(10, 30, 75, 14);
		panel_processes.add(label_1);

		JPanel panel_execulted = new JPanel();
		panel_execulted.setBounds(0, 228, 718, 112);
		panel_execulted.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Execultados",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		contentPane.add(panel_execulted);
		panel_execulted.setLayout(null);

		JScrollPane scrollPane_execulted = new JScrollPane(table_execulted, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_execulted.setBounds(87, 11, 611, 90);
		panel_execulted.add(scrollPane_execulted);

		table_execulted = new JTable();
		table_execulted.setModel(execultedModel);
		table_execulted.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_execulted.setViewportView(table_execulted);

		JLabel label_2 = new JLabel("Processos:");
		label_2.setBounds(10, 31, 75, 14);
		panel_execulted.add(label_2);

		JLabel label_3 = new JLabel("Tempo:");
		label_3.setBounds(10, 44, 75, 14);
		panel_execulted.add(label_3);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new SwingWorker() {
					@Override
					protected Object doInBackground() throws Exception {
						
						RB();

						return null;
					}

				}.execute();

			}
		});
		btnIniciar.setBounds(27, 351, 89, 23);
		contentPane.add(btnIniciar);

		JButton btnAdicionarNovoProcesso = new JButton("Adicionar novo processo");
		btnAdicionarNovoProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				setProcesses(processeNum + 1);
				Random r = new Random();
				Processe p = new Processe("P" + processeNum, r.nextInt(16) + 4);
				processes.add(p);

				escreverProcessos();

			}
		});
		btnAdicionarNovoProcesso.setBounds(368, 351, 184, 23);
		contentPane.add(btnAdicionarNovoProcesso);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean parada = false;
				HomeScreen homeScreen = new HomeScreen();
				homeScreen.setVisible(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
			
			}
		});
		btnVoltar.setBounds(591, 351, 89, 23);
		contentPane.add(btnVoltar);

		createRandomProcesses();
		escreverProcessos();

	}

	public void RB() {
		processesInCore = new Processe[core];
		boolean parada = false;

	

		while (parada == false) {

			for (int i = 0; i < processesInCore.length; i++) {
				if (processesInCore[i] == null && processes.size() != 0) {
					processesInCore[i] = processes.get(0);
					processes.remove(0);
				}
			}

			escreverProcessos();
			escreverProcessosCore();
			int contador = contador();
			label_quantum.setText(String.valueOf(contador));

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < processesInCore.length; i++) {
				if (processesInCore[i] != null) {
					processesInCore[i].setTime(processesInCore[i].getTime() - 1);
				}
			}

			for (int i = 0; i < processesInCore.length; i++) {
				if (processesInCore[i] == null)
					continue;
				if (processesInCore[i].getTime() == 0) {
					processesExecuted.add(processesInCore[i]);
					processesInCore[i] = null;
				}
			}

			escreverProcessosCore();
			escreverProcessosExecutados();
			
			if (contador == 0) {
				for (int i = 0; i < processesInCore.length; i++) {
					if (processesInCore[i] != null) {
						processes.add(processesInCore[i]);
						processesInCore[i] = null;
					}
				}

			}

			escreverProcessosCore();
			escreverProcessos();

			if (processes.size() == 0 && checarVazio()) {
				
				parada = true;
			}

		}

	}

	public void setCore(int cores) {
		this.core = cores;
	}

	public void setProcesses(int processe) {
		this.processeNum = processe;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public void setAux(int aux) {
		this.aux = aux;
	}

	public void createRandomProcesses() {
		Random random = new Random();
		ArrayList<Processe> list = new ArrayList<Processe>();
		for (int i = 0; i < processeNum; i++) {
			list.add(new Processe("P" + String.valueOf((i + 1)), random.nextInt(16) + 4));
		}
		processes = list;

	}

	private int contador() {
		int aux1 = aux;
		if (aux != 0) {
			setAux(aux1 - 1);
			return aux1;
		}

		setAux(quantum);
		return aux1;

	}

	public void escreverProcessos() {
		limparTabelaProcessos();
		processeModel.setNumRows(1);
		processeModel.setNumRows(2);
		int i = 0;
		for (Processe p : this.processes) {
			processeModel.setValueAt(p.getName(), 0, i);
			processeModel.setValueAt(p.getTime(), 1, i);
			i++;
		}

	}

	public void escreverProcessosCore() {
		limparTabelaCore();
		coreModel.setNumRows(1);
		coreModel.setNumRows(2);

		for (int i = 0; i < processesInCore.length; i++) {
			if (processesInCore[i] != null) {
				coreModel.setValueAt(processesInCore[i].getName(), 0, i);
				coreModel.setValueAt(processesInCore[i].getTime(), 1, i);
			} else {
				coreModel.setValueAt("", 0, i);
				coreModel.setValueAt("", 1, i);
			}
		}
	}

	public void escreverProcessosExecutados() {
		limparTabelaExecultados();
		execultedModel.setNumRows(1);
		execultedModel.setNumRows(2);
		int i = 0;
		for (Processe p : this.processesExecuted) {
			execultedModel.setValueAt(p.getName(), 0, i);
			execultedModel.setValueAt(p.getTime(), 1, i);
			i++;
		}
	}

	public boolean checarVazio() {

		for (int i = 0; i < processesInCore.length; i++) {
			if (processesInCore[i] != null) {
				return false;
			}
		}
		return true;
	}

	public void limparTabelaProcessos() {
		for (int i = processeModel.getRowCount() - 1; i >= 0; i--)
			processeModel.removeRow(i);
	}

	public void limparTabelaCore() {
		for (int i = coreModel.getRowCount() - 1; i >= 0; i--)
			coreModel.removeRow(i);
	}

	public void limparTabelaExecultados() {
		for (int i = execultedModel.getRowCount() - 1; i >= 0; i--)
			execultedModel.removeRow(i);
	}

}
