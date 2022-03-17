package it.polito.tdp.prova;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	// private HashMap<String, String> elencoParoleTradotte = new HashMap<>(); ES1

	private HashMap<String, List<String>> elencoParole = new HashMap<String, List<String>>(); // ES2

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnTranslate;

	@FXML
	private Label labelAlien;

	@FXML
	private Label labelTitolo;

	@FXML
	private TextField txtToTranslate;

	@FXML
	private TextArea txtTradotto;

	/*
	 * @FXML ES1 void handleButtonAction(ActionEvent event) { boolean bool = false;
	 * String toTranslate = txtToTranslate.getText().toLowerCase(); for (char c :
	 * toTranslate.toCharArray()) { if ((c > 96 && c < 123) || (c > 64 && c < 91))
	 * bool = true; } if (bool && toTranslate.contains("> <")) {
	 * 
	 * int a = toTranslate.indexOf("<") + 1; int b = toTranslate.indexOf(">");
	 * 
	 * int c = toTranslate.lastIndexOf("<") + 1; int d =
	 * toTranslate.lastIndexOf(">");
	 * 
	 * String itaWord = toTranslate.substring(c, d); String alienWord =
	 * toTranslate.substring(a, b);
	 * 
	 * elencoParoleTradotte.put(alienWord, itaWord); txtTradotto.setText("Alien: " +
	 * alienWord + " = " + itaWord + " added to the dictionary."); } else {
	 * 
	 * int a = toTranslate.indexOf("<") + 1; int b = toTranslate.indexOf(">");
	 * 
	 * String alienWord = toTranslate.substring(a, b); String itaWord =
	 * elencoParoleTradotte.get(alienWord);
	 * 
	 * txtTradotto.setText("Traduzione di: " + alienWord + " = " + itaWord + "."); }
	 * }
	 */

	@FXML
	void handleButtonAction(ActionEvent event) {

		boolean bool = false;

		String toTranslate = txtToTranslate.getText().toLowerCase();

		for (char c : toTranslate.toCharArray()) {
			if ((c > 96 && c < 123) || (c > 64 && c < 91) || c == 63)
				bool = true;
		}

		if (bool) {

			if (toTranslate.contains("> <")) {

				int a = toTranslate.indexOf("<") + 1;
				int b = toTranslate.indexOf(">");

				int c = toTranslate.lastIndexOf("<") + 1;
				int d = toTranslate.lastIndexOf(">");

				String alienWord = toTranslate.substring(a, b);
				String itaWord = toTranslate.substring(c, d);

				if (elencoParole.containsKey(alienWord)) {
					elencoParole.get(alienWord).add(itaWord);
					txtTradotto.setText("Alien: " + alienWord + " = " + itaWord + " added to the dictionary."
							+ "\nThis word has " + elencoParole.get(alienWord).size() + " meaning/s");
				} else {
					LinkedList<String> elencoSignificatiParola = new LinkedList<>();
					elencoSignificatiParola.add(itaWord);
					elencoParole.put(alienWord, elencoSignificatiParola);
					txtTradotto.setText("Alien: " + alienWord + " = " + itaWord + " added to the dictionary."
							+ "\nThis word has " + elencoParole.get(alienWord).size() + " meaning/s");
				}
			} else {

				int a = toTranslate.indexOf("<") + 1;
				int b = toTranslate.indexOf(">");

				String alienWord = toTranslate.substring(a, b); // <Mar?o> becomes Mar?o

				if (!alienWord.contains("?")) {
					List<String> elencoTraduzioni = elencoParole.get(alienWord);
					if (elencoParole.get(alienWord) != null) {
						int i = 1;
						String toWrite = "This word has " + elencoTraduzioni.size() + " meaning/s";
						for (String p : elencoTraduzioni) {
							toWrite += "\n" + i + ") " + p + ";";
							i++;
						}
						txtTradotto.setText(toWrite);
					}
				}

				if (alienWord.contains("?")) {
					String toWrite = this.esOpzionale(alienWord);
					txtTradotto.setText(toWrite);
				}

				if (!alienWord.contains("?") && elencoParole.get(alienWord) == null) {
					txtTradotto.setText("No translations available");
				}
			}
		}
	}

	public String esOpzionale(String toFind) {

		List<String> keys = new ArrayList<>(elencoParole.keySet());

		String toWrite = ""; // final text for txtArea
		int m = 1;
		String[] textParts = toFind.split("\\?"); // Divide around ?: to?me = [ to, me]

		for (int i = 0; i < keys.size(); i++) { // Scroll all keys

			if (keys.get(i).startsWith(textParts[0]) && keys.get(i).endsWith(textParts[1])) { // Same as Key in position
																								// I
				toWrite += "Possible word: " + keys.get(i) + "\n"; // Add possible alien world

				for (String s : elencoParole.get(keys.get(i))) { // Scroll all translations
					toWrite += m + ") " + s + ";\n"; // Add "N) translationN; line by line
					m++;
				}
			}
		}
		return toWrite;
	}

	@FXML
	void initialize() {
		assert btnTranslate != null : "fx:id=\"btnTranslate\" was not injected: check your FXML file 'Scene.fxml'.";
		assert labelAlien != null : "fx:id=\"labelAlien\" was not injected: check your FXML file 'Scene.fxml'.";
		assert labelTitolo != null : "fx:id=\"labelTitolo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtToTranslate != null : "fx:id=\"txtToTranslate\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtTradotto != null : "fx:id=\"txtTradotto\" was not injected: check your FXML file 'Scene.fxml'.";
	}
}
