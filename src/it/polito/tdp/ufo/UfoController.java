/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Anno> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAnalizza(ActionEvent event) {
    	String state = boxStato.getValue();
    	if(state == null) {
    		txtResult.appendText("Si deve selezionare uno stato\n");
    	}
    	List<String> predecessori = this.model.getPrecedenti(state);
    	txtResult.appendText("PREDECESSORI:\n");
    	for(String s: predecessori) {
    		txtResult.appendText(s + "\n");
    	}
    	List<String> successori = this.model.getSuccessori(state);
    	txtResult.appendText("SUCCESSORI:\n");
    	for(String s: successori) {
    		txtResult.appendText(s + "\n");
    	}
    	txtResult.appendText("STATI RAGGIUNGIBILI:\n");
    	for(String s: this.model.getRaggiungibili(state)) {
    		txtResult.appendText(s + "\n");
    	}
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	Integer anno = boxAnno.getValue().getAnno();
    	if(anno == null) {
    		txtResult.appendText("Si deve selezionare un anno\n");
    	}
    	this.model.creaGrafo(anno);
    	txtResult.appendText("Grafo creato con "+ this.model.getVertici()+ " vertici e "+ this.model.getArchi()+ " archi\n");
    	boxStato.getItems().addAll(this.model.getStati());
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(this.model.getAnni());
    }
    
}
