package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Interface.IProductDAO;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5911354805813991037L;
	private JPanel contentPane;
	public static JTable tblProduct;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPrice;
	private JButton btnShow;

	public MainView() {
		
		setTitle("Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Control Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 335, 539);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(32, 30, 75, 25);
		panel.add(lblNewLabel);

		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setEditable(false);
		txtId.setBounds(117, 30, 190, 25);
		panel.add(txtId);
		txtId.setColumns(10);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(117, 66, 190, 25);
		panel.add(txtName);

		JLabel lblName = new JLabel("name");
		lblName.setBounds(32, 66, 75, 25);
		panel.add(lblName);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(117, 102, 190, 25);
		panel.add(txtPrice);

		JLabel lblPrice = new JLabel("price");
		lblPrice.setBounds(32, 102, 75, 25);
		panel.add(lblPrice);

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = txtName.getText();
					Double price = Double.parseDouble(txtPrice.getText());
					IProductDAO productDAO = (IProductDAO) Naming.lookup("rmi://localhost:2/final");
					String result = productDAO.insert(name, price);
					System.out.println(result);
					
					btnShow.doClick();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnInsert.setBounds(32, 162, 89, 69);
		panel.add(btnInsert);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(txtId.getText());
					IProductDAO productDAO = (IProductDAO) Naming.lookup("rmi://localhost:2/final");
					String result = productDAO.delete(id);
					System.out.println(result);
					btnShow.doClick();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(200, 162, 89, 69);
		panel.add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(txtId.getText());
					String name = txtName.getText();
					Double price = Double.parseDouble(txtPrice.getText());
					IProductDAO productDAO = (IProductDAO) Naming.lookup("rmi://localhost:2/final");
					String result = productDAO.update(id, name, price);
					System.out.println(result);
					btnShow.doClick();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(32, 262, 89, 69);
		panel.add(btnUpdate);

		btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					IProductDAO productDAO = (IProductDAO) Naming.lookup("rmi://localhost:2/final");
					DefaultTableModel model = productDAO.showAll();

					tblProduct.setModel(model);
					
					System.out.println("show products successfully");
				} catch (Exception e2) {
					System.out.println("something wrong with show products process");
					e2.printStackTrace();
				}
			}
		});
		btnShow.setBounds(200, 262, 89, 69);
		panel.add(btnShow);

		JButton btnReadXML = new JButton("Read XML");
		btnReadXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReadXML.setBounds(32, 363, 89, 69);
		panel.add(btnReadXML);

		JButton btnWriteXML = new JButton("Write XML");
		btnWriteXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnWriteXML.setBounds(200, 363, 89, 69);
		panel.add(btnWriteXML);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 11, 469, 539);
		contentPane.add(scrollPane);
		
		tblProduct = new JTable();
		scrollPane.setViewportView(tblProduct);

		tblProduct.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tblProduct.getSelectedRow();
				TableModel tblModel = tblProduct.getModel();
				
				int id = Integer.parseInt(tblModel.getValueAt(index, 0).toString());
				String name = tblModel.getValueAt(index, 1).toString();
				Double price = Double.parseDouble(tblModel.getValueAt(index, 2).toString());
				
				txtId.setText(""+id);
				txtName.setText(name);
				txtPrice.setText(""+price);
			}
		});
		
	}
	
	
}
