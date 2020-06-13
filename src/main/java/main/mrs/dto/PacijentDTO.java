package main.mrs.dto;

import java.util.*;

import main.mrs.model.Pacijent;

public class PacijentDTO extends KorisnikDTO {
	private String lbo;
	public ZKartonDTO zKarton;
	public Set<PregledDTO> pregled;
	private boolean aktivan;
	
	public PacijentDTO() {}
	public PacijentDTO(Pacijent p)
	{
		// podesiti atribute nadklase
		this.ime = p.getIme();
		this.prezime = p.getPrezime();
		this.adresa = p.getAdresa();
		this.drzava = p.getDrzava();
		this.email = p.getEmail();
		this.grad = p.getGrad();
		this.id = p.getId();
		this.kontakt = p.getKontakt();
		this.lozinka = p.getLozinka();
		this.zKarton = new ZKartonDTO(p.getzKarton()); // PROMENITI
		this.pregled = new java.util.HashSet<PregledDTO>();
		this.lbo = p.getLbo();
		this.aktivan = false;
		this.promenioLozinku = p.isPromenioLozinku();
		//this.zKarton = new ZKartonDTO(p.zKarton);
		
	}

	public String getLbo() {
		return lbo;
	}

	public void setLbo(String lbo) {
		this.lbo = lbo;
	}

	public Set<PregledDTO> getPregled() {
		if (pregled == null)
			pregled = new java.util.HashSet<PregledDTO>();
		return pregled;
	}

	public void setPregled(Set<PregledDTO> newPregled) {
		this.pregled = newPregled;
	}

	public void addPregled(PregledDTO newPregled) {
		if (newPregled == null)
			return;
		if (this.pregled == null)
			this.pregled = new java.util.HashSet<PregledDTO>();
		if (!this.pregled.contains(newPregled))
			this.pregled.add(newPregled);
	}

	public void removePregled(PregledDTO oldPregled) {
		if (oldPregled == null)
			return;
		if (this.pregled != null)
			if (this.pregled.contains(oldPregled))
				this.pregled.remove(oldPregled);
	}

	public void removeAllPregled() {
		if (pregled != null)
			pregled.clear();
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

}