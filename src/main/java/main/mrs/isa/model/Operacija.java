package main.mrs.isa.model;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.Version;
@Entity
public class Operacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="datumVreme", unique=false, nullable=false)
    private Date datumVreme;
	@Column(name="trajanje", unique=false, nullable=false)
    private int trajanje;
	@Column(name="status", unique=false, nullable=false)
    private Status status;
   
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public StavkaCenovnika stavkaCenovnika;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public Sala sala;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	//@JoinColumn(name="operacija_id", nullable=false)
    public Set<Lekar> lekar;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name="pacijent_id", nullable=true)
	public Pacijent pacijent;
	
	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public StavkaCenovnika getStavkaCenovnika() {
		return stavkaCenovnika;
	}

	public void setStavkaCenovnika(StavkaCenovnika stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

public Date getDatumVreme() {
	return datumVreme;
}

public void setDatumVreme(Date datumVreme) {
	this.datumVreme = datumVreme;
}

public int getTrajanje() {
	return trajanje;
}

public void setTrajanje(int trajanje) {
	this.trajanje = trajanje;
}

public Status getStatus() {
	return status;
}

public void setStatus(Status status) {
	this.status = status;
}

public Set<Lekar> getLekar() {
	return lekar;
}

public void setLekar(Set<Lekar> lekar) {
	this.lekar = lekar;
}


}