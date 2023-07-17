/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	
    	txtResult.clear();
    	if(verificaSelezione(this.boxMese.getValue()) == true) {
    		txtResult.setText(model.trovaSequenza(this.boxMese.getValue()));
    	} else {
    		txtResult.setText("Selezionare un mese di cui sapere l'umidità!");
    	}
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	
    	txtResult.clear();
    	if(verificaSelezione(this.boxMese.getValue()) == true) {
    		txtResult.setText(model.getUmiditaMedia(this.boxMese.getValue()));
    	} else {
    		txtResult.setText("Selezionare un mese di cui sapere l'umidità!");
    	}
    }
    
    private boolean verificaSelezione(Integer mese) {
    	if(mese!=null) 
    		return true;
    	return false;
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        for(int i=1; i<=12; i++) {
        	this.boxMese.getItems().add(i);
        }
    }

	public void setModel(Model model) {
		this.model = model;
	}
}

