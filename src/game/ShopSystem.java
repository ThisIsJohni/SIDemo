package game;

import java.awt.Color;
import java.util.ArrayList;

import character.CharacterHandler;
import items.*;

public class ShopSystem {
	
	protected ArrayList<Items> itemArray;
	protected ArrayList<ShopButton> btnArray;
	protected Items selectedItem;
	protected boolean openShop;
	ShopButton shopBtn, purchaseBtn;
	CharacterHandler handler;
	
	
	public ShopSystem(InputSystem inputSystem, CharacterHandler handler) {

		this.handler = handler;
		//create an item array with items in it
		
		itemArray = new ArrayList<Items>();
		itemArray.add(new Damage("Damage Up", 1000));
		itemArray.add(new Firerate("Firerate Up", 750));
		itemArray.add(new Health("Health Up", 750));
		
		//shop button that opens and closes the shop
		shopBtn  = new ShopButton(600, 20, 60, 40, new Color(72,68,58), inputSystem);
		shopBtn.toggleColor = new Color(48,39,34);
		
		//The purchase button
		purchaseBtn = new ShopButton(400, 400, 100, 50, Color.yellow, inputSystem);
		
		//initialize the btnArray
		btnArray = new ArrayList<ShopButton>();
		
		//initial coordinates for the buttons to fit into the "shop screen"
		int x = 170;
		int y = 120;
		
		//create the buttons for items in the shop
		for (int i = 0; i < itemArray.size(); i++) {
			btnArray.add(new ShopButton(x, y, 50, 50, Color.blue, inputSystem));
			x += 80;
		}
	}
	
	//gets called every frame by GameLoop
	public void update() {
		openShop();
		if (openShop) {
			selectItem();
			if (selectedItem != null) {
				purchaseItem();
				
			}
		}
	}

	//select an item in the shop
	private void selectItem() {
		for (int i = 0; i < itemArray.size(); i++) {
				if (btnArray.get(i).clicked()) {
					btnArray.get(i).toggle();
					//unselect all other shopButtons if some had been pressed
					for (int j = 0; j < btnArray.size(); j++) {
						if (btnArray.get(j) != btnArray.get(i)) {
							if (btnArray.get(j).toggled) btnArray.get(j).toggle();
						}
					}
					//set selectedItem as the item being clicked on
					if (btnArray.get(i).toggled) selectedItem = itemArray.get(i);
					else selectedItem = null;
			}
		}
	}
	
	private void purchaseItem() {
		if (selectedItem != null) {
			if (purchaseBtn.clicked()) {
				if (handler.getScore() >= selectedItem.getPrice()) {
					int index = itemArray.indexOf(selectedItem);
					purchaseBtn.toggle();
					
					if(selectedItem instanceof Damage) {
						handler.setDamage((Damage) selectedItem );
						selectedItem = null;
						btnArray.get(index).toggle();
						purchaseBtn.toggle();
					}

					if(selectedItem instanceof Health) {
							handler.setHealth((Health) selectedItem );
							selectedItem = null;
							btnArray.get(index).toggle();
							purchaseBtn.toggle();
					}
					
					if(selectedItem instanceof Firerate) {
						handler.setFirerate((Firerate) selectedItem );
						selectedItem = null;
						btnArray.get(index).toggle();
						purchaseBtn.toggle();
					}


				}
			}
		}
	}
	

	//open or close the shop screen
	private void openShop() {
		if (shopBtn.clicked()) shopBtn.toggle();
		if (shopBtn.color == shopBtn.iniColor) openShop = false;
		else openShop = true;
	}
	

	public boolean getOpenShop() {
		return openShop;
	}
	
	public Items getSelected() {
		return selectedItem;
	}
	// Shop weapons and stuff
}
