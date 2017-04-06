package pkg_main;

import javafx.scene.control.Button;

public class AppButton extends Button {
	
	public AppButton(String displayText) {
		super(displayText);
		this.setOnAction(new ClsHandlers());
		this.setMinWidth(IConstants.BTN_WIDTH);
	}
	
	public AppButton() {
		super();
		this.setOnAction(new ClsHandlers());
		this.setMinWidth(IConstants.BTN_WIDTH);
	}
}
