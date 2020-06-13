package main.mrs.dto;

import main.mrs.model.Lek;


public class LekDTO {
	private Integer id;
	private String sifra;
	private String naziv;

	
	public LekDTO() {
		
	}
	public LekDTO(Lek z) {
		this(z.getNaziv(), z.getSifra());
	}
	public LekDTO(String naziv2, String sifra2) {
		this.sifra =sifra2;
		this.naziv = naziv2;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}