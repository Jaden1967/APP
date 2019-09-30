package UI;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Initial extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Initial frame = new Initial();
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
	public Initial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("Setting");
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		JLabel lblNewLabel = new JLabel("place for map picture");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.PINK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(14, 13, 370, 313);
		getContentPane().add(lblNewLabel);
		
		JButton btnSelectMap = new JButton("select map");
		btnSelectMap.setFont(new Font("SimSun", Font.PLAIN, 18));
		btnSelectMap.setBounds(92, 339, 216, 37);
		getContentPane().add(btnSelectMap);
		
		JTextField textField = new JTextField();
		textField.setBounds(572, 360, 149, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("SimSun", Font.PLAIN, 20));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(451, 364, 72, 18);
		getContentPane().add(lblName);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblColor.setFont(new Font("SimSun", Font.PLAIN, 20));
		lblColor.setBounds(451, 417, 72, 18);
		getContentPane().add(lblColor);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textArea.setEditable(false);
		textArea.setBounds(412, 13, 356, 313);
		getContentPane().add(textArea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("SimSun", Font.PLAIN, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"red", "blue", "yellow", "purple", "black", "green"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(572, 411, 149, 31);
		getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("Add Player");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Name cannot be empty!");
				}
				else {
					textArea.append(textField.getText()+" "+comboBox.getItemAt(comboBox.getSelectedIndex())+"\n");
				}
			}
		});
		btnNewButton.setFont(new Font("SimSun", Font.PLAIN, 20));
		btnNewButton.setBounds(451, 480, 134, 31);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Start Game");
		btnNewButton_1.setFont(new Font("SimSun", Font.PLAIN, 20));
		btnNewButton_1.setBounds(107, 438, 189, 54);
		getContentPane().add(btnNewButton_1);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("SimSun", Font.PLAIN, 20));
		btnReset.setBounds(634, 480, 134, 31);
		contentPane.add(btnReset);
	}

}
