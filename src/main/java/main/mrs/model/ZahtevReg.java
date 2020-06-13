package main.mrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZahtevRegistracije")

public class ZahtevReg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="email", unique=true, nullable=false)
	private String email;
	@Column(name="lozinka", unique=false, nullable=false)
	private String lozinka;
	@Column(name="ime", unique=false, nullable=false)
	private String ime;
	@Column(name="prezime", unique=false, nullable=false)
	private String prezime;
	@Column(name="adresa", unique=false, nullable=false)
	private String adresa;
	@Column(name="grad", unique=false, nullable=false)
	private String grad;
	@Column(name="drzava", unique=false, nullable=false)
	private String drzava;
	@Column(name="kontakt", unique=true, nullable=false)
	private String kontakt;
	@Column(name="lbo", unique=true, nullable=false)
	private String lbo;
	
	public ZahtevReg() {}

	public ZahtevReg(String email, String lozinka, String ime, String prezime, String adresa, String grad,
			String drzava, String kontakt, String lbo) {
		
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