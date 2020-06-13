package main.mrs.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class StavkaCenovnika {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="cena", unique=false, nullable=false)
	private double cena;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	public TipPregleda tipPregleda;

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public Cenovnik cenovnik;
	
	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	

}