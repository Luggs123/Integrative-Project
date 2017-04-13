package pkg_main;

import javafx.scene.control.Button;

public class AppButton extends Button {
	
	public AppButton(String displayText) {
		super(displayText);
		this.setOnMousePressed(new ClsHandlers());
		this.setMinWidth(IConstants.BTN_WIDTH);
	}
	
	public AppButton() {
		super();
		this.setOnMousePressed(new ClsHandlers());
		this.setMinWidth(IConstants.BTN_WIDTH);
	}
}
