package main.mrs.isa.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
@Entity
public class Sala {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="broj", unique=true, nullable=false)
	private int broj;
	
	@Column(name="naziv", unique=true, nullable=false)
	private String naziv;

	@OneToMany(mappedBy="sala",fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	public Set<Pregled> pregled;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	public Klinika klinika;
	
	@Version
	private Long version;
	
	@Column(name="izmena", unique=false, nullable=true)
	private int izmena;
	
	public int getIzmena() {
		return izmena;
	}
	public void setIzmena(int izmena) {
		this.izmena = izmena;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

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

	public Set<Pregled> getPregled() {
		return pregled;
	}

	public void setPregled(Set<Pregled> pregled) {
		this.pregled = pregled;
	}

}