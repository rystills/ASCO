package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Expression;
import logic.NormalForm;

public class CheckFormDisplay extends Stage
{
	public static void display(Expression ex, Stage owner)
	{
		new CheckFormDisplay(ex, owner);
	}
	
	private CheckFormDisplay(Expression ex, Stage owner)
	{
		VBox box = new VBox();
		
		for (NormalForm normalForm : NormalForm.values())
		{
			box.getChildren().add(new Text(String.format(
					"Expression is%s in %s normal form",
					normalForm.inForm(ex) ? "" : " not",
					normalForm.toString()
			)));
		}
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(event -> close());
		
		box.getChildren().add(closeButton);

		Scene scene = new Scene(box, 300, 250);
		setScene(scene);
		initModality(Modality.APPLICATION_MODAL);
		initOwner(owner);
		show();
	}
}