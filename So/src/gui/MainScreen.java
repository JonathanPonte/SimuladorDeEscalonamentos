package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.Processe;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainScreen extends JFrame {

	private int core;
	private int processe;
	private JPanel contentPane;
	DefaultTableModel coreModel = new DefaultTableModel();
	DefaultTableModel processeModel = new DefaultTableModel();
	DefaultTableModel execultedModel = new DefaultTableModel();
	private JTable table_core;
	private JTable table_processes;
	private Processe p;
	private ArrayList<Processe> processesSJF = new ArrayList<Processe>();
	private Processe[] processesInCore;
	private ArrayList<Processe> processesExecuted = new ArrayList<Processe>();
	private JTable table_execulted_processe;
	boolean parada = false;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// MainScreen frame = new MainScreen();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public MainScreen(int cores, int processes) {
		setTitle("Simulador de escalonador-Shortest Job First (SJF)");
		setCore(cores);
		setProcesses(processes);
		setTitle("Simulador de escalonador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 761, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// criar cabeçalho dos cores
		for (int i = 0; i < cores; i++) {
			coreModel.addColumn("core" + (i + 1));
		}

		// Criar cabeça~ho dos processos
		for (int i = 0; i < processes * 5; i++) {
			processeModel.addColumn("processos" + (i + 1));
		}

		for (int i = 0; i < processes * 5; i++) {
			execultedModel.addColumn("processos" + (i + 1));
		}

		JPanel panel_cores = new JPanel();
		panel_cores.setBounds(0, 0, 755, 117);
		contentPane.add(panel_cores);
		panel_cores.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Cores",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel_cores.setLayout(null);

		JScrollPane scrollPane_core = new JScrollPane(table_core, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_core.setBounds(89, 23, 656, 83);
		panel_cores.add(scrollPane_core);

		table_core = new JTable();
		table_core.setModel(coreModel);
		table_core.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_core.setViewportView(table_core);

		JLabel lblProcessos = new JLabel("Processos:");
		lblProcessos.setBounds(10, 47, 79, 14);
		panel_cores.add(lblProcessos);

		JLabel lblTempo = new JLabel("Tempo:");
		lblTempo.setBounds(26, 58, 53, 14);
		panel_cores.add(lblTempo);

		JPanel panel_processes = new JPanel();
		panel_processes.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Processos",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		panel_processes.setBounds(0, 128, 755, 110);
		contentPane.add(panel_processes);
		panel_processes.setLayout(null);

		JScrollPane scrollPane_processes = new JScrollPane(table_processes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_processes.setBounds(88, 21, 657, 78);
		panel_processes.add(scrollPane_processes);

		table_processes = new JTable();
		table_processes.setModel(processeModel);
		table_processes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_processes.setViewportView(table_processes);

		JLabel label = new JLabel("Processos:");
		label.setBounds(10, 50, 79, 14);
		panel_processes.add(label);

		JLabel label_1 = new JLabel("Tempo:");
		label_1.setBounds(26, 61, 53, 14);
		panel_processes.add(label_1);

		JButton btnNewButton = new JButton("Iniciar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new SwingWorker() {
					@Override
					protected Object doInBackground() throws Exception {
						// for (int i = 0; i < core; i++) {
						// try {
						// Thread.sleep(1000);
						// } catch (InterruptedException e) {
						// e.printStackTrace();
						// }
						// coreModel.setValueAt("P1", 0, i);
						// coreModel.fireTableCellUpdated(0, i);
						// int num = (int) coreModel.getValueAt(1, 1);
						// System.out.println(num - 1);
						// coreModel.setValueAt((num - 1), 1, 1);
						// coreModel.fireTableCellUpdated(1, i);
						// }

						sjf2();

						return null;
					}

				}.execute();

			}
		});
		btnNewButton.setBounds(35, 355, 89, 23);
		contentPane.add(btnNewButton);

		JPanel panel_execulted_processes = new JPanel();
		panel_execulted_processes.setBounds(0, 249, 755, 95);
		panel_execulted_processes.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Execultados",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		contentPane.add(panel_execulted_processes);
		panel_execulted_processes.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table_execulted_processe, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(88, 11, 657, 73);
		panel_execulted_processes.add(scrollPane);

		table_execulted_processe = new JTable();
		table_execulted_processe.setModel(execultedModel);
		table_execulted_processe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_execulted_processe);

		JLabel label_2 = new JLabel("Tempo:");
		label_2.setBounds(26, 49, 53, 14);
		panel_execulted_processes.add(label_2);

		JLabel label_3 = new JLabel("Processos:");
		label_3.setBounds(10, 38, 79, 14);
		panel_execulted_processes.add(label_3);

		JButton btnGerarNovoProcesso = new JButton("Gerar novo Processo");
		btnGerarNovoProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
					setProcesses(processe + 1);
					Random r = new Random();
					Processe p = new Processe("P" + processe, r.nextInt(16) + 4);
					processesSJF.add(p);

					ArrayList<Processe> ordenada = insertionSort(processesSJF);
					processesSJF.clear();

					limparTabelaProcessos();
					// setando a lista ordenada novamente;
					for (Processe po : ordenada) {
						processesSJF.add(po);
					}
					escreverProcessos();
				

			}
		});
		btnGerarNovoProcesso.setBounds(455, 355, 190, 23);
		contentPane.add(btnGerarNovoProcesso);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parada = true;
				HomeScreen homeScreen = new HomeScreen();
				homeScreen.setVisible(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
				
			}
		});
		btnVoltar.setBounds(656, 355, 89, 23);
		contentPane.add(btnVoltar);

		
			createRandomProcesses();
			escreverProcessos();
		

	}

	public void setCore(int cores) {
		core = cores;
	}

	public void setProcesses(int processes) {
		this.processe = processes;
	}

	

	public void createRandomProcesses() {
		Random random = new Random();
		ArrayList<Processe> list = new ArrayList<Processe>();
		for (int i = 0; i < processe; i++) {
			list.add(new Processe("P" + String.valueOf((i + 1)), random.nextInt(16) + 4));
		}
		processesSJF = insertionSort(list);

	}

	static ArrayList<Processe> insertionSort(ArrayList<Processe> a) {

		Processe[] A = new Processe[a.size()];
		int pos = 0;
		for (Processe p : a) {
			A[pos] = p;
			pos++;
		}

		for (int j = 1; j < A.length; j++) {

			int chave = A[j].getTime();
			Processe chave1 = A[j];
			int i = j - 1;
			while (i >= 0 && A[i].getTime() > chave) {

				A[i + 1] = A[i];
				i--;
			}
			A[i + 1] = chave1;
		}

		ArrayList<Processe> ordenado = new ArrayList<Processe>();

		for (int po = 0; po < A.length; po++) {
			ordenado.add(A[po]);
		}

		return ordenado;
	}

	public void escreverProcessos() {
		limparTabelaProcessos();
		processeModel.setNumRows(1);
		processeModel.setNumRows(2);
		int i = 0;
		for (Processe p : this.processesSJF) {
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

//	public void sjf() {
//		processesInCore = new Processe[core];
//		boolean listaVazia = false;
//		System.out.println("to no sjf");
//
//		while (listaVazia != true) {
//
//			// pupular o vetor de cores
//			int i = 0;
//			if (processesSJF.size() != 0) {
//				for (Processe p : processesSJF) {
//					if (i < core) {
//						if (processesInCore[i] == null) {
//							System.out.println("to no primeiro for");
//							processesInCore[i] = p;
//
//						}
//					}
//					i++;
//				}
//			}
//
//			// Tirar os processos que estão no core
//			if (processesSJF.size() != 0) {
//				for (int j = 0; j < processesInCore.length; j++) {
//					processesSJF.remove(processesInCore[j]);
//				}
//			}
//			limparTabelaProcessos();
//			escreverProcessos();
//			limparTabelaCore();
//			escreverProcessosCore();
//
//			for (int k = 0; k < processesInCore.length; k++) {
//				System.out.println("to no 3 for");
//				processesInCore[k].setTime(processesInCore[k].getTime() - 1);
//			}
//			limparTabelaCore();
//			escreverProcessosCore();
//
//			for (int l = 0; l < processesInCore.length; l++) {
//				if (processesInCore[l] != null) {
//					if (processesInCore[l].getTime() == 0) {
//						System.out.println("to no 4 for");
//						processesExecuted.add(processesInCore[l]);
//						processesInCore[l] = null;
//					}
//				}
//			}
//			limparTabelaCore();
//			escreverProcessosCore();
//			limparTabelaExecultados();
//			escreverProcessosExecutados();
//
//			if (processesExecuted.size() == processe) {
//				System.out.println("ola");
//				listaVazia = true;
//			}
//
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}
//	}

	public void sjf2() {
		processesInCore = new Processe[core];
		// ArrayList<Integer> pos = new ArrayList<Integer>();
		

		while (parada == false) {

			// System.out.println(processesSJF.size());
			// for (int i = 0; i < processesInCore.length; i++) {
			// if (processesInCore[i] == null) {
			// pos.add(i);
			// }
			//
			// }

			

			if(processesSJF.size() != 0) {
			for (int i = 0; i < processesInCore.length; i++) {
				if (processesInCore[i] == null) {
					processesInCore[i] = processesSJF.get(0);
					processesSJF.remove(0);
				}
			}
			}
			escreverProcessos();
			escreverProcessosCore();
			
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
			
			escreverProcessosCore();
			

			for (int i = 0; i < processesInCore.length; i++) {
				if(processesInCore[i] == null)
					continue;
				if (processesInCore[i].getTime() == 0) {
					processesExecuted.add(processesInCore[i]);
					processesInCore[i] = null;
				}
			}
			escreverProcessosCore();
			escreverProcessosExecutados();
			

			//
			// for(Integer p : pos) {
			// int po = 0;
			// if(processesInCore[p] == null) {
			// if(processesSJF.size() != 0) {
			// processesInCore[p] = processesSJF.get(po);
			// processesSJF.remove(po);
			// }
			// }}
			//
			//
			// }
			//
			// limparTabelaProcessos();
			// escreverProcessos();
			// limparTabelaCore();
			// escreverProcessosCore();
			//
			// for(int c = 0; c < processesInCore.length; c++) {
			// if(processesInCore[c] == null)
			// continue;
			// processesInCore[c].setTime(processesInCore[c].getTime() - 1);
			// }
			//
			//
			// limparTabelaCore();
			// escreverProcessosCore();
			//
			//
			// for(int c = 0; c < processesInCore.length; c++) {
			// if(processesInCore[c] == null)
			// continue;
			// if(processesInCore[c].getTime() == 0) {
			// processesExecuted.add(processesInCore[c]);
			// processesInCore[c] = null;
			// System.out.println(c);
			// pos.remove(c);
			// System.out.println("ola");
			// }
			// }
			//
			//
			// limparTabelaCore();
			// escreverProcessosCore();
			// limparTabelaExecultados();
			// escreverProcessosExecutados();

			

			// colocar metodo para ver se estão todos vazios

			if (processesSJF.size() == 0 && checarVazio()) {
				
				parada = true;
			}

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
