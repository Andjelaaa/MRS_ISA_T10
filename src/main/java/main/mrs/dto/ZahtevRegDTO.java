package main.mrs.dto;

import main.mrs.model.ZahtevReg;

public class ZahtevRegDTO {
	private Integer id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String kontakt;
	private String lbo;
	
	
	public ZahtevRegDTO() {
		
	}
	public ZahtevRegDTO(ZahtevReg z) {
		this(z.getEmail(), z.getLozinka(), z.getIme(), z.getPrezime(), z.getAdresa(), z.getGrad(), z.getDrzava(), z.getKontakt(), z.getLbo());
	}

	public ZahtevRegDTO(String email, String lozinka, String ime, String prezime, String adresa,
			String grad, String drzava, String kontakt, String lbo) {
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.kontakt = kontakt;
		this.lbo = lbo;
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
	public String getLbo() {
		return lbo;
	}
	public void setLbo(String lbo) {
		this.lbo = lbo;
	}
}