package ch.tbz.escaperoom.game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	JTextArea gameFeed = new JTextArea();
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
		gameFeed.setEditable(false);
		frame.getContentPane().add(gameFeed);
		gameFeed.setColumns(4);
		
		inputField = new JTextField();
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validateInput(inputField.getText());
			}
		});
		inputField.setBounds(10, 576, 530, 77);
		frame.getContentPane().add(inputField);
		inputField.setColumns(10);
		
		JButton btnClearField = new JButton("Clear Field");
		btnClearField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputField.setText("");
			}
		});
		btnClearField.setBounds(451, 664, 100, 23);
		frame.getContentPane().add(btnClearField);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.setBounds(10, 664, 89, 23);
		frame.getContentPane().add(btnRestart);
		
		background.setIcon(new ImageIcon("../EscapeRoom/res/mainMenu.png"));
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
			returnText = use(input);
		} else if(input.contains("Start") || input.contains("start")){
			start(input);
		} else if(input.contains("Rechts") || input.contains("rechts")){
			currentRoom++;
			if(currentRoom > 3) {
				currentRoom = 0;
			}
			background.setIcon(run.getSingularImage(currentRoom));;
		} else if(input.contains("Links") || input.contains("links")){
			currentRoom--;
			if(currentRoom < 0) {
				currentRoom = 3;
			}
			background.setIcon(run.getSingularImage(currentRoom));;
		} else {
			returnText = otherActions(input);
		}
		
		gameFeed.setText(returnText);
		
	}
	
	private String inspect(String input) {
		String returnText = "";
		
		if(background.getIcon().equals(run.getSingularImage(0))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE WAND":
				returnText = "An der Wand siehst du eine Türe und ein Sofa, ansonsten nichts spezielles.";
			break;
			case "UNTERSUCHE TÜR":
				returnText = "Die Tür ist abgeschlossen. \nAuch mit Kraft bewegt sich die Türe keinen Milimeter.";
			break;
			case "UNTERSUCHE SOFA":
				run.setImages("../EscapeRoom/res/FirstWall/FirstScreenOpenCouch.png", 0);
				background.setIcon(run.getSingularImage(0));
				run.isCouchOpen = true;
				returnText = "Du findest unter einem der Kissen eine runde <Goldplatte>";
			break;
			case "UNTERSUCHE BODEN":
				returnText = "Der Boden ist ein dunkles Holzparkett.\n Sieht schon etwas älter aus.";
			break;
			case "UNTERSUCHE DACH":
				returnText = "Das Dach ist zu hoch über dir um etwas daran zu untersuchen. "
						+ "\nBeim Beobachtern jedoch findest du nicht spezielles ausser der Dachlampe.";
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(1))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE BILD":
				returnText = "Auf dem Bild siehst du einen schönen Sonnenaufgang.";
			break;
			case "UNTERSUCHE WAND":
				returnText = "An der Wand siehst du von links nach rechts:\n ein Bild, einen Safe und eine Pflanze";
			break;
			case "UNTERSUCHE SAFE":
				if(run.isSafeOpen) {
					returnText = "Im offenen Safe ist ein <Gerät> welches dir nicht bekannt vorkommt.";
				} else {
					returnText = "Der Safe ist verschlossen.\n Anstatt eines normalen Schlossen hat dieser Safe eine Runde öffnung rechts am Safe.";
				}
			break;
			case "UNTERSUCHE PFLANZE":
				returnText = "Die Pflanze hat drei Blumen drin,\n die Farben der Blume sind von Link nach Rechts:\n Rosa, Violett und Violett.";
			break;
			case "UNTERSUCHE GERÄT":
				if(run.isSafeOpen) {
					returnText = "Das Gerät hat einen Bildschirm in welchem nach einem Passwort gefragt wird.\n Unter dem Eingabefesnter steht:"
							+ "\n 'Tipp: 3 Farben.'"
							+ "\n(Falls du ein Passwort eingeben willst schreibe 'Passwort' und dann das Passwort. z.B. Passwort 1 2 3)";
				} else {
					returnText = "Du Siehst kein Gerät im Raum.";
				}
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(2))) {
			switch(input.toUpperCase()) {
			case "UNTERSUCHE WAND":
				returnText = "An der Wand siehst einen Schalter.";
			break;
			case "UNTERSUCHE BRECHEISEN":
				returnText = "Das Brecheisen sieht sehr neu und unbenutzt aus.\n Es könnt noch Nützlich werden.";
			break;
			case "UNTERSUCHE SCHALTER":
				if(run.isSwitchActivated) {
					returnText = "Du hast den Schalter betätigt.";	
				} else {
					returnText = "Der Schalter and der Wand ist sehr gross und bewegt sich kaum wenn du ihn Drehen willst. "
							+ "\nWie es aussieht klemmt der Schalter. "
							+ "\nMit einen Stabilen Gegenstand könntest du den Schalter vielleicht zum drehen bringen.";
				}
			break;
			case "UNTERSUCHE WASSER":
				returnText = "Das Wasser Tropft von der Decke.\n Wahrscheinlich ist das Dach nicht mehr ganz dicht.";
			break;
			default:
				returnText = "Du siehst kein " + input + " im Raum.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(3))) {
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
					returnText = "Der Laser Markiert eine Fläche am Boden.\n Vielleicht verbirgt sich etwas darunter?";
				} else {
					returnText = "Du siehst keine Markierung im Raum.";
				}
				
			break;
			case "UNTERSUCHE LOCH":
				if(run.isWallOpen) {
					if(run.getInventory().contains("Samen")) {
						returnText = "In der Wand ist ein Loch sichtbar.";
					} else {
						returnText = "In der Wand ist ein Loch sichtbar.\n Im Loch befinden sich <Samen>.";
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
		
		if(background.getIcon().equals(run.getSingularImage(0))) {
			switch(input.toUpperCase()) {
			case "NIMM GOLDPLATTE":
				if(run.isCouchOpen) {
					returnText = "Du nimmst die <Goldplatte> ins Inventar auf.";
					run.setInventory("Goldplatte");
					run.setImages("../EscapeRoom/res/FirstWall/FirstScreenOpenCouchWithoutKey.png", 0);
					background.setIcon(run.getSingularImage(0));
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
						background.setIcon(run.getSingularImage(0));
					}
				}
					
			break;
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(1))) {
			switch(input.toUpperCase()) {
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(2))) {
			switch(input.toUpperCase()) {
			case "NIMM BRECHEISEN":
				returnText = "Du nimmst das <Brecheisen> ins Inventar auf.";
				run.setInventory("Brecheisen");
				run.setImages("../EscapeRoom/res/ThirdWall/ThirdWallWithoutCrowbar.png", 2);
				background.setIcon(run.getSingularImage(2));
			break;
			default:
				returnText = "Du siehst kein " + input + " das du aufnehmen kannst.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(3))) {
			switch(input.toUpperCase()) {
			case "NIMM SCHLÜSSEL":
				if(run.isKeyVisible) {
					returnText = "Du nimmst den <Schlüssel> ins Inventar auf.";
					run.setInventory("Schlüssel");
					run.setImages("../EscapeRoom/res/FourthWall/FourthWallWithPlant.png", 3);
					background.setIcon(run.getSingularImage(3));
				} else {
					returnText = "Du siehst kein Schlüssel das du aufnehmen kannst.";
				}
				
			break;
			case "NIMM SAMEN":
				if(run.isWallOpen) {
					returnText = "Du nimmst den <Samen> ins Inventar auf.";
					run.setInventory("Samen");
					run.setImages("../EscapeRoom/res/FourthWall/FourthWallOpenWithoutSeeds.png", 3);
					background.setIcon(run.getSingularImage(3));
				} else {
					returnText = "Du siehst kein Samen das du aufnehmen kannst.";
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
		
		if(background.getIcon().equals(run.getSingularImage(0))) {
			if(run.isCouchOpen) {
				if(run.getInventory().contains("Goldplatte") && run.getInventory().contains("Schale")) {
					returnText = "Das Sofa hat ein verschobenes Kissen.";
				} else if(run.getInventory().contains("Goldplatte") && !(run.getInventory().contains("Schale"))) {
					returnText = "Das Sofa hat ein verschobenes Kissen. \nAm Boden vor dem Sofa is eine <Schale>.";
				} else if(!(run.getInventory().contains("Goldplatte")) && (run.getInventory().contains("Schale"))) {
					returnText = "Das Sofa hat ein verschobenes Kissen. \nUnter dem verschobenen Kissen ist eine <Goldplatte>.";
				} else if(!(run.getInventory().contains("Goldplatte")) && !(run.getInventory().contains("Schale"))) {
					returnText = "Das Sofa hat ein verschobenes Kissen. \nUnter dem verschobenen Kissen ist eine <Goldplatte>."
							+ "\nAm Boden vor dem Sofa is eine <Schale>.";
				}
			} else {
				if(run.getInventory().contains("Schale")) {
					returnText = "An der Wand ist ein Sofa neben der abgeschlossenen Türe.";
				} else if(!(run.getInventory().contains("Schale"))) {
					returnText = "An der Wand ist ein Sofa neben der abgeschlossenen Türe."
							+ "\nAm Boden vor dem Sofa is eine <Schale>.";
				}
			}

		} else if(background.getIcon().equals(run.getSingularImage(1))) {
			if(run.isSafeOpen) {
				returnText = "Der Safe ist offen. Im Safe ist ein seltsames Gerät.";
			} else {
				returnText = "An der Wand ist ein Safe, ein Bild und eine Pflanze.";
			}
		} else if(background.getIcon().equals(run.getSingularImage(2))) {
			if(run.getInventory().contains("Brecheisen")) {
				returnText = "An der Wand ist ein Schalter und von der Decke Tropft Wasser auf den Boden.";
			} else {
				returnText = "An der Wand ist ein Schalter und von der Decke Tropft Wasser auf den Boden."
						+ "\nAm Boden liegt noch ein Brecheisen.";
			}
		} else if(background.getIcon().equals(run.getSingularImage(3))) {
			if(run.isGroundOpen && run.isWallOpen) {
				returnText = "An der Wand ist über der Blumenzeichnung ein Loch."
						+ "\nIm Boden ist ein Loch mit Erde darunter.";
			} else if(!(run.isGroundOpen) && run.isWallOpen) {
				returnText = "An der Wand ist über der Blumenzeichnung ein Loch.";
			} else if(run.isGroundOpen && !(run.isWallOpen)) {
				returnText = "An der Wand eine Blumenzeichnung."
						+ "\nIm Boden ist ein Loch mit Erde darunter.";
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
	
	private String use(String input) {
		String returnText = "";
		String[] items = new String [2];
		items = input.replaceAll(" ", "").split("mit");
		items[0].replace("benutze", "");
		
		if(background.getIcon().equals(run.getSingularImage(0))) {	
			switch(input.toUpperCase()) {
			case "BENUTZE SCHLÜSSEL MIT TÜRE":
				if(run.getInventory().contains("Schlüssel")) {
					returnText = "Du öffnest die Türe mit deinem Schlüssel.";
					run.setImages("../EscapeRoom/res/FirstWall/FirstScreenWithOpenDoor.png", 0);
					background.setIcon(run.getSingularImage(0));
				} else {
					returnText = "Du hast keinen passenden Schlüssel im Inventar.";
				}
				
			break;
			default:
				returnText = "Du kannst "+items[0]+" nicht mit "+items[1]+" benutzen.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(1))) {
			switch(input.toUpperCase()) {
			case "BENUTZE GOLDPLATTE MIT SAFE":
				if(run.getInventory().contains("Goldplatte")) {
					returnText = "Du öffnest den Safe mit der Goldplatte.";
					run.setImages("../EscapeRoom/res/SecondWall/SecondWallOpenSafe.png", 1);
					background.setIcon(run.getSingularImage(1));
					run.isSafeOpen = true;
				} else {
					returnText = "Du hast keine Goldplatte im Inventar.";
				}
				
			break;
			default:
				returnText = "Du kannst "+items[0]+" nicht mit "+items[1]+" benutzen.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(2))) {
			switch(input.toUpperCase()) {
			case "BENUTZE BRECHEISEN MIT SCHALTER":
				if(run.getInventory().contains("Brecheisen")) {
					returnText = "Du Betätigst den Schalter mithilfe des Brecheisens."
							+ "\nDu hörst einen Mechanismus rechts von dir.";
					run.setImages("../EscapeRoom/res/ThirdWall/ThirdWallActivatedButton.png", 2);
					if(run.isGroundOpen) {
						run.setImages("../EscapeRoom/res/FourthWall/FourthWallWithOpenDirt.png", 3);
					} else {
						run.setImages("../EscapeRoom/res/FourthWall/FourthWallOpenWithSeeds.png", 3);
					}
					background.setIcon(run.getSingularImage(2));
					run.isDeviceActivated = true;
					run.isSwitchActivated = true;
					run.isWallOpen = true;
				} else {
					returnText = "Du hast kein Brecheisen im Inventar.";
				}
				
			break;
			case "BENUTZE SCHALE MIT WASSER":
				if(run.getInventory().contains("Schale")) {
					returnText = "Du füllst die Schale mit Wasser.";
					run.removeItem("Schale");
					run.setInventory("Schale mit Wasser");
				} else {
					returnText = "Du hast keine leere Schale im Inventar.";
				}
				
			break;
			default:
				returnText = "Du kannst "+items[0]+" nicht mit "+items[1]+" benutzen.";
				break;
			}
		} else if(background.getIcon().equals(run.getSingularImage(3))) {
			switch(input.toUpperCase()) {
			case "BENUTZE SCHALE MIT ERDE":
				if(run.getInventory().contains("Schale mit Wasser") && run.isSeedInGround) {
					returnText = "Du Wässerst die Samen in der Erde."
							+ "\nMit voller Wucht wächste eine riesige Pflanze aus dem Boden und macht ein Loch ins Dach."
							+ "\nVom Dach fällt ein Schlüssel auf den Boden.";
					run.setImages("../EscapeRoom/res/FourthWall/FourthWallWithPlantAndKey.png", 3);
					background.setIcon(run.getSingularImage(3));
					run.isKeyVisible = true;
				} else {
					returnText = "Du kannst das nicht machen.";
				}
				
			break;
			case "BENUTZE SAMEN MIT ERDE":
				if(run.getInventory().contains("Samen")) {
					returnText = "Du pflanzt die Samen in die Erde.";
					run.removeItem("Samen");
					run.isSeedInGround = true;
				} else {
					returnText = "Du kannst das nicht machen.";
				}
				
			break;
			case "BENUTZE BRECHEISEN MIT MARKIERUNG":
				if(run.getInventory().contains("Brecheisen")) {
					returnText = "Du reisst ein Stück Holz aus dem Boden wo die Markierung ist mithilfe des Brecheisens."
							+ "\nUnter dem Holz verbirgt sich ein haufen Erde.";
					run.setImages("../EscapeRoom/res/FourthWall/FourthWallWithOpenDirt.png", 3);
					background.setIcon(run.getSingularImage(3));
					run.isGroundOpen = true;
				} else {
					returnText = "Du hast kein Brecheisen.";
				}
				
			break;
			default:
				returnText = "Du kannst "+items[0]+" nicht mit "+items[1]+" benutzen.";
				break;
			}
		}
		
		return returnText;
	}
	
	private void start(String input) {
		run = new GameRun();
		background.setIcon(run.getSingularImage(0));
	}
	
	private String otherActions(String input) {
		String returnText = "";
		
		if(input.toUpperCase().contains("PASSWORT")) {
			if(input.equalsIgnoreCase("Passwort Rosa Violett Violett") && run.isDeviceActivated) {
				returnText = "Das Gerät sendet einen roten Lichtstrahl aus."
						+ "\nEr zeigt vor eine der anderen Wände auf den Boden.";
				run.isMarkerVisible = true;
				run.setImages("../EscapeRoom/res/FourthWall/FourthWallWithMarker.png", 3);
			} else if(!run.isDeviceActivated) {
				returnText = "Auf dem Eingabegerät des Gerätes steht:"
						+ "\nInsufficient Power.";
			} else if(run.isDeviceActivated) {
				returnText = "Falsche Passwort";
			}
		}
		if(input.equalsIgnoreCase("Anleitung")) {
			background.setIcon(new ImageIcon("../EscapeRoom/res/Anleitung.png"));
		}
		
		return returnText;
	}

}
