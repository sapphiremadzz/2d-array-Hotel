package maddie;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class HotelReservation extends JFrame{
	static JTextField txtName , txtType , txtCheckIn , txtCheckOut;
	DefaultTableModel model;
	JTable table;
	public static final String FILE_NAME = "hotel.txt";
	
	public HotelReservation() {
		JLabel lblName = new JLabel("Guest Name"); add(lblName).setBounds(20,350,100,20);
		JLabel lblType = new JLabel("Room Type"); add(lblType).setBounds(150,350,100,20);
		JLabel lblIn = new JLabel("Checl In Date"); add(lblIn).setBounds(280,350,120,20);
		JLabel lblOut = new JLabel("Checl Out Date"); add(lblOut).setBounds(410,350,120,20);
		
		txtName = new JTextField(); add(txtName).setBounds(20,380,100,20);
		txtType = new JTextField(); add(txtType).setBounds(150,380,100,20);
		txtCheckIn = new JTextField(); add(txtCheckIn).setBounds(280,380,100,20);
		txtCheckOut = new JTextField(); add(txtCheckOut).setBounds(410,380,100,20);
		
		showTable(); readData();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked (MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				
				if(selectedRow !=-1) {
					txtName.setText(model.getValueAt(selectedRow, 0).toString());
					txtType.setText(model.getValueAt(selectedRow, 1).toString());
					txtCheckIn.setText(model.getValueAt(selectedRow, 2).toString());
					txtCheckOut.setText(model.getValueAt(selectedRow, 3).toString());
				}
			}
		});
		
		
		JButton btnAdd = new JButton("Add"); add(btnAdd).setBounds(550,380,100,20);
		JButton btnUpdate = new JButton("Update"); add(btnUpdate).setBounds(550,410,100,20);
		JButton btnDelete = new JButton("Delete"); add(btnDelete).setBounds(550,440,100,20);
		JButton btnExit = new JButton("Exit"); add(btnExit).setBounds(550,470,100,20);
		
		btnAdd.addActionListener(e->{
			add();
		});
		btnDelete.addActionListener(e->{
			delete();
		});
		btnUpdate.addActionListener(e->{
			update();
		});
		btnExit.addActionListener(e->{
			exit();
		});
		
		
		setSize(700,580);
		setTitle("Hotel Reseravation System");
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void clear() {
		txtName.setText("");
		txtType.setText("");
		txtCheckIn.setText("");
		txtCheckOut.setText("");
	}
	public void showTable() {
		String col [] = {"Guest Name", "Room Type", "Check-In Date", "Check-Out Date"};
		model = new DefaultTableModel(col,0);
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table); add(scroll).setBounds(20,20,650,300);
	}
	
	public void add() {
		try(FileWriter fw = new FileWriter(FILE_NAME, true)){
			if(txtName.getText().isEmpty() || txtType.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "please fill out first"); return;
			}
			
			String name = txtName.getText();
			String type = txtType.getText();
			String in = txtCheckIn.getText();
			String out = txtCheckOut.getText();
			
			model.addRow(new Object[] {name, type, in, out});
			fw.write(name + "#" + type + "#" + in + "#" + out + "\n");
			
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "error" + e.getMessage()); 
		}
		clear();
	}
	
	public void delete() {
		int selectedRow = table.getSelectedRow();
		if(selectedRow ==-1) {
			JOptionPane.showMessageDialog(null, "pelase select a row first"); return;
		}
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "delete Confirmation" , JOptionPane.YES_NO_OPTION);
		if(confirm != JOptionPane.YES_OPTION) {
			return;
		}
		ArrayList <String> lines = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
			String line;
			int rowIndex = 0;
			while((line = br.readLine())!=null) {
				if(rowIndex !=selectedRow) {
					lines.add(line);
				} rowIndex++;
			}
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "error" + e.getMessage()); 
		} saveData(lines);
	}
	
	public void readData() {
		model.setRowCount(0);
		try(BufferedReader br = new BufferedReader (new FileReader (FILE_NAME))){
			String line;
			while((line = br.readLine())!= null) {
				String row[] = line.split("#");
				if(row.length==4) {
					model.addRow(row);
				}
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "error" + e.getMessage()); 
		}
	}
	
	public void update() {
		int selectedRow = table.getSelectedRow();
		if(selectedRow ==-1) {
			JOptionPane.showMessageDialog(null, "pelase select a row first"); return;
		}
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "update Confirmation" , JOptionPane.YES_NO_OPTION);
		if(confirm != JOptionPane.YES_OPTION) {
			return;
		}
		ArrayList <String> lines = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
			
			if(txtName.getText().isEmpty() || txtType.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "please fill out first"); return;
			}
			
			String line;
			int rowIndex = 0;
			
			while((line = br.readLine())!=null) {
				if(rowIndex ==selectedRow) {
					String update = txtName.getText() + "#" + txtType.getText() + "#" + txtCheckIn.getText() + "#" + txtCheckOut.getText();
					lines.add(update);
				} else {
					lines.add(line);
					}   rowIndex++;
			}
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "error" + e.getMessage()); 
		} saveData(lines);
	}
	
	public void exit() {
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "delete Confirmation" , JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else {
			return;
		}
	}
	
	public void saveData(ArrayList <String> lines) {
		try(BufferedWriter bw = new BufferedWriter (new FileWriter(FILE_NAME))){
			for(String line: lines) {
				bw.write(line);
				bw.newLine();
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "error" + e.getMessage()); 
		} readData();
	}
	

	public static void main(String[] args) {
		new HotelReservation();

	}

}
