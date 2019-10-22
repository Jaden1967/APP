package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entities.GamePlay;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadPrompt extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public LoadPrompt(JFrame menuFrame) {
		setTitle("Load progress");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 400, 447, 196);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBounds(14, 47, 401, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPleaseInsertThe = new JLabel("Please insert the name of the file");
		lblPleaseInsertThe.setFont(new Font("SimSun", Font.PLAIN, 18));
		lblPleaseInsertThe.setBounds(56, 13, 315, 18);
		contentPane.add(lblPleaseInsertThe);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = textField.getText();
				Controller control = new Controller(new GamePlay());
				control.loadFile(address);
				MapUI map_ui = new MapUI (control);
				if(control.loadFile(textField.getText())) {
					JOptionPane.showMessageDialog(null, "Successfully loaded!", "Save file", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					menuFrame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Load fail!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnOk.setBounds(56, 98, 113, 27);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(258, 98, 113, 27);
		contentPane.add(btnCancel);
	}
}