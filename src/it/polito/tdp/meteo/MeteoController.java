package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {
	private List<Integer> mesi=new ArrayList<Integer>();
	
	ObservableList<Integer> mounthBoxList = FXCollections.observableArrayList(this.riempilistamesi(mesi)); 

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		Model modello=new Model();
		int mese;
		mese=boxMese.getValue();
		String risultato=modello.trovaSequenza(mese);
		txtResult.appendText(risultato);
		

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		Model modello=new Model();
		int mese;
		mese=boxMese.getValue();
		txtResult.appendText(modello.getUmiditaMedia(mese));
		

	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		boxMese.setItems(mounthBoxList);
		
	}
	public List<Integer> riempilistamesi(List lista) {
		int i;
		for(i=1;i<=12;i++)
			lista.add(i);
		return lista;
	}

}
