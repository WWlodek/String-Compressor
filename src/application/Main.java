package application;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {

	static String compress(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] charArray = str.toCharArray();
		int count = 1;
		char lastChar = charArray[0];
		for (int i = 1; i < charArray.length; i++) {
			char nextChar = charArray[i];
			if (lastChar == nextChar) {
				count++;
			} else {
				stringBuilder.append(count).append(lastChar);
				count = 1;
				lastChar = nextChar;
			}
		}
		stringBuilder.append(count).append(lastChar);
		String compressed = stringBuilder.toString();
		return compressed;
	}

	static String decompress(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i += 2) {
			char n = charArray[i];

			String m = String.valueOf(n);
			int j = Integer.parseInt(m);

			for (int k = 0; k < j; k++) {
				char literka = charArray[i + 1];
				stringBuilder.append(literka);
			}
		}

		String decompressed = stringBuilder.toString();
		return decompressed;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 450, 200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Text fields and Labels ===========
			Label label1 = new Label("Input text:");
			label1.setId("label1");

			TextField txt = new TextField();
			txt.getStyleClass().add("field1");

			Label label2 = new Label("Output:");
			label2.setId("label2");

			TextField dst = new TextField();
			dst.getStyleClass().add("field2");

			// Buttons ===========================
			Button but1 = new Button();
			but1.setId("but1");
			but1.setText("Count!");

			Button but2 = new Button();
			but2.setId("but2");
			but2.setText("Save to file");

			// Radio Buttons ======================
			RadioButton r1 = new RadioButton("Compress");
			RadioButton r2 = new RadioButton("Decompress");

			ToggleGroup toggle1 = new ToggleGroup();
			r1.setToggleGroup(toggle1);
			r2.setToggleGroup(toggle1);

// Count Button Action =============================
			but1.setOnAction(event -> {

				if (r1.isSelected()) {

					String alpha = txt.getText();
					dst.setText(compress(alpha));

				} else {

					String alpha = txt.getText();
					dst.setText(decompress(alpha));

				}
			});

// Save Button Action =============================
			but2.setOnAction(event -> {

				FileWriter out = null;

				try {
					out = new FileWriter("src/Compression.txt", true);

					if (r1.isSelected()) {

						out.write("Data Saved -> Compression from: " + txt.getText() + " to: " + dst.getText() + "\n");

					} else {

						out.write("Data Saved -> Decompression from: " + txt.getText() + " to: " + dst.getText()
								+ "\n");

					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});

			// Layout ==========================================

			VBox vbox1 = new VBox(4);
			vbox1.setId("vbox1");
			VBox vbox2 = new VBox(2);
			vbox2.setId("vbox2");
			HBox hbox1 = new HBox(2);
			hbox1.setId("hbox1");

			root.setLeft(vbox1);
			vbox1.getChildren().add(label1);
			vbox1.getChildren().add(txt);
			vbox1.getChildren().add(label2);
			vbox1.getChildren().add(dst);

			root.setRight(vbox2);
			vbox2.getChildren().add(r1);
			vbox2.getChildren().add(r2);

			root.setCenter(hbox1);
			hbox1.getChildren().add(but1);
			hbox1.getChildren().add(but2);

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Launch ========================================
	public static void main(String[] args) {
		launch(args);

	}
}
