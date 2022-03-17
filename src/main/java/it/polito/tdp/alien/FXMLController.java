package it.polito.tdp.alien;

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
			if ((c > 96 && c < 123) || (c > 64 && c < 91))
				bool = true;
		}
		if (bool && toTranslate.contains("> <")) {

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

			String alienWord = toTranslate.substring(a, b);
			List<String> elencoTraduzioni = elencoParole.get(alienWord);
			if (elencoTraduzioni != null) {
				int i = 1;
				String toWrite = "This word has " + elencoTraduzioni.size() + " meaning/s";
				for (String p : elencoTraduzioni) {
					toWrite += "\n" + i + ") " + p + ";";
					i++;
				}
				txtTradotto.setText(toWrite);
			} else {
				txtTradotto.setText("No translations available");
			}
		}
	}

	public void esOpzionale() {

		String keySet = elencoParole.entrySet().toString(); // tutte
		String keyTot = keySet.substring(1, (keySet.length() - 1)); // senza le quadre
		String[] keysTemp = keyTot.split(", "); // key=List in ogni cella
		ArrayList<String> keys = new ArrayList<>(); // elenco chiavi
		String toWrite = ""; // Testo da mettere nella txtArea a fine metodo
		for (int i = 0; i < keysTemp.length; i++) {
			keys.add(keysTemp[i].split("=")[0]);
		}
		// Da qui keys contiene tutte le chiavi della mappa

		String toFind = txtToTranslate.getText(); // Da tradurre
		String[] textParts = toFind.split("?"); // Divido la parola da tradurre Ma?io = [ Ma, io]
		
		for (int i = 0; i < keys.size(); i++) { //Scorro tutte le chiavi

			if (keys.get(i).startsWith(textParts[0]) && keys.get(i).endsWith(textParts[1])) { //Coincide con keys in posizione I
				
				toWrite += "Possible word: " + keys.get(i) + "\n"; //Aggiungo la possibile parola aliena
				
				for (String s : elencoParole.get(keys.get(i))) { //Scorro tutte le sue traduzioni
					toWrite += ") " + s + ";\n"; //Aggiungo "N) traduzioneN; riga per riga
				}
			}
		}
		txtTradotto.setText(toWrite);
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
