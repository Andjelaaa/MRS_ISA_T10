package main.mrs.isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class OcenaKlinika {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="klinika_id", unique=false, nullable=true)
	private int klinikaId;
	
	@Column(name="pacijent_id", unique=false, nullable=true)
	private int pacijentId;
	
	@Column(name="ocena", unique=false, nullable=true)
	private int ocena;

	public OcenaKlinika() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public int getKlinikaId() {
		return klinikaId;
	}

	public void setKlinikaId(int klinikaId) {
		this.klinikaId = klinikaId;
	}

	public int getPacijentId() {
		return pacijentId;
	}

	public void setPacijentId(int pacijentId) {
		this.pacijentId = pacijentId;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	
	
}
