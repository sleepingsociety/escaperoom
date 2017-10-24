package ch.tbz.escaperoom.game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ch.tbz.escaperoom.utilities.ImagePanel;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GameScreen {

	private ImageIcon[] images = new ImageIcon[4];
	private JFrame frame;
	private JTextField inputField;
	private JLabel background = new JLabel();
	JLabel gameFeed = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameScreen window = new GameScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		images[0] = new ImageIcon("../EscapeRoom/res/FirstWall/FirstScreen.png");
		images[1] = new ImageIcon("../EscapeRoom/res/SecondWall/SecondWall.png");
		images[2] = new ImageIcon("../EscapeRoom/res/ThirdWall/ThirdWall.png");
		images[3] = new ImageIcon("../EscapeRoom/res/FourthWall/FourthWall.png");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 566, 734);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		background.setBounds(10, 11, 530, 480);
		frame.getContentPane().add(background);
		

		gameFeed.setBounds(10, 507, 530, 58);
		frame.getContentPane().add(gameFeed);
		
		inputField = new JTextField();
		inputField.setBounds(10, 576, 530, 77);
		frame.getContentPane().add(inputField);
		inputField.setColumns(10);
		
		JButton btnClearField = new JButton("Clear Field");
		btnClearField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputField.setText("");
			}
		});
		btnClearField.setBounds(352, 664, 89, 23);
		frame.getContentPane().add(btnClearField);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validateInput(inputField.getText());
			}
		});
		btnSend.setBounds(451, 664, 89, 23);
		frame.getContentPane().add(btnSend);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.setBounds(10, 664, 89, 23);
		frame.getContentPane().add(btnRestart);
	}
	
	private void validateInput(String input){
		gameFeed.setText("kek");
		
		switch(input){
			case "first":
				background.setIcon(images[0]);
				break;
			case "second":
				background.setIcon(images[1]);
				break;
			case "third":
				background.setIcon(images[2]);
				break;
			case "fourth":
				background.setIcon(images[3]);
				break;
		}
		
	}
}
