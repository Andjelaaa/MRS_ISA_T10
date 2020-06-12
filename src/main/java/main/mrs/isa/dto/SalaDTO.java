package main.mrs.isa.dto;

import java.util.*;

import main.mrs.model.Sala;



public class SalaDTO {
	private Integer id;
	private int broj;
	private String naziv;
	public KlinikaDTO klinika;
	public int izmena;

	public int getIzmena() {
		return izmena;
	}

	public void setIzmena(int izmena) {
		this.izmena = izmena;
	}

	public SalaDTO(Sala s) {
		this.id = s.getId();
		this.broj = s.getBroj();
		this.naziv = s.getNaziv();
		this.klinika = new KlinikaDTO();
	}
	
	public SalaDTO(String naziv, int broj) {
		this.naziv = naziv;
		this.broj = broj;
		
	}
	
	public SalaDTO() {}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}

	public Set<PregledDTO> pregled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<PregledDTO> getPregled() {
		return pregled;
	}

	public void setPregled(Set<PregledDTO> pregled) {
		this.pregled = pregled;
	}

}