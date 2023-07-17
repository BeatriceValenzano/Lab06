package it.polito.tdp.meteo.model;

public class Rilevamento {
	
	private String localita;
	private int umidita;
	private int giorno;

	public Rilevamento(String localita, int umidita, int giorno) {
		this.localita = localita;
		this.umidita = umidita;
		this.giorno = giorno;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setData(int giorno) {
		this.giorno = giorno;
	}

	public int getUmidita() {
		return umidita;
	}

	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}

	// @Override
	// public String toString() {
	// return localita + " " + data + " " + umidita;
	// }

	@Override
	public String toString() {
		return String.valueOf(umidita);
	}

	

}
