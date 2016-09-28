/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.sun.glass.events.KeyEvent;

import ctrlLayer.*;
import dbLayer.DuplicateException;
import modelLayer.Bus;
import modelLayer.BusStation;
import modelLayer.Day;
import modelLayer.Person;
import modelLayer.Route;
import modelLayer.Time;
import modelLayer.Travel;

/**
 *
 * @author viva
 */
public class employeeView extends javax.swing.JFrame {
	private LogIn in;
	private  JPanel panel;
	PersonCtrl persCtrl = new PersonCtrl();
	private DefaultTableModel modelCustomer;
	private Person pers;
	private JPanel panel2JCustomer;
	private int tab1;
	private int tab2;
	private int tab3;
	private Thread thread;
	private BusStationCtrl busStationCtrl;
	private RouteCtrl routeCtrl;
	private TravelCtrl travelCtrl;
	private Map<Integer,Route> routesMap;
	private Map<Day, List<Time>> shedules;
	private Map<Day, HashMap<Time,Integer>> busShcedules;
	private DayCtrl dayCtrl;
	private TimeCtrl timeCtrl;
	private int countAddedRoutes;
	private int countAddedShedules;
	private BusesCtrl busesCtrl;
	private DefaultTableModel modelBus;
	 private int oldId;
	 private javax.swing.JButton jButtonSellect;
		private javax.swing.JButton jButton2ResetTable;
		private javax.swing.JLabel jLabel26;
		private javax.swing.JPanel jPanel2Customer;
		private javax.swing.JScrollPane jScrollPane3;
		private javax.swing.JSplitPane jSplitPane1;
		private javax.swing.JLabel message2Customer;
		private javax.swing.JButton searchButton;
		private javax.swing.JTextField searchFieldCustomer;
		private javax.swing.JTable tableCustomer;
	 
