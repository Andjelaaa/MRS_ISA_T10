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
import javax.persistence.OneToMany;

@Entity
public class OcenaLekar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="lekar_id", unique=false, nullable=true)
	private int lekarId;
	
	@Column(name="pacijent_id", unique=false, nullable=true)
	private int pacijentId;
	
	@Column(name="ocena", unique=false, nullable=true)
	private int ocena;
	
	

	public OcenaLekar() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public int getLekarId() {
		return lekarId;
	}

	public void setLekarId(int lekarId) {
		this.lekarId = lekarId;
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
