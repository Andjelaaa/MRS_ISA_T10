package main.mrs.model;

public class Ocene {

	// pomocna klasa koja sadrzi ocene klinike i lekara
	public int ocenaLekar;
	public int ocenaKlinika;
	
	
	public Ocene() {
		super();
		this.ocenaKlinika = 0;
		this.ocenaLekar = 0;
	}


	public Ocene(int ocenaLekar, int ocenaKlinika) {
		super();
		this.ocenaLekar = ocenaLekar;
		this.ocenaKlinika = ocenaKlinika;
	}
	
	
}