    /**
     * Creates new form employeeView
     */
    public employeeView() {
    	tab1 = 0;tab2=0;tab3=0;

    	Instant start = Instant.now();
        initComponents();

        modelBus = (DefaultTableModel) tb3.getModel();
    	busShcedules = new HashMap<Day, HashMap<Time,Integer>>();
        
        pricePerDayField.setText(0+"");
        in = LogIn.getInstance();
		jPanel2Customer.add(in.getRegisterPanel2(), BorderLayout.CENTER);
		modelCustomer = (DefaultTableModel) tableCustomer.getModel();

		
        
        jPanel6 = new JPanel();
        jTabbedPane1.addTab("Customers", null, jPanel6, null);
        GroupLayout gl_jPanel6 = new GroupLayout(jPanel6);
        gl_jPanel6.setHorizontalGroup(
        	gl_jPanel6.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_jPanel6.createSequentialGroup()
        			.addGap(18)
        			.addComponent(message2Customer, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        			.addGap(723))
        		.addGroup(gl_jPanel6.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel2Customer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
        			.addGroup(gl_jPanel6.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, gl_jPanel6.createSequentialGroup()
        					.addComponent(jButton2ResetTable)
        					.addGap(341))
        				.addGroup(gl_jPanel6.createSequentialGroup()
        					.addGap(257)
        					.addComponent(jLabel26)
        					.addGap(33)
        					.addComponent(searchFieldCustomer, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_jPanel6.createParallelGroup(Alignment.LEADING)
        				.addComponent(jButtonSellect)
        				.addComponent(searchButton))
        			.addGap(225))
        		.addGroup(gl_jPanel6.createSequentialGroup()
        			.addContainerGap(621, Short.MAX_VALUE)
        			.addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 518, GroupLayout.PREFERRED_SIZE)
        			.addGap(174))
        );
        gl_jPanel6.setVerticalGroup(
        	gl_jPanel6.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel6.createSequentialGroup()
        			.addGroup(gl_jPanel6.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_jPanel6.createSequentialGroup()
        					.addGap(60)
        					.addComponent(jPanel2Customer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_jPanel6.createSequentialGroup()
        					.addGap(35)
        					.addGroup(gl_jPanel6.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel26)
        						.addComponent(searchFieldCustomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(searchButton))
        					.addGap(37)
        					.addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addGroup(gl_jPanel6.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jButton2ResetTable)
        						.addComponent(jButtonSellect))))
        			.addGap(363)
        			.addComponent(message2Customer, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        jPanel6.setLayout(gl_jPanel6);
	addSavebutton();
	addDeleteButton();

		Runnable runnable = () -> {
			tab1Function();
		};
			Thread thread = new Thread(runnable);
			thread.start();	
			Runnable runnable2 = () -> {
			tab2Function();
			};
			Thread thread2 = new Thread(runnable2);
			thread2.start();
			Runnable runnable3 = () -> {
			tab3Function();
			};
			Thread thread3 = new Thread(runnable3);
			thread3.start();


		Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }
    private void addSavebutton() {
		panel2JCustomer = new JPanel(new BorderLayout());
		JButton buttonSave = new JButton("Save changes");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pers != null) {
					in.saveChanges(pers);
				} else {
					JOptionPane.showMessageDialog(null, "There is no selected person");
				}
			}
		});
		buttonSave.setSize(40, 40);
		panel2JCustomer.add(buttonSave, BorderLayout.WEST);
		jPanel2Customer.add(panel2JCustomer, BorderLayout.SOUTH);

	}

	private void addDeleteButton() {
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pers != null) {
					try {
						persCtrl.deletePerson(pers);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "Error while deleting person");
					}
				} else {
					JOptionPane.showMessageDialog(null, "There is no selected person");
				}

			}
		});
		buttonDelete.setSize(40, 40);
		panel2JCustomer.add(buttonDelete, BorderLayout.EAST);
		jPanel2Customer.add(panel2JCustomer, BorderLayout.SOUTH);

	}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	jSplitPane1 = new javax.swing.JSplitPane();
		jPanel6 = new javax.swing.JPanel();
		jLabel26 = new javax.swing.JLabel();
		searchFieldCustomer = new javax.swing.JTextField();
		jScrollPane3 = new javax.swing.JScrollPane();
		tableCustomer = new javax.swing.JTable();
		message2Customer = new javax.swing.JLabel();
		searchButton = new javax.swing.JButton();
		jPanel2Customer = new javax.swing.JPanel();
		jButtonSellect = new javax.swing.JButton();
		jButton2ResetTable = new javax.swing.JButton();

    	busStationCtrl = new BusStationCtrl();
    	routeCtrl = new RouteCtrl();
    	travelCtrl = new TravelCtrl();
    	dayCtrl = new DayCtrl();
    	timeCtrl = new TimeCtrl();
    	routesMap = new TreeMap<Integer,Route>(); 
		shedules = new HashMap<Day, List<Time>>();
		busesCtrl = new BusesCtrl();

		jTabbedPane1 = new JTabbedPane();
		

		// ======== this ========
		setDefaultCloseOperation(3);
		Container contentPane = getContentPane();

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout
				.setHorizontalGroup(contentPaneLayout.createParallelGroup().addGroup(GroupLayout.Alignment.TRAILING,
						contentPaneLayout.createSequentialGroup().addComponent(jTabbedPane1).addContainerGap()));
		contentPaneLayout
				.setVerticalGroup(contentPaneLayout.createParallelGroup().addGroup(GroupLayout.Alignment.TRAILING,
						contentPaneLayout.createSequentialGroup().addContainerGap(31, Short.MAX_VALUE)
								.addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        
        JPanel panel_1 = new JPanel();
        jTabbedPane1.addTab("Routes and BusStations", null, panel_1, null);
        panel_1.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(43, 58, 669, 108);
        panel_1.add(scrollPane);
        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		DefaultTableModel model = (DefaultTableModel)table.getModel();
        		textField.setText(model.getValueAt(table.getSelectedRow(),1).toString());
        		textField_1.setText(model.getValueAt(table.getSelectedRow(),2).toString());
        		textField_2.setText(model.getValueAt(table.getSelectedRow(),3).toString());
        		textField_3.setText(model.getValueAt(table.getSelectedRow(),4).toString());
        		textField_4.setText(model.getValueAt(table.getSelectedRow(),5).toString());
        	}
        });
        
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Route ID", "From Bus Station", "To Bus Station", "Price", "Distance (km)", "Duration(min)"
        	}
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(73);
        table.getColumnModel().getColumn(1).setPreferredWidth(107);
        table.getColumnModel().getColumn(2).setPreferredWidth(91);
        table.getColumnModel().getColumn(5).setPreferredWidth(82);
        scrollPane.setViewportView(table);

		Runnable runnable = () -> {

				ArrayList<Route> routes;
				try {
					routes = (ArrayList<Route>) routeCtrl.getAllRoutes();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					routes.stream().forEach(route1 -> {
						model.addRow(new Object[]{
								route1.getId(), route1.getFrom().getCity(), route1.getTo().getCity(), route1.getPrice(),
								route1.getKm(), route1.getDuration()
						});
					});


				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

		};

		 thread = new Thread(runnable);
		thread.start();
		Runnable runnable2 = () -> {


			JLabel lblRoutes = new JLabel();
			lblRoutes.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblRoutes.setText("Routes");
			lblRoutes.setBounds(466, 11, 56, 21);
			panel_1.add(lblRoutes);

			JLabel lblBusStation = new JLabel();
			lblBusStation.setText("Bus Station");
			lblBusStation.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblBusStation.setBounds(466, 238, 101, 21);
			panel_1.add(lblBusStation);

			JLabel lblNewLabel = new JLabel("From Bus Station");
			lblNewLabel.setBounds(804, 18, 101, 14);
			panel_1.add(lblNewLabel);

			textField = new JTextField();
			textField.setBounds(939, 11, 86, 29);
			panel_1.add(textField);
			textField.setColumns(10);

			JLabel lblToBusStation = new JLabel("To Bus Station");
			lblToBusStation.setBounds(804, 61, 101, 14);
			panel_1.add(lblToBusStation);

			JLabel lblPrice = new JLabel("Price");
			lblPrice.setBounds(804, 101, 101, 14);
			panel_1.add(lblPrice);

			JLabel lblDistance = new JLabel("Distance(km)");
			lblDistance.setBounds(804, 139, 101, 14);
			panel_1.add(lblDistance);

			JLabel lblDurationmin = new JLabel("Duration(min)");
			lblDurationmin.setBounds(804, 180, 101, 14);
			panel_1.add(lblDurationmin);

			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(939, 54, 86, 29);
			panel_1.add(textField_1);

			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(939, 94, 86, 29);
			panel_1.add(textField_2);

			textField_3 = new JTextField();
			textField_3.setColumns(10);
			textField_3.setBounds(939, 134, 86, 24);
			panel_1.add(textField_3);

			textField_4 = new JTextField();
			textField_4.setColumns(10);
			textField_4.setBounds(939, 169, 86, 25);
			panel_1.add(textField_4);

			label_1 = new JLabel();
			label_1.setForeground(Color.RED);
			label_1.setBounds(195, 177, 327, 36);
			panel_1.add(label_1);


			JButton btnAddRoute = new JButton("Add Route");
			btnAddRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					label_1.setText("");
					if (!textField.getText().trim().equals("") && !textField_1.getText().trim().equals("") && !textField_2.getText().trim().equals("")
							&& !textField_3.getText().trim().equals("") && !textField_4.getText().trim().equals("")) {
						int routeID = 0;
						try {
							int fromBusStationID = busStationCtrl.getStationByCity(textField.getText()).getId();
							int toBusStationID = busStationCtrl.getStationByCity(textField_1.getText()).getId();
							try {
								routeCtrl.insertRoute(fromBusStationID, toBusStationID, Double.parseDouble(textField_2.getText()),
										Double.parseDouble(textField_3.getText()), Integer.parseInt(textField_4.getText()));
								DefaultTableModel model = (DefaultTableModel) table.getModel();
								routeID = routeCtrl.getRouteID(fromBusStationID, toBusStationID);
								model.addRow(new Object[]{routeID, textField.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText(),
										textField_4.getText()});
							} catch (NumberFormatException e1) {
								label_1.setText("There is incorrect input data!");
								e1.printStackTrace();
							} catch (DuplicateException e1) {
								label_1.setText("This route already exist");
								e1.printStackTrace();
							}
							routeID = routeCtrl.getRouteID(fromBusStationID, toBusStationID);
						} catch (SQLException ex) {
							label_1.setText("The route is not added successfully");
							ex.printStackTrace();
						}


					} else {
						label_1.setText("There is a blank field!");

					}
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField_3.setText("");
					textField_4.setText("");
				}
			});
			btnAddRoute.setBounds(692, 205, 114, 23);
			panel_1.add(btnAddRoute);

			JButton btnDeleteRoute = new JButton("Delete Route");
			btnDeleteRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					label_1.setText("");
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					if (table.getSelectedRow() == -1) {
						if (table.getRowCount() == 0) {
							label_1.setText("Table is empty");
						} else {
							label_1.setText("You must select a Route");
						}
					} else {
						try {
							int i = routeCtrl.deleteRouteByID((int) model.getValueAt(table.getSelectedRow(), 0));
							if (i > 0) {
								label_1.setText("Successful deleted route!");
							}
						} catch (SQLException ex) {
							label_1.setText("Not successful deleted route!");
							ex.printStackTrace();
						}
						model.removeRow(table.getSelectedRow());
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						textField_4.setText("");
					}
				}

			});
			btnDeleteRoute.setBounds(939, 205, 109, 23);
			panel_1.add(btnDeleteRoute);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(43, 273, 416, 108);
			panel_1.add(scrollPane_1);

			table_1 = new JTable();
			table_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();
					textField_6.setText(model.getValueAt(table_1.getSelectedRow(), 1).toString());
				}
			});
			table_1.setModel(new DefaultTableModel(
					new Object[][]{
					},
					new String[]{
							"Bus Station ID", "City"
					}
			));
			table_1.getColumnModel().getColumn(0).setPreferredWidth(93);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(89);
			scrollPane_1.setViewportView(table_1);
			ArrayList<BusStation> busStations;
			try {
				busStations = (ArrayList<BusStation>) busStationCtrl.getAllStations();
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();

				for (BusStation station : busStations) {
					model.addRow(new Object[]{
							station.getId(), station.getCity()
					});
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			JLabel lblCity = new JLabel("City");
			lblCity.setBounds(541, 311, 49, 14);
			panel_1.add(lblCity);

			textField_6 = new JTextField();
			textField_6.setColumns(10);
			textField_6.setBounds(582, 304, 86, 29);
			panel_1.add(textField_6);
			JLabel label = new JLabel();
			label.setBounds(386, 375, 239, 36);
			label.setForeground(Color.RED);
			panel_1.add(label);
			JButton btnAddBusStation = new JButton("Add Bus Station");
			btnAddBusStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					label.setText("");
					if (!textField_6.getText().trim().equals("")) {
						BusStation busStation = null;
						try {
							busStationCtrl.createBusStation(textField_6.getText());
							busStation = busStationCtrl.getStationByCity(textField_6.getText());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						DefaultTableModel model3 = (DefaultTableModel) table_1.getModel();
						model3.addRow(new Object[]{busStation.getId(), busStation.getCity()});
					} else {
						label.setText("The city should not"
								+ " be left blank.");
					}
					textField_6.setText("");
				}
			});
			btnAddBusStation.setBounds(700, 273, 140, 23);
			panel_1.add(btnAddBusStation);

			JButton btnDeleteBusStation = new JButton("Delete Bus Station");
			btnDeleteBusStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					label.setText("");
					DefaultTableModel model3 = (DefaultTableModel) table_1.getModel();
					if (table_1.getSelectedRow() == -1) {
						if (table_1.getRowCount() == 0) {
							label.setText("Table is empty");
						} else {
							label.setText("You must select a Bus Station");
						}
					} else {
						try {
							busStationCtrl.deleteStation(textField_6.getText());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						model3.removeRow(table_1.getSelectedRow());
					}
				}
			});
			btnDeleteBusStation.setBounds(700, 307, 140, 23);
			panel_1.add(btnDeleteBusStation);

			btnUpdateBusStation = new JButton("Update Bus Station");
			btnUpdateBusStation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					label.setText("");
					DefaultTableModel model3 = (DefaultTableModel) table_1.getModel();
					if (table_1.getSelectedRow() == -1) {
						if (table_1.getRowCount() == 0) {
							label.setText("Table is empty");
						} else {
							label.setText("You must select a Bus Station");
						}
					} else {

						try {
							busStationCtrl.updateStation(textField_6.getText(), (int) model3.getValueAt(table_1.getSelectedRow(), 0));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						model3.setValueAt(textField_6.getText(), table_1.getSelectedRow(), 1);
					}
				}
			});
			btnUpdateBusStation.setBounds(700, 341, 140, 23);
			panel_1.add(btnUpdateBusStation);

			btnUpdateRoute = new JButton("Update Route");
			btnUpdateRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					label_1.setText("");
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					if (table.getSelectedRow() == -1) {
						if (table.getRowCount() == 0) {
							label.setText("Table is empty");
						} else {
							label.setText("You must select a Route");
						}
					} else {
						try {
							int fromBusStationID = busStationCtrl.getStationByCity(textField.getText()).getId();
							int toBusStationID = busStationCtrl.getStationByCity(textField_1.getText()).getId();

							int routeID = (int) model.getValueAt(table.getSelectedRow(), 0);
							try {
								routeCtrl.updateRoute(routeID, fromBusStationID, toBusStationID, Double.parseDouble(textField_2.getText()),
										Double.parseDouble(textField_3.getText()), Integer.parseInt(textField_4.getText()));
								model.setValueAt(textField.getText(), table.getSelectedRow(), 1);
								model.setValueAt(textField_1.getText(), table.getSelectedRow(), 2);
								model.setValueAt(textField_2.getText(), table.getSelectedRow(), 3);
								model.setValueAt(textField_3.getText(), table.getSelectedRow(), 4);
								model.setValueAt(textField_4.getText(), table.getSelectedRow(), 5);
							} catch (NumberFormatException e1) {
								label_1.setText("There is incorrect input data!");

								e1.printStackTrace();
							} catch (DuplicateException e1) {
								label_1.setText("The route already exist!");

								e1.printStackTrace();
							}
						} catch (SQLException e1) {
							label_1.setText("The route is not added successfully");
							e1.printStackTrace();
						}

					}


				}
			});
			btnUpdateRoute.setBounds(820, 205, 109, 23);
			panel_1.add(btnUpdateRoute);

			label_1 = new JLabel();
			label_1.setForeground(Color.RED);
			label_1.setBounds(195, 177, 327, 36);
			panel_1.add(label_1);
		};
		Thread thread2 = new Thread(runnable2);
		thread2.start();
        
         panel = new JPanel();
        
        	
        jTabbedPane1.addTab("Travels", null, panel, null);
        panel.setLayout(null);

        
        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(41, 65, 512, 92);
        panel.add(scrollPane_2);
        
        table_2 = new JTable();
        
        table_2.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Travel ID", "Number Bus Stations", "First bus station", "Last bus station", "km"
        	}
        ));
        table_2.getColumnModel().getColumn(1).setPreferredWidth(125);
        table_2.getColumnModel().getColumn(2).setPreferredWidth(134);
        table_2.getColumnModel().getColumn(3).setPreferredWidth(115);
        
      
        
        JScrollPane scrollPane_4 = new JScrollPane();
      
        scrollPane_4.setBounds(56, 182, 459, 98);
        panel.add(scrollPane_4);
        
        table_4 = new JTable();
        table_4.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		DefaultTableModel model2 = (DefaultTableModel)table_4.getModel();
        		
        		DefaultTableModel model3 = (DefaultTableModel)table_3.getModel();
        		model3.setRowCount(0);
        		try {
        			int travelID = (int) model2.getValueAt(table_4.getSelectedRow(), 0);
        			TreeMap<Integer,Route> routes = (TreeMap<Integer, Route>) travelCtrl.getRoutesOfTravelByTravelID(travelID);
        			
        			HashMap<Day, List<Time>> shedules = (HashMap<Day, List<Time>>) travelCtrl.getDaysSheduleForTravelByID((int)(travelID));
					
						String day =  (String) model2.getValueAt(table_4.getSelectedRow(), 1);
						
							int duration = 0;
					        int busStationID = 0;
					        String departureTime = (String)model2.getValueAt(table_4.getSelectedRow(), 2);
					        String arrivalTime = "";
							 for(Map.Entry<Integer,Route> route: routes.entrySet()) {
				                    if (route.getKey() == 1) {
				                        duration += route.getValue().getDuration();
				                        busStationID = route.getValue().getTo().getId();
				                    }
				                    if (route.getKey() >= 2) {
				                        Route route1 = routeCtrl.getRouteByID(routeCtrl.getRouteID(busStationID,
				                                route.getValue().getTo().getId()));
				                        if (route1.getDuration() != 0) {
				                            duration += route1.getDuration();
				                            busStationID = route.getValue().getTo().getId();
				                        }
				                    }
				                    arrivalTime = calculateTime(departureTime, duration);
				                    model3.addRow(new Object[]{
											route.getValue().getFrom().getCity(),route.getValue().getTo().getCity(),route.getKey(),
											day,departureTime, arrivalTime,route.getValue().getKm(),
											route.getValue().getPrice(),travelID
										});
				                    departureTime = arrivalTime;
				                    duration = 0;
				                }
							
							
							
						
					
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
        	}
        	
        });
        scrollPane_4.setViewportView(table_4);
        table_4.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"TravelID", "Day", "Departure time", "Arrival time", "Bus ID"
        	}
        ));
        
        panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Create/ Delete Travel", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_2.setBounds(652, 11, 379, 291);
        panel.add(panel_2);
        panel_2.setLayout(null);
        
        textField_5 = new JTextField();
        textField_5.setBounds(111, 23, 86, 23);
        panel_2.add(textField_5);
        textField_5.setColumns(10);
        
        JLabel lblFrom = new JLabel("First bus station");
        lblFrom.setBounds(16, 27, 93, 14);
        panel_2.add(lblFrom);
        
        JLabel lblTo = new JLabel("To bus station(s)");
        lblTo.setBounds(16, 61, 93, 14);
        panel_2.add(lblTo);
        
        textField_7 = new JTextField();
        textField_7.setBounds(111, 57, 86, 23);
        panel_2.add(textField_7);
        textField_7.setColumns(10);
        
        textField_8 = new JTextField();
        textField_8.setBounds(111, 140, 86, 23);
        panel_2.add(textField_8);
        textField_8.setColumns(10);
        
        JLabel lblDay = new JLabel("Day");
        lblDay.setBounds(16, 144, 46, 14);
        panel_2.add(lblDay);
        
        JLabel lblDepartureTime = new JLabel("Departure time");
        lblDepartureTime.setBounds(16, 178, 86, 14);
        panel_2.add(lblDepartureTime);
        
        textField_9 = new JTextField();
        textField_9.setBounds(111, 174, 31, 23);
        panel_2.add(textField_9);
        textField_9.setColumns(10);
        
        JButton btnNewButton = new JButton("Add station ");
        btnNewButton.setBounds(142, 91, 114, 23);
        panel_2.add(btnNewButton);
        
        JLabel label_2 = new JLabel(":");
        label_2.setBounds(151, 178, 18, 14);
        panel_2.add(label_2);
        
        textField_10 = new JTextField();
        textField_10.setBounds(166, 174, 31, 23);
        panel_2.add(textField_10);
        textField_10.setColumns(10);
        
        JLabel lblHh = new JLabel("HH");
        lblHh.setBounds(111, 199, 18, 14);
        panel_2.add(lblHh);
        
        JLabel lblMm = new JLabel("MM");
        lblMm.setBounds(162, 199, 18, 14);
        panel_2.add(lblMm);
        
        JButton btnAddShedule = new JButton("Add shedule ");
        btnAddShedule.setBounds(215, 174, 114, 23);
        panel_2.add(btnAddShedule);
        
		JButton btnCreateTravel = new JButton("Create travel");
		btnCreateTravel.setBounds(55, 245, 114, 23);
		panel_2.add(btnCreateTravel);
		
		textField_11 = new JTextField();
		textField_11.setBounds(303, 57, 37, 23);
		panel_2.add(textField_11);
		textField_11.setColumns(10);
		
		lblStopNumber = new JLabel("Stop number");
		lblStopNumber.setBounds(208, 61, 85, 14);
		panel_2.add(lblStopNumber);
		
		JButton btnDeleteTravel = new JButton("Delete travel");
		btnDeleteTravel.setBounds(215, 245, 114, 23);
		panel_2.add(btnDeleteTravel);
		
		labelStations = new JLabel("");
		labelStations.setBounds(10, 108, 365, 21);
		panel_2.add(labelStations);
		
		label_3 = new JLabel("");
		label_3.setBounds(6, 213, 349, 21);
		panel_2.add(label_3);
		
		label_4 = new JLabel("");
		label_4.setBounds(6, 259, 334, 21);
		panel_2.add(label_4);
		
		txtBusID = new JTextField();
		txtBusID.setColumns(10);
		txtBusID.setBounds(272, 140, 46, 23);
		panel_2.add(txtBusID);
		
		JLabel lblBusId = new JLabel("Bus ID:");
		lblBusId.setBounds(219, 144, 53, 14);
		panel_2.add(lblBusId);
		
		panel_3 = new JPanel();
		jTabbedPane1.addTab("Buses", null, panel_3, null);
		
		panel_4 = new JPanel();
		
		label_5 = new JLabel();
		label_5.setText("BUS Registration");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            busesCtrl.updateBus(oldId,busesCtrl.createBusWithoutAddidngToDatabase(Integer.parseInt(numberField.getText()), modelField.getText(), (int) seatSpinner.getValue(),
			                   parseDateField() ,Boolean.getBoolean((String)seatSpinner.getValue()),Integer.parseInt(pricePerDayField.getText())));
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(null, "Server error");
			        }catch(NumberFormatException nfe){
			            JOptionPane.showMessageDialog(null, "Price per day should be a number");
			        } catch (ParseException ex) {
			            JOptionPane.showMessageDialog(null,"Wrong format for date");
			        }
			}
		});
		button.setText("Save changes");
		
		label_6 = new JLabel();
		label_6.setText("<html> Note : \n<BR>The date have to be writen in the pattern of MM-YY</BR>\n<BR>Example : 10 - 18</BR></html>");
		
		button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            if (busesCtrl.deleteBus(Integer.parseInt(numberField.getText())) == 1) {
			                JOptionPane.showMessageDialog(null, "Bus removed succesfully");
			            }
			        } catch (SQLException ex) {
			        	ex.printStackTrace();
			            JOptionPane.showMessageDialog(null, "Could not remove bus");
			        }
			}
		});
		button_1.setText("Delete bus");
		
		button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
			            if(busesCtrl.createBus(Integer.parseInt(numberField.getText()), modelField.getText(), (int) seatSpinner.getValue(), parseDateField(),
			                    Boolean.valueOf((String) comboBox.getSelectedItem()),Integer.parseInt(pricePerDayField.getText())) ==1){
			                    JOptionPane.showMessageDialog(null, "Bus sucesfuly added");
			            }
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(null, "The bus could not be added");
			        }  catch(NumberFormatException nfe){
			                    JOptionPane.showMessageDialog(null,"Price per day should be written with digits");
			        } catch (ParseException ex) {
			            JOptionPane.showMessageDialog(null, "The date format is wrong");
			        }
			}
		});
		button_2.setText("Add bus");
		
		label_7 = new JLabel();
		label_7.setText("Bus Id");
		
		label_8 = new JLabel();
		label_8.setText("Number of Seats");
		
		label_9 = new JLabel();
		label_9.setText("Model");
		
		label_10 = new JLabel();
		label_10.setText("Year");
		
		label_11 = new JLabel();
		label_11.setText("Available for rent");
		
		label_12 = new JLabel();
		label_12.setText("Price per day");
		
		numberField = new JTextField();
		
		seatSpinner = new JSpinner();
		
		comboBox = new JComboBox<String>();
		
		modelField = new JTextField();
		
		yearField = new JTextField();
		seatSpinner.setModel(new SpinnerNumberModel(20, 10, 52, 1));

        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "True", "False" }));
		
		pricePerDayField = new JTextField();
		pricePerDayField.setText("0");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_5)
						.addComponent(label_6)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
								.addComponent(button_1)
								.addGap(27)
								.addComponent(button)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button_2))
							.addGroup(gl_panel_4.createSequentialGroup()
								.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_panel_4.createSequentialGroup()
										.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
											.addComponent(label_7)
											.addComponent(label_8)
											.addComponent(label_9)
											.addComponent(label_10)
											.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
										.addGap(51))
									.addGroup(gl_panel_4.createSequentialGroup()
										.addComponent(label_12)
										.addGap(102)))
								.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(numberField, Alignment.LEADING)
									.addComponent(comboBox, 0, 132, Short.MAX_VALUE)
									.addComponent(modelField)
									.addComponent(yearField)
									.addComponent(pricePerDayField)
									.addComponent(seatSpinner, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(229, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(4)
					.addComponent(label_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_6)
					.addGap(22)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(numberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_8)
						.addComponent(seatSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(modelField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_9))
					.addGap(18)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_10)
						.addComponent(yearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_12)
						.addComponent(pricePerDayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_1)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(button_2)
							.addComponent(button)))
					.addGap(43))
		);
		panel_4.setLayout(gl_panel_4);
		
		label_13 = new JLabel();
		
		panel_5 = new JPanel();
		
		label_14 = new JLabel();
		label_14.setText("Enter bus Id ");
		
		searchField = new JTextField();
		
		button_3 = new JButton();
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String search = searchField.getText();
		         modelBus.setRowCount(0);
		         try{
		             insertBusIntoTable(busesCtrl.getBus(Integer.parseInt(search)), 0);
		         }catch(NumberFormatException ne){
		            JOptionPane.showMessageDialog(null,"Bus id is supposed to be a digit");
		         }
		          catch (SQLException ex) {
		             JOptionPane.showMessageDialog(null,"Could  not find bus");
		        }
			}
		});
		button_3.setText("Search");
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 284, Short.MAX_VALUE)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(0, 49, Short.MAX_VALUE)
					.addComponent(label_14)
					.addGap(18)
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(button_3))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGap(0, 52, Short.MAX_VALUE)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap(29, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(button_3)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(4)
							.addComponent(label_14))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(1)
							.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		panel_5.setLayout(gl_panel_5);
		
		button_4 = new JButton();
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillTable();
			}
		});
		button_4.setText("Reset table");
		
		button_5 = new JButton();
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
			            fillFields(busesCtrl.getBus((int) modelBus.getValueAt(tb3.getSelectedRow(), 0)));
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(null, "Could not finish operation");
			        }catch (NullPointerException ne){
			            JOptionPane.showMessageDialog(null,"No bus selected");
			        }
			}
		});
		button_5.setText("Select");
		
		scrollPane_5 = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_3.createSequentialGroup()
								.addGap(282)
								.addComponent(label_13, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(74, Short.MAX_VALUE))
							.addGroup(gl_panel_3.createSequentialGroup()
								.addGap(129)
								.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_panel_3.createSequentialGroup()
								.addGap(116)
								.addComponent(button_4)
								.addPreferredGap(ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
								.addComponent(button_5)
								.addGap(110)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(32)
							.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 527, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(26)
							.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_3.createSequentialGroup()
									.addGap(121)
									.addComponent(label_13))
								.addGroup(gl_panel_3.createSequentialGroup()
									.addGap(26)
									.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
										.addComponent(button_4)
										.addComponent(button_5)))))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		tb3 = new JTable();
		
		 tb3.setModel(new DefaultTableModel(
		 	new Object[][] {
		 		{null, null, null, null, null, null},
		 		{null, null, null, null, null, null},
		 		{null, null, null, null, null, null},
		 	},
		 	new String[] {
		 		"Id", "Seats", "Model", "Year", "Price per day", "Rentable"
		 	}
		 ));
		 jLabel26.setText("Enter name or id");

			searchFieldCustomer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					searchFieldActionPerformed(evt);
				}
			});
			searchFieldCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent evt) {
					searchFieldKeyPressed(evt);
				}
			});

			tableCustomer.setAutoCreateRowSorter(true);
			tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
					new Object[][] { { null, null, null, null, null, null }, { null, null, null, null, null, null },
							{ null, null, null, null, null, null } },
					new String[] { "Id", "First name", "Last name", "E-mail", "Telephone", "Card Id" }) {
				Class[] types = new Class[] { java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
						java.lang.String.class, java.lang.String.class, java.lang.String.class };
				boolean[] canEdit = new boolean[] { false, false, false, true, false, false };

				public Class getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});
			tableCustomer.getTableHeader().setReorderingAllowed(false);
			tableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					tableMouseClicked(evt);
				}
			});
			jScrollPane3.setViewportView(tableCustomer);

			message2Customer.setForeground(new java.awt.Color(255, 0, 51));

			searchButton.setText("Search");
			searchButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					searchButtonActionPerformed(evt);
				}
			});

			jPanel2Customer.setLayout(new java.awt.BorderLayout());

			jButtonSellect.setText("Select");
			jButtonSellect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButton1ActionPerformed(evt);
				}
			});

			jButton2ResetTable.setText("Reset Table");
			jButton2ResetTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButton2ActionPerformed(evt);
				}
			});

		scrollPane_5.setViewportView(tb3);
		panel_3.setLayout(gl_panel_3);
		btnDeleteTravel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				label_4.setText("");
				label_3.setText("");
				labelStations.setText("");
				DefaultTableModel model = (DefaultTableModel)table_2.getModel();
				DefaultTableModel model2 = (DefaultTableModel)table_4.getModel();
				DefaultTableModel model3 = (DefaultTableModel)table_3.getModel();
				try {
					int row = table_2.getSelectedRow();
					if(row >-1){
					travelCtrl.deleteTravelByID((int) model.getValueAt(table_2.getSelectedRow(), 0));
					model.removeRow(row);
					
					label_4.setText("Travel deleted successfully!");
					}else{
						label_4.setText("You should select travel from first table!");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCreateTravel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_4.setText("");
				label_3.setText("");
				labelStations.setText("");
				if (countAddedRoutes > 0 && countAddedShedules > 0) {
					try {
						Travel travel = travelCtrl.insertTravle(routesMap, shedules,busShcedules);
						label_4.setText("Sucsessfully created route!");
						DefaultTableModel model = (DefaultTableModel) table_2.getModel();
						model.addRow(new Object[] { travel.getId(), travel.getNumStops(),
								travel.getMainRoute().getFrom().getCity(), travel.getMainRoute().getTo().getCity(),
								travel.getKm() });
					} catch (DuplicateException e1) {
						label_4.setText("Not created route due to dublication!");
						e1.printStackTrace();
					} catch (SQLException e1) {
						label_4.setText("Not sucsessfully created route!");
						e1.printStackTrace();
					} finally {
						shedules = null;
						routesMap = null;
						textField_5.setEditable(true);
					}
				}else{
					label_4.setText("Missing routes or shedules!");
				}
			}

        });
        btnAddShedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				label_4.setText("");
				label_3.setText("");
				labelStations.setText("");
			
				if (!textField_8.getText().trim().equals("") && !textField_9.getText().trim().equals("")
						&& !textField_10.getText().trim().equals("")&& !txtBusID.getText().trim().equals("")) {
					Day day;
					try {
						day = dayCtrl.getDayByDay(textField_8.getText());
						String departureTime = textField_9.getText() + ":" + textField_10.getText();
						Time time = timeCtrl.createTime(departureTime);
						if (shedules.containsKey(day)) {
							shedules.get(day).add(time);
							System.out.println(txtBusID.getText());
							System.out.println("hash " +	busShcedules.get(day) + "day" + day);
							busShcedules.get(day).put(time, Integer.parseInt(txtBusID.getText()));// to do 
							
							label_3.setText("Successfully added shedule!");
						} else if (shedules.containsKey(day) != true) {
							List<Time> times = new ArrayList<Time>();
							HashMap<Time,Integer> bs = new HashMap<>();
							
							times.add(time);
							shedules.put(day, times);
							label_3.setText("Successfully added shedule!");
							bs.put(time, Integer.parseInt(txtBusID.getText()));
							busShcedules.put(day, bs);
						} else {
							label_3.setText("You cannot add this shedule! Time or day dublication!");
						}

						countAddedShedules += 1;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{
					label_3.setText("The field(s) should not be left blank!");
				}
			}
		});
        btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				label_4.setText("");
				label_3.setText("");
				labelStations.setText("");
				if (!textField_5.getText().trim().equals("") && !textField_7.getText().trim().equals("")
						&& !textField_11.getText().trim().equals("")) {
					try {
						int fromBusStationID = busStationCtrl.getStationByCity(textField_5.getText()).getId();
						int toBusStationID = busStationCtrl.getStationByCity(textField_7.getText()).getId();
						int routeID = routeCtrl.getRouteID(fromBusStationID, toBusStationID);
						Route route = routeCtrl.getRouteByID(routeID);
						int stopNumber = Integer.parseInt(textField_11.getText());
						if (!routesMap.containsKey(stopNumber)) {
							routesMap.put(stopNumber, route);
						} else {
							labelStations
									.setText("You cannot add this Station to travel! Dublicate route or stopNumber!");
						}
						textField_5.setEditable(false);
						labelStations.setText("Successfully added route to travel!");
						countAddedRoutes += 1;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					labelStations.setText("The field(s) should not be left blank!");
				}
			}
        });
        
        table_4.getColumnModel().getColumn(2).setPreferredWidth(93);
        pack();
        setLocationRelativeTo(getOwner());

	}// </editor-fold>//GEN-END:initComponents
    protected void tab3Function() {
    	Instant start = Instant.now();
    	try {
    		System.out.println(Thread.currentThread().getName());
    		
			fillTable(persCtrl.getAllPeople());
			
			tab3++;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Could not load people from the server");
		}
	
    	 Instant end = Instant.now();
         System.out.println("tab3 time: "+Duration.between(start, end));
	}
	protected void tab2Function() {
		Instant start = Instant.now();
       
		System.out.println(Thread.currentThread().getName());
    	fillTable();
		tab2++;
		 Instant end = Instant.now();
	        System.out.println("tab2 time: "+Duration.between(start, end));
		
	}
	private void fillFields(Bus bus) {
        yearField.setText(String.valueOf(bus.getYear()));
        modelField.setText(bus.getModel());
        numberField.setText(bus.getId()+"");
        seatSpinner.setValue(bus.getNoOfSeats());
        pricePerDayField.setText(bus.getPricePerDay()+"");
        boolean item = ((bus.isAvailableForRent()));
        int i = -1;
        if(item == false ){
        	i = 1;
        }else{
        	i = 0;
        }
        comboBox.setSelectedIndex(i);
    }
    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_searchFieldKeyPressed
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			searchButton.doClick();
		}
	}// GEN-LAST:event_searchFieldKeyPressed

	private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchButtonActionPerformed
		String search = searchFieldCustomer.getText();
		modelCustomer.setRowCount(0);
		try {
			try {
				pers = persCtrl.getPersonById(Integer.parseInt(search));
				insertPersonIntoTable(0, pers);
			} catch (NumberFormatException ne) {
				fillTable(persCtrl.getPersonByFirstName(search));
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "No person with such id");
		}
	}

	private void insertPersonIntoTable(int row, Person pers) {
		if (persCtrl.getPersonCard(pers) == null) {
			modelCustomer.insertRow(row,
					new Object[] { persCtrl.getPersonId(pers), persCtrl.getPersonFname(pers),
							persCtrl.getPersonLname(pers), persCtrl.getPersonEmail(pers),
							persCtrl.getPersonTelephone(pers), "" });
		} else {
			modelCustomer.insertRow(row,
					new Object[] { persCtrl.getPersonId(pers), persCtrl.getPersonFname(pers),
							persCtrl.getPersonLname(pers), persCtrl.getPersonEmail(pers),
							persCtrl.getPersonTelephone(pers), persCtrl.getPersonCard(pers).getCardNumber() });
		}

	}// GEN-LAST:event_searchButtonActionPerformed

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tableMouseClicked

	}// GEN-LAST:event_tableMouseClicked

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		try {
			pers = persCtrl.getPersonById((int) modelCustomer.getValueAt(tableCustomer.getSelectedRow(), 0));
			in.fillRegisterPanel2(pers);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Could not finish operation");
		}
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		try {
			fillTable(persCtrl.getAllPeople());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Could not load people from server");
		}
	}// GEN-LAST:event_jButton2ActionPerformed

	private void fillTable(List<Person> list) {
		modelCustomer.setRowCount(0);
		int row = 0;
		for (Person person : list) {
			insertPersonIntoTable(row, person);
			row++;
		}
	}

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

   //-LAST:event_jButton2ActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed

    }//GEN-LAST:event_searchFieldActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
      /*  if (!email.getText().trim().equals("")) {
            DefaultTableModel model2 = (DefaultTableModel) tb2.getModel();
            model2.addRow(new Object[]{email.getText(), fname2.getText(), lname2.getText(), info2.getText()});
        } else {
            message2.setText("You need to enter the employees information.");
        }
        PersonCtrl persCtrl = new PersonCtrl();
        if (persCtrl.createCustomer(fname2.getName(), lname2.getName(), email, password, telephone, group) == 1) {
            JOptionPane.showMessageDialog(null, "Customer registered");
        } else {
            JOptionPane.showMessageDialog(null, "Error");
        }*/
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) throws ClassNotFoundException {//GEN-FIRST:event_jButton11ActionPerformed
//        PersonCtrl persCtrl = new PersonCtrl();
//        Person p = null;
//        try {
//            p = persCtrl.getPersonById(Integer.parseInt(searchField.getText()));
//        } catch (NumberFormatException ne) {
//            try {
//                p = persCtrl.getPersonByFirstName(searchField.getText());
//            } catch (SQLException ex) {
//                Logger.getLogger(Class.forName(BookTicket.class.getName())).log(Level.SEVERE, null, ex);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Class.forName(BookTicket.class.getName())).log(Level.SEVERE, null, ex);
//        }
//        if (p != null) {
//
//        } else {
//            JOptionPane.showMessageDialog(rootPane, "");
//        }
    }//GEN-LAST:event_jButton11ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(employeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(employeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(employeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(employeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new employeeView().setVisible(true);
            }
        });
    }
    private void fillTable(){
        modelBus.setRowCount(0);
        int row[] = {0};
		List<Bus> buses = busesCtrl.getAllBuses();
        buses.forEach(bus -> {
            insertBusIntoTable(bus, row[0]);
            row[0] +=1 ;
        });
  }
    private void insertBusIntoTable(Bus bus, int row){
        modelBus.insertRow(row, new Object[]{bus.getId(),bus.getNoOfSeats(),bus.getModel(),bus.getYear(),bus.getPricePerDay(),bus.isAvailableForRent()});
   }
    private String calculateTime(String departureTime, int duration){
    	 String arrivalTime = "";
         String[] hourMin = departureTime.split(":");
         int hour = Integer.parseInt(hourMin[0]);
         int mins = Integer.parseInt(hourMin[1]);
         int hoursInMins = hour * 60;
         mins = mins + hoursInMins;
         int newTime = mins + duration;
         int hours = newTime / 60; //since both are ints, you get an int
         int minutes = newTime % 60;
         arrivalTime = String.format("%02d", hours) + ":"
                 + String.format("%02d", minutes);
         return arrivalTime;
    }
    private Date parseDateField() throws ParseException {
        DateFormat format = new SimpleDateFormat("MM, d, yyyy");
        String str[] = yearField.getText().split("-");
        return format.parse(str[0] + ", 1, " + "20" + str[1]);
    }
    private void tab1Function(){
    	System.out.println(Thread.currentThread().getName());
    	Instant start = Instant.now();
       
    	table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table_2.getModel();

				int travelID = (int) model.getValueAt(table_2.getSelectedRow(), 0);

				DefaultTableModel model2 = (DefaultTableModel) table_4.getModel();
				model2.setRowCount(0);
				DefaultTableModel model3 = (DefaultTableModel) table_3.getModel();
				model3.setRowCount(0);
				try {

					TreeMap<Integer, Route> routes = (TreeMap<Integer, Route>) travelCtrl
							.getRoutesOfTravelByTravelID(travelID);

					HashMap<Day, List<Time>> shedules = (HashMap<Day, List<Time>>) travelCtrl
							.getDaysSheduleForTravelByID((int) (travelID));
					for (HashMap.Entry<Day, List<Time>> entry : shedules.entrySet()) {
						String day = entry.getKey().getDay();
						for (Time time : entry.getValue()) {
							TravelBuses travelBuses = new TravelBuses();
							int busID = travelBuses.getBusForTravel(travelID,time.getId(),entry.getKey().getDayID()).getId();
							model2.addRow(new Object[] { travelID, day, time.getDepartureTime(),
									time.getArrivalTime(),busID });
							int duration = 0;
							int toTempID = 0;
							int fromTempID = 0;
							int busStationID = 0;
							HashMap<Integer, String> fromIDs = new HashMap<>();
							Route route1 = new Route();
							String departureTime = time.getDepartureTime();
							String arrivalTime = "";
							for (Map.Entry<Integer, Route> route : routes.entrySet()) {
								int a[] = { -1 };
								String mapArrivalTime[] = { "" };
								if (route.getKey() == 1) {
									duration += route.getValue().getDuration();
									busStationID = route.getValue().getTo().getId();// for
																					// keys
																					// >2
																					// from
																					// bus
																					// station
																					// id
									toTempID = busStationID;
									fromTempID = route.getValue().getFrom().getId();
								}
								if (route.getKey() >= 2) {

									route1 = routeCtrl.getRouteByID(routeCtrl.getRouteID(busStationID,
											route.getValue().getTo().getId()));
									if (route1.getDuration() != 0 && route1 != null) {
										duration += route1.getDuration();
										fromTempID = busStationID;
										busStationID = route.getValue().getTo().getId();// for
																						// keys
																						// >2
																						// from
																						// bus
																						// station
																						// id
										toTempID = route.getValue().getTo().getId();
										a[0] = toTempID;

									}

								}
								System.out.println(fromTempID + " " + toTempID);
								arrivalTime = calculateTime(departureTime, duration);
								mapArrivalTime[0] = arrivalTime;
								int tempRouteID = routeCtrl.getRouteID(fromTempID, toTempID);
								Route tempRoute = routeCtrl.getRouteByID(tempRouteID);

								model3.addRow(new Object[] { route.getValue().getFrom().getCity(),
										route.getValue().getTo().getCity(), route.getKey(), day,
										time.getDepartureTime(), arrivalTime, route.getValue().getKm(),
										route.getValue().getPrice(), travelID });

								if (route.getKey() >= 2) {
									model3.addRow(new Object[] { tempRoute.getFrom().getCity(),
											tempRoute.getTo().getCity(), route.getKey(), day, departureTime,
											arrivalTime, tempRoute.getKm(), tempRoute.getPrice(),
											travelID });
								}

								fromIDs.forEach((k, v) -> {
									int mapFromID = k;

									try {
										int mapRouteID = routeCtrl.getRouteID(mapFromID, a[0]);
										Route mapRoute = routeCtrl.getRouteByID(mapRouteID);
										model3.addRow(new Object[] { mapRoute.getFrom().getCity(),
												mapRoute.getTo().getCity(), route.getKey(), day, v,
												mapArrivalTime[0], mapRoute.getKm(), mapRoute.getPrice(),
												travelID });
									} catch (Exception e) {
										throw new RuntimeException(e);

									}
								});
								if (route.getKey() >= 2) {
									fromIDs.put(fromTempID, departureTime);
								}
								departureTime = arrivalTime;
								duration = 0;
								toTempID = 0;
								fromTempID = 0;
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		scrollPane_2.setViewportView(table_2);
	
	
    
    JLabel lblTravels = new JLabel();
    lblTravels.setText("Travels");
    lblTravels.setFont(new Font("Tahoma", Font.BOLD, 13));
    lblTravels.setBounds(446, 11, 63, 21);
    panel.add(lblTravels);
    
    scrollPane_3 = new JScrollPane();
    scrollPane_3.setBounds(50, 302, 773, 98);
    panel.add(scrollPane_3);
    
    table_3 = new JTable();
    scrollPane_3.setViewportView(table_3);
    table_3.setModel(new DefaultTableModel(
    	new Object[][] {
    	},
    	new String[] {
    		"From bus station", "To bus station", "Stop Number", "Day", "Departure time", "Arrival time", "km", "Price", "Travel ID"
    	}
    ));
    table_3.getColumnModel().getColumn(0).setPreferredWidth(105);
    table_3.getColumnModel().getColumn(1).setPreferredWidth(87);
    table_3.getColumnModel().getColumn(4).setPreferredWidth(94);
    table_3.getColumnModel().getColumn(7).setPreferredWidth(65);
    tab1++;
    
	Runnable runnableFillTable = ()->{
	  	ArrayList<Travel> travels = null;
		try {
			Instant startTravel = Instant.now();
			travels = (ArrayList<Travel>) travelCtrl.getAllTravels();
			 Instant endtr = Instant.now();
			    System.out.println("tab1 travel time :" + Duration.between(startTravel, endtr));
			DefaultTableModel model = (DefaultTableModel) table_2.getModel();
			
	        for(Travel travel: travels){
	        	model.addRow(new Object[]{
	        			travel.getId(), travel.getNumStops(),travel.getMainRoute().getFrom().getCity(),travel.getMainRoute().getTo().getCity(),travel.getKm()
	        	});
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}};
		Thread threadFillTable = new Thread(runnableFillTable);
		threadFillTable.start();
		try {
			threadFillTable.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    Instant end = Instant.now();
    System.out.println("tab1 time :" + Duration.between(start, end));
    }

    // Variables declaration - do not modify                     
    // Generated using JFormDesigner Evaluation license - unknown
    private JTabbedPane jTabbedPane1;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTable table_1;
    private JTextField textField_6;
    private JTable table_2;
    private JScrollPane scrollPane_2;
    private JButton btnUpdateBusStation;
    private JButton btnUpdateRoute;
    private JLabel label_1;
    private JTable table_3;
    private JScrollPane scrollPane_3;
    private JTable table_4;
    private JTextField textField_5;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;
    private JTextField textField_10;
    private JTextField textField_11;
    private JLabel lblStopNumber;
    private JLabel labelStations;
    private JLabel label_3;
    private JLabel label_4;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JLabel label_5;
    private JButton button;
    private JLabel label_6;
    private JButton button_1;
    private JButton button_2;
    private JLabel label_7;
    private JLabel label_8;
    private JLabel label_9;
    private JLabel label_10;
    private JLabel label_11;
    private JLabel label_12;
    private JTextField numberField;
    private JSpinner seatSpinner;
    private JComboBox<String> comboBox;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField pricePerDayField;
    private JLabel label_13;
    private JPanel panel_5;
    private JLabel label_14;
    private JTextField searchField;
    private JButton button_3;
    private JButton button_4;
    private JButton button_5;
    private JScrollPane scrollPane_5;
    private JTable tb3;
    private JPanel jPanel6;
    private JTextField txtBusID;
}
