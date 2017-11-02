package ch.tbz.escaperoom.utilities;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class GameRun {

	public ArrayList<String> getInventory() {
		return inventory;
	}
	
	public void setInventory(String item) {
		if(!this.inventory.contains(item)) {
			inventory.add(item);
		}
	}
	
	public boolean isGameHasBegun() {
		return gameHasBegun;
	}
	
	public void setGameHasBegun(boolean gameHasBegun) {
		this.gameHasBegun = gameHasBegun;
	}
	
	public ImageIcon getSingularImage(int i) {
		return images[i];
	}
	
	public void setImages(String input, int number) {
		this.images[number] = new ImageIcon(input);
	}
	
	public void removeItem(String input) {
		this.inventory.remove(input);
	}
	
	private ArrayList<String> inventory = new ArrayList<>();
	private boolean gameHasBegun;
	private ImageIcon[] images = new ImageIcon[4];
	public boolean isSafeOpen = false;
	public boolean isSwitchActivated = false;
	public boolean isWallOpen = false;
	public boolean isGroundOpen = false;
	public boolean isMarkerVisible = false;
	public boolean isKeyVisible = false;
	public boolean isCouchOpen = false;
	public boolean isSeedInGround = false;
	public boolean isDeviceActivated = false;
	
	public GameRun() {
		inventory = new ArrayList<>();
		gameHasBegun = true;
		images[0] = new ImageIcon("../EscapeRoom/res/FirstWall/FirstScreen.png");
		images[1] = new ImageIcon("../EscapeRoom/res/SecondWall/SecondWall.png");
		images[2] = new ImageIcon("../EscapeRoom/res/ThirdWall/ThirdWall.png");
		images[3] = new ImageIcon("../EscapeRoom/res/FourthWall/FourthWall.png");
		gameHasBegun = false;
		isSafeOpen = false;
		isSwitchActivated = false;
		isWallOpen = false;
		isGroundOpen = false;
		isMarkerVisible = false;
		isKeyVisible = false;
		isCouchOpen = false;
		isSeedInGround = false;
		isDeviceActivated = false;
	}
	
}
