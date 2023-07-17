package it.polito.tdp.meteo.model;

import java.util.*;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	MeteoDAO meteoDAO;
	List<String> parziale;
	List<String> citta;
	List<Rilevamento> rilevamenti;   //variabile globale quindi la posso inizializzare dove voglio e avrò comunque la disponibilità ovunque
	List<String> soluzioneMigliore = new LinkedList<String>();
	double costoMigliore = Double.MAX_VALUE;  //sarà il parziale più grande avuto
	
	public Model() {
		meteoDAO = new MeteoDAO();
		citta = meteoDAO.getCitta();
		}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		
		String s = meteoDAO.getUmiditaMedia(mese);
				
		return s;
	}
	
	public boolean controlloSequenza(List<String> parziale) {
		
		for(String c : citta) {
			int cnt = 0;
			for(String s : parziale) {
				if(s.toLowerCase().equals(c.toLowerCase())) {
					cnt++;
				}
				if(cnt>NUMERO_GIORNI_CITTA_MAX) {
					return false;
				}
			}
		}
		
		for(String c : citta) {
			if(!parziale.contains(c)) {
				return false;
			}
		}
		
		String nomeCitta = parziale.get(0);
		int cntCitta3 = 0;
		
		for(String s : parziale) {
			if(s.toLowerCase().equals(nomeCitta.toLowerCase())) {
				cntCitta3++;
			} else {
				if(cntCitta3<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
					return false;
				}
				else {
					cntCitta3 = 1;
					nomeCitta = s;
				}
			}
		}
		
		double costo = costo(parziale);
		if(costo>costoMigliore) {
			return false;
		} else {
			costoMigliore = costo;
		}
		
		return true;
	}
	
	public double costo(List<String> parziale) {
		
		double costo = 0.0;
		String precedente = parziale.get(0);
		for(int i=0; i<parziale.size(); i++) {
			if(!parziale.get(i).equals(precedente)) {
				precedente = parziale.get(i);
				costo += COST;
			}
			for(Rilevamento r : rilevamenti) {
				if(r.getGiorno()==i+1 && r.getLocalita().equals(parziale.get(i))) {
					costo += r.getUmidita();
				}
			}
		}
		return costo;
	}
	
	public void cercaSequenza (List<String> parziale) {
		
//		condizione di terminazione 
		if(parziale.size() == NUMERO_GIORNI_TOTALI) {   //non metto questi due in uno stesso if perchè alla prima iterazione controlloSequenza(parziale) mi darà false senza entrare dunque mi aggiungerà il 16esimo
			if(controlloSequenza(parziale)) {    //non posso metterli con un || poichè me li stamperebbe tutti invece devono essere verificate entrambe
				soluzioneMigliore.clear();    //la ripulisco perchè sennò mi aggiungerebbe alle 15 città messe prime le nuove 15 città in sequenza migliori
				soluzioneMigliore.addAll(parziale);    //aggiungo parziale in soluzione migliore poichè ha costo migliore della precedente
//				System.out.println(soluzioneMigliore);
			}
			return;
		}
		
		for(String c : citta) {
			if(c!=null) {
				parziale.add(c);
				cercaSequenza(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {   //metodo che mi chiama la ricorsione
		
		parziale = new LinkedList<String>();
		rilevamenti = meteoDAO.getAllRilevamentiLocalitaMese(mese);
 		cercaSequenza(parziale);
 		String result = "";
 		for(String s : soluzioneMigliore) {
 			result += s + "; ";
 		}
 		result += "\n" + costoMigliore;		
 		return result;
	}
	
	
}
