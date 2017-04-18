package pkg_main;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AppTextField extends TextField {
	
	private String fieldName;
	
	public AppTextField(String fieldName) {
		super();
		this.fieldName = fieldName;
	}
	
	public AppTextField(String text, String fieldName) {
		super(text);
		this.fieldName = fieldName;
	}
	
	// Attempts to parse an integer from the string's text property.
	// Returns false and displays an alert dialog if a NumberFormatException occurs.
	public boolean tryGetInt() {
		try {
			Integer.parseInt(this.getText());
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for " + this.fieldName + " is not an integer.");

			alert.showAndWait();
			return false;
		}
		
		return true;
	}
	
	public boolean tryGetFloat() {
		try {
			Float.parseFloat(this.getText());
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input Value Error!");
			alert.setHeaderText(null);
			alert.setContentText("The value inputed for " + this.fieldName + " is not a number.");

			alert.showAndWait();
			return false;
		}
		
		return true;
	}
	
	public boolean tryGetColour() {
		if (this.getLength() != 6 ) {
			for (int i = 0; i < 6; i++) {
				switch (this.getText().charAt(i)) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f': break;
				default: return false;
				}
			}
			return true;
		}
		return false;
	}
}
