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
	
	public boolean tryGetInt(int numRef) {
		int num;
		try {
			num = Integer.parseInt(this.getText());
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error bitch");
			alert.setHeaderText(null);
			alert.setContentText("I have a great message for you!");

			alert.showAndWait();
			return false;
		}
		numRef = num;
		
		return true;
	}
	
	public boolean tryGetFloat(double numRef) {
		double num;
		try {
			num = Double.parseDouble(this.getText());
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error bitch");
			alert.setHeaderText(null);
			alert.setContentText("I have a great message for you!");

			alert.showAndWait();
			return false;
		}
		numRef = num;
		
		return true;
	}
}
