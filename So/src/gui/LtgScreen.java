package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.Processe;
import model.ProcesseLTG;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;

public class LtgScreen extends JFrame {

	private int core;
	private int processe;
	private DefaultTableModel coreModel = new DefaultTableModel();
	private DefaultTableModel processeModel = new DefaultTableModel();
	private DefaultTableModel deadModel = new DefaultTableModel();
	private DefaultTableModel execultedModel = new DefaultTableModel();
	private ArrayList<ProcesseLTG> processes = new ArrayList<ProcesseLTG>();
	private ProcesseLTG[] processesInCore;
	private ArrayList<ProcesseLTG> processesExecuted = new ArrayList<ProcesseLTG>();
	private ArrayList<ProcesseLTG> processesDead = new ArrayList<ProcesseLTG>();
	private JPanel contentPane;
	private JTable table_core;
	private JTable table_processos;
	private JTable table_dead;
	private JTable table_execulted;
	private boolean parada = false;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LtgScreen frame = new LtgScreen(2, 5);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public LtgScreen(int core, int processeqtd) {
		setTitle("Simulador de escalonador - Least Time to Go (LTG)");
		setCore(core);
		setProcesses(processeqtd);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 716, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		for (int i = 0; i < processe * 5; i++) {
			deadModel.addColumn("processos" + (i + 1));
		}

		JPanel panel_core = new JPanel();
		panel_core.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Cores",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel_core.setBounds(0, 0, 710, 112);
		contentPane.add(panel_core);
		panel_core.setLayout(null);

		JScrollPane scrollPane_core = new JScrollPane(table_core, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_core.setBounds(100, 12, 601, 89);
		panel_core.add(scrollPane_core);

		table_core = new JTable();
		table_core.setModel(coreModel);
		table_core.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_core.setViewportView(table_core);

		JLabel label = new JLabel("Tempo:");
		label.setBounds(10, 45, 75, 14);
		panel_core.add(label);

		JLabel label_1 = new JLabel("Processos:");
		label_1.setBounds(10, 32, 75, 14);
		panel_core.add(label_1);

		JPanel panel_processe = new JPanel();
		panel_processe.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Processos",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel_processe.setBounds(0, 123, 710, 127);
		contentPane.add(panel_processe);
		panel_processe.setLayout(null);

		JScrollPane scrollPane_processos = new JScrollPane(table_processos,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_processos.setBounds(100, 17, 601, 99);
		panel_processe.add(scrollPane_processos);

		table_processos = new JTable();
		table_processos.setModel(processeModel);
		table_processos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_processos.setViewportView(table_processos);

		JLabel label_2 = new JLabel("Tempo:");
		label_2.setBounds(10, 50, 75, 14);
		panel_processe.add(label_2);

		JLabel label_3 = new JLabel("Processos:");
		label_3.setBounds(10, 37, 75, 14);
		panel_processe.add(label_3);

		JPanel panel_dead_line = new JPanel();
		panel_dead_line.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Mortos",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel_dead_line.setBounds(0, 249, 710, 117);
		contentPane.add(panel_dead_line);
		panel_dead_line.setLayout(null);

		JScrollPane scrollPane_dead = new JScrollPane(table_dead, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_dead.setBounds(100, 11, 601, 99);
		panel_dead_line.add(scrollPane_dead);

		table_dead = new JTable();
		table_dead.setModel(deadModel);
		table_dead.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_dead.setViewportView(table_dead);

		JLabel label_4 = new JLabel("Tempo:");
		label_4.setBounds(10, 44, 75, 14);
		panel_dead_line.add(label_4);

		JLabel label_5 = new JLabel("Processos:");
		label_5.setBounds(10, 31, 75, 14);
		panel_dead_line.add(label_5);

		JPanel panel_execulted = new JPanel();
		panel_execulted.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Execultados",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel_execulted.setBounds(0, 377, 710, 117);
		contentPane.add(panel_execulted);
		panel_execulted.setLayout(null);

		JScrollPane scrollPane_execulted = new JScrollPane(table_execulted,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_execulted.setBounds(100, 11, 601, 99);
		panel_execulted.add(scrollPane_execulted);

		table_execulted = new JTable();
		table_execulted.setModel(execultedModel);
		table_execulted.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_execulted.setViewportView(table_execulted);

		JLabel label_6 = new JLabel("Tempo:");
		label_6.setBounds(10, 44, 75, 14);
		panel_execulted.add(label_6);

		JLabel label_7 = new JLabel("Processos:");
		label_7.setBounds(10, 31, 75, 14);
		panel_execulted.add(label_7);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new SwingWorker() {
					@Override
					protected Object doInBackground() throws Exception {
						
						LTG();

						return null;
					}

				}.execute();

			}
		});
		btnIniciar.setBounds(25, 505, 89, 23);
		contentPane.add(btnIniciar);

		JButton btnAdicionarNovoProcesso = new JButton("Adicionar novo processo");
		btnAdicionarNovoProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				setProcesses(processe + 1);
				Random r = new Random();
				ProcesseLTG p = new ProcesseLTG("P" + processe, r.nextInt(16) + 4, r.nextInt(16) + 4);
				processes.add(p);

				ArrayList<ProcesseLTG> ordenada = insertionSort(processes);
				processes.clear();

				limparTabelaProcessos();
				// setando a lista ordenada novamente;
				for (ProcesseLTG po : ordenada) {
					processes.add(po);
				}
				escreverProcessos();

			}
		});
		btnAdicionarNovoProcesso.setBounds(398, 505, 178, 23);
		contentPane.add(btnAdicionarNovoProcesso);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				parada = true;
				HomeScreen homeScreen = new HomeScreen();
				homeScreen.setVisible(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
				
			}
		});
		btnVoltar.setBounds(598, 505, 89, 23);
		contentPane.add(btnVoltar);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 23, 651, 86);
		setLocationRelativeTo(null);
		setResizable(false);

		createRandomProcesses();
		escreverProcessos();

	}

	public void LTG() {
		processesInCore = new ProcesseLTG[core];

		while (parada == false) {

			//checar se o core esta vazio e caso esteja mandar processos para ele
			for (int i = 0; i < processesInCore.length; i++) {
				if (processesInCore[i] == null && processes.size() != 0) {
					processesInCore[i] = processes.get(0);
					processes.remove(0);
				}
			}

			escreverProcessos();
			escreverProcessosCore();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//decrementar tempo dos processos no core
			for (int i = 0; i < processesInCore.length; i++) {
				if (processesInCore[i] != null) {
					processesInCore[i].setTime(processesInCore[i].getTime() - 1);
				}
			}

			//decrementar deadline dentro da fila de aptos  
			for (ProcesseLTG p : processes) {
				p.setDead(p.getDead() - 1);
			}

			//checar se o tempo do processo chegou a zero, se sim, tierar ele do core e colocar nos execultados 
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

			//checar se a dead line chegou a zero, se sim add na lista de processos mortos 
			for (ProcesseLTG p : processes) {
				if (p.getDead() == 0) {
					processesDead.add(p);
				}
			}

			//retirar processos mortos da fila de aptos 
			for (ProcesseLTG p : processesDead) {
				processes.remove(p);
			}

			escreverProcessos();
			escreverProcessosCore();
			escreverProcessosDead();

			//checar se pode para o while
			if (processes.size() == 0 && checarVazio()) {
				
				parada = true;
			}
		}
	}

	public void escreverProcessosExecutados() {
		limparTabelaExecultados();
		execultedModel.setNumRows(1);
		execultedModel.setNumRows(2);
		int i = 0;
		for (ProcesseLTG p : this.processesExecuted) {
			execultedModel.setValueAt(p.getName(), 0, i);
			execultedModel.setValueAt(p.getTime(), 1, i);
			i++;
		}
	}

	public void escreverProcessosDead() {
		limparTabelaDead();
		deadModel.setNumRows(1);
		deadModel.setNumRows(2);
		int i = 0;
		for (ProcesseLTG p : this.processesDead) {
			deadModel.setValueAt(p.getName(), 0, i);
			deadModel.setValueAt(p.getTime(), 1, i);
			i++;
		}
	}

	public void setCore(int cores) {
		this.core = cores;
	}

	public void setProcesses(int processe) {
		this.processe = processe;
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

	public void escreverProcessos() {
		limparTabelaProcessos();
		processeModel.setNumRows(1);
		processeModel.setNumRows(2);
		processeModel.setNumRows(3);
		int i = 0;
		for (ProcesseLTG p : this.processes) {
			processeModel.setValueAt(p.getName(), 0, i);
			processeModel.setValueAt(p.getTime(), 1, i);
			processeModel.setValueAt(p.getDead(), 2, i);
			i++;
		}

	}

	public void createRandomProcesses() {
		Random random = new Random();
		ArrayList<ProcesseLTG> list = new ArrayList<ProcesseLTG>();
		for (int i = 0; i < processe; i++) {
			list.add(new ProcesseLTG("P" + String.valueOf((i + 1)), random.nextInt(16) + 4, random.nextInt(16) + 4));
		}
		processes = insertionSort(list);

	}

	static ArrayList<ProcesseLTG> insertionSort(ArrayList<ProcesseLTG> a) {

		ProcesseLTG[] A = new ProcesseLTG[a.size()];
		int pos = 0;
		for (ProcesseLTG p : a) {
			A[pos] = p;
			pos++;
		}

		for (int j = 1; j < A.length; j++) {

			int chave = A[j].getDead();
			ProcesseLTG chave1 = A[j];
			int i = j - 1;
			while (i >= 0 && A[i].getDead() > chave) {

				A[i + 1] = A[i];
				i--;
			}
			A[i + 1] = chave1;
		}

		ArrayList<ProcesseLTG> ordenado = new ArrayList<ProcesseLTG>();

		for (int po = 0; po < A.length; po++) {
			ordenado.add(A[po]);
		}

		return ordenado;
	}

	public void limparTabelaProcessos() {
		for (int i = processeModel.getRowCount() - 1; i >= 0; i--)
			processeModel.removeRow(i);
	}

	public void limparTabelaDead() {
		for (int i = deadModel.getRowCount() - 1; i >= 0; i--)
			deadModel.removeRow(i);
	}

	public void limparTabelaCore() {
		for (int i = coreModel.getRowCount() - 1; i >= 0; i--)
			coreModel.removeRow(i);
	}

	public void limparTabelaExecultados() {
		for (int i = execultedModel.getRowCount() - 1; i >= 0; i--)
			execultedModel.removeRow(i);
	}

	public boolean checarVazio() {

		for (int i = 0; i < processesInCore.length; i++) {
			if (processesInCore[i] != null) {
				return false;
			}
		}
		return true;
	}

}
