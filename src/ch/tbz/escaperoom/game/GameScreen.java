package ch.tbz.escaperoom.game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ch.tbz.escaperoom.utilities.GameRun;

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
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GameScreen {

	private JFrame frame;
	private JTextField inputField;
	private JLabel background = new JLabel();
	JLabel gameFeed = new JLabel();
	private GameRun run;
	private int currentRoom;

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
		String returnText = "";
		if(input.contains("Untersuche") || input.contains("untersuche")){
			returnText = inspect(input);
		} else if(input.contains("Nimm") || input.contains("nimm")){
			returnText = take(input);
		} else if(input.contains("Situation") || input.contains("situation")){
			returnText = situation(input);
		} else if(input.contains("Inventar") || input.contains("inventar")){
			returnText = inventory(input);
		} else if(input.contains("Benutze") || input.contains("benutze")){
			use(input);
		} else if(input.contains("Start") || input.contains("start")){
			start(input);
		} else if(input.contains("Rechts") || input.contains("rechts")){
			currentRoom++;
			if(currentRoom > 3) {
				currentRoom = 0;
			}
			background.setIcon(run.getSinuglarImage(currentRoom));;
		} else if(input.contains("Links") || input.contains("links")){
			currentRoom--;
			if(currentRoom < 0) {
				currentRoom = 3;
			}
			background.setIcon(run.getSinuglarImage(currentRoom));;
		} else {
			otherActions(input);
		}
		
		gameFeed.setText(returnText);
		
	}
	
	private String inspect(String input) {
		String returnText = "";
		
		if(background.getIcon().equals(run.getSinuglarImage(0))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE WAND":
				returnText = "An der Wand siehst du eine Türe und ein Sofa, ansonsten nichts spezielles.";
			break;
			case "UNTERSUCHE TÜR":
				returnText = "Die Tür ist abgeschlossen. Auch mit Kraft bewegt sich die Türe keinen Milimeter.";
			break;
			case "UNTERSUCHE SOFA":
				run.setImages("../EscapeRoom/res/FirstWall/FirstScreenOpenCouch.png", 0);
				background.setIcon(run.getSinuglarImage(0));
				run.isCouchOpen = true;
				returnText = "Du findest unter einem der Kissen eine runde <Goldplatte>";
			break;
			case "UNTERSUCHE BODEN":
				returnText = "Der Boden ist ein dunkles Holzparkett. Sieht schon etwas älter aus.";
			break;
			case "UNTERSUCHE DACH":
				returnText = "Das Dach ist zu hoch über dir um etwas daran zu untersuchen. "
						+ "Beim Beobachtern jedoch findest du nicht spezielles ausser der Dachlampe.";
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(1))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE BILD":
				returnText = "Auf dem Bild siehst du einen schönen Sonnenaufgang.";
			break;
			case "UNTERSUCHE WAND":
				returnText = "An der Wand siehst du von links nach rechts: ein Bild, einen Safe und eine Pflanze";
			break;
			case "UNTERSUCHE SAFE":
				if(run.isSafeOpen) {
					returnText = "Im offenen Safe ist ein <Gerät> welches dir nicht bekannt vorkommt.";
				} else {
					returnText = "Der Safe ist verschlossen. Anstatt eines normalen Schlossen hat dieser Safe eine Runde öffnung rechts am Safe.";
				}
			break;
			case "UNTERSUCHE PFLANZE":
				returnText = "Die Pflanze hat drei Blumen drin, die Farben der Blume sind von Link nach Rechts: Pink, Violett und Violett.";
			break;
			case "UNTERSUCHE GERÄT":
				if(run.isSafeOpen) {
					returnText = "Das Gerät hat einen Bildschirm in welchem nach einem Passwort gefragt wird. Unter dem Eingabefesnter steht:"
							+ " 'Tipp: 3 Farben.'"
							+ "Falls du ein Passwort eingeben willst schreibe <Passwort> und dann das Passwort.";
				} else {
					returnText = "Du Siehst kein Gerät im Raum.";
				}
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(2))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE WAND":
				returnText = "An der Wand siehst einen Schalter.";
			break;
			case "UNTERSUCHE BRECHEISEN":
				returnText = "Das Brecheisen sieht sehr neu und unbenutzt aus. Es könnt noch Nützlich werden.";
			break;
			case "UNTERSUCHE SCHALTER":
				if(run.isSwitchActivated) {
					returnText = "Du hast den Schalter betätigt.";	
				} else {
					returnText = "Der Schalter and der Wand ist sehr gross und bewegt sich kaum wenn du ihn Drehen willst. "
							+ "Wie es aussieht klemmt der Schalter. "
							+ "Mit einen Stabilen Gegenstand könntest du den Schalter vielleicht zum drehen bringen.";
				}
			break;
			case "UNTERSUCHE WASSER":
				returnText = "Das Wasser Tropft von der Decke. Wahrscheinlich ist das Dach nicht mehr ganz dicht.";
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(3))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE WAND":
				if(run.isWallOpen) {
					returnText = "An der Wand ist über der Blumenzeichnung ein Loch.";
				} else {
					returnText = "An der Wand ist eine Zeichnung von Blumen.";
				}
				
			break;
			case "UNTERSUCHE MARKIERUNG":
				if(run.isMarkerVisible) {
					returnText = "Der Laser Markiert eine Fläche am Boden. Vielleicht verbirgt sich etwas darunter?";
				} else {
					returnText = "Du siehst keine Markierung im Raum.";
				}
				
			break;
			case "UNTERSUCHE LOCH":
				if(run.isWallOpen) {
					if(run.getInventory().contains("Samen")) {
						returnText = "In der Wand ist ein Loch sichtbar.";
					} else {
						returnText = "In der Wand ist ein Loch sichtbar. Im Loch befinden sich <Samen>.";
					}
				} else {
					returnText = "Du siehst kein Loch im Raum.";
				}
				
				
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		}
		
		
		
		return returnText;
	}
	
	private String take(String input) {
		String returnText = "";
		
		if(background.getIcon().equals(run.getSinuglarImage(0))) {
			switch(input.toUpperCase()) {
			case "NIMM GOLDPLATTE":
				if(run.isCouchOpen) {
					returnText = "Du nimmst die <Goldplatte> ins Inventar auf.";
					run.setInventory("Goldplatte");
					run.setImages("../EscapeRoom/res/FirstWall/FirstScreenOpenCouchWithoutKey.png", 0);
					background.setIcon(run.getSinuglarImage(0));
				} else {
					returnText = "Du siehst kein Goldplatte das du aufnehmen kannst";
				}
				
			break;
			case "NIMM SCHALE":
				if(run.getInventory().contains("Schale")) {
					returnText = "Du siehst kein Schale das du aufnehmen kannst";
				} else {
					returnText = "Du nimmst die <Schale> ins Inventar auf.";
					run.setInventory("Schale");
					if(run.isCouchOpen) {
						
					} else {
						run.setImages("../EscapeRoom/res/FirstWall/FirstScreenWithoutBowl.png", 0);
						background.setIcon(run.getSinuglarImage(0));
					}
				}
					
			break;
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(1))) {
			switch(input.toUpperCase()) {
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(2))) {
			switch(input.toUpperCase()) {
			case "NIMM BRECHEISEN":
				returnText = "Du nimmst das <Brecheisen> ins Inventar auf.";
				run.setInventory("Brecheisen");
				run.setImages("../EscapeRoom/res/ThirdWall/ThirdWallWithoutCrowbar", 2);
				background.setIcon(run.getSinuglarImage(2));
			break;
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(3))) {
			switch(input.toUpperCase()) {
			case "NIMM SCHLÜSSEL":
				if(run.isKeyVisible) {
					returnText = "Du nimmst den <Schlüssel> ins Inventar auf.";
					run.setInventory("Schlüssel");
				} else {
					returnText = "Du siehst kein Schlüssel das du aufnehmen kannst.";
				}
				
			break;
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		}
		
		return returnText;
	}

	private String situation(String input) {
		String returnText = "";
		
		if(background.getIcon().equals(run.getSinuglarImage(0))) {
			if(run.isCouchOpen) {
				if(run.getInventory().contains("Goldplatte") && run.getInventory().contains("Schale")) {
					returnText = "Das Sofa hat ein verschobenes Kissen.";
				} else if(run.getInventory().contains("Goldplatte") && !(run.getInventory().contains("Schale"))) {
					returnText = "Das Sofa hat ein verschobenes Kissen. Am Boden vor dem Sofa is eine <Schale>.";
				} else if(!(run.getInventory().contains("Goldplatte")) && (run.getInventory().contains("Schale"))) {
					returnText = "Das Sofa hat ein verschobenes Kissen. Unter dem verschobenen Kissen ist eine <Goldplatte>.";
				} else if(!(run.getInventory().contains("Goldplatte")) && !(run.getInventory().contains("Schale"))) {
					returnText = "Das Sofa hat ein verschobenes Kissen. Unter dem verschobenen Kissen ist eine <Goldplatte>."
							+ "Am Boden vor dem Sofa is eine <Schale>.";
				}
			} else {
				if(run.getInventory().contains("Schale")) {
					returnText = "An der Wand ist ein Sofa neben der abgeschlossenen Türe.";
				} else if(!(run.getInventory().contains("Schale"))) {
					returnText = "An der Wand ist ein Sofa neben der abgeschlossenen Türe."
							+ "Am Boden vor dem Sofa is eine <Schale>.";
				}
			}

		} else if(background.getIcon().equals(run.getSinuglarImage(1))) {
			if(run.isSafeOpen) {
				returnText = "Der Safe ist offen. Im Safe ist ein seltsames Gerät.";
			} else {
				returnText = "An der Wand ist ein Safe, ein Bild und eine Pflanze.";
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(2))) {
			if(run.getInventory().contains("Brecheisen")) {
				returnText = "An der Wand ist ein Schalter und von der Decke Tropft Wasser auf den Boden.";
			} else {
				returnText = "An der Wand ist ein Schalter und von der Decke Tropft Wasser auf den Boden."
						+ "Am Boden liegt noch ein Brecheisen.";
			}
		} else if(background.getIcon().equals(run.getSinuglarImage(3))) {
			if(run.isGroundOpen && run.isWallOpen) {
				returnText = "An der Wand ist über der Blumenzeichnung ein Loch."
						+ "Im Boden ist ein Loch mit Erde darunter.";
			} else if(!(run.isGroundOpen) && run.isWallOpen) {
				returnText = "An der Wand ist über der Blumenzeichnung ein Loch.";
			} else if(run.isGroundOpen && !(run.isWallOpen)) {
				returnText = "An der Wand eine Blumenzeichnung."
						+ "Im Boden ist ein Loch mit Erde darunter.";
			} else if(!(run.isGroundOpen) && !(run.isWallOpen)) {
				returnText = "An der Wand ist eine Blumenzeichnung.";
			}
			if(run.isMarkerVisible) {
				returnText = "Im Raum ist eine Markierung am Boden.";
			}
		}
		
		return returnText;
	}

	private String inventory(String input) {
		String returnText = "In deinem Inventar ist:";
		ArrayList<String> items = run.getInventory();
		for(String s : items) {
			returnText += s + ", ";
		}
		return returnText;
		
	}
	
	private void use(String input) {
			
	}
	
	private void start(String input) {
		run = new GameRun();
		background.setIcon(run.getSinuglarImage(0));
	}
	
	private void otherActions(String input) {
		
	}

}
