package game;

import java.awt.Color;
import java.awt.Rectangle;

public class ShopButton extends Rectangle {

	private static final long serialVersionUID = 1L;

	public boolean clicked;

	private InputSystem input;
	public Color color;
	public Color iniColor;
	public Color toggleColor;
	public String name;
	private boolean released = true;
	boolean toggled = false;
	ShopButton(int x, int y, int width, int height, Color color, InputSystem input) {
		super(x, y, width, height);
		this.color = color;
		iniColor = color;
		this.input = input;
		toggleColor = Color.red;
	}
	
	public void toggle() {
		if (input.mousePressed() && released) {
			released = false;
			if (contains(input.mousePoint())) {
				toggled = !toggled;
				if (color == iniColor) {
					color = toggleColor;
				} else color = iniColor;
			}
		}
		if (!input.mousePressed()) released = true;
	}

	public void clear() {
		color = iniColor;
	}
}
