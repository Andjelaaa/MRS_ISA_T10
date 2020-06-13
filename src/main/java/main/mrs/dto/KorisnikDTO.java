package main.mrs.dto;

import main.mrs.model.Korisnik;

public class KorisnikDTO {
	protected Integer id;
	protected String email;
	protected String lozinka;
	protected String ime;
	protected String prezime;
	protected String adresa;
	protected String grad;
	protected String drzava;
	protected String kontakt;
	protected boolean promenioLozinku;

	public KorisnikDTO() {}
	public KorisnikDTO(Korisnik p) {
		this.ime = p.getIme();
		this.prezime = p.getPrezime();
		this.adresa = p.getAdresa();
		this.drzava = p.getDrzava();
		this.email = p.getEmail();
		this.grad = p.getGrad();
		this.id = p.getId();
		this.kontakt = p.getKontakt();
		this.lozinka = p.getLozinka();
		this.promenioLozinku = p.isPromenioLozinku();
	}

	public boolean isPromenioLozinku() {
		return promenioLozinku;
	}
	public void setPromenioLozinku(boolean promenioLozinku) {
		this.promenioLozinku = promenioLozinku;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getKontakt() {
		return kontakt;
	}

	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}

}