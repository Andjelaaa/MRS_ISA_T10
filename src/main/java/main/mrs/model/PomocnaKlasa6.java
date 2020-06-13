package main.mrs.model;

import java.util.ArrayList;

public class PomocnaKlasa6 {
	// klasa za prewtragu slobodnnih lekara
	public String ime;
	public String prezime;
	public int ocena;
	public ArrayList<PomocnaKlasa5> lekariTermini;
	
	
	public PomocnaKlasa6() {
		super();
		this.lekariTermini = new ArrayList<PomocnaKlasa5>();
	}


	public PomocnaKlasa6(String ime, String prezime, int ocena, ArrayList<PomocnaKlasa5> lekariTermini) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.ocena = ocena;
		this.lekariTermini = lekariTermini;
	}


	
	
	
}
