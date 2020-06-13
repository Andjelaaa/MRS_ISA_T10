package main.mrs.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
@Entity
@Table(name="ODSUSTVO")
public class Odsustvo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="status", unique=false, nullable=true)
	private Status status;
	@Column(name="tip", unique=false, nullable=true)
	private String tip;
	@Column(name="opis", unique=false, nullable=true)
	private String opis;
	
	@Column(name="pocetak", unique=false, nullable=false)
	private Date pocetak;
	
	@Column(name="kraj", unique=false, nullable=false)
	private Date kraj;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private MedSestra medSestra;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Lekar lekar;
	
	@Version
	private Long version;
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

	public MedSestra getSestra() {
		return medSestra;
	}

	public void setSestra(MedSestra m) {
		
		MedSestra ms = new MedSestra();
		
		ms.setAdresa(m.getAdresa());
		
		ms.setDrzava(m.getDrzava());
		
		ms.setEmail(m.getEmail());
	
		ms.setGrad(m.getGrad());
	
		ms.setIme(m.getIme());
		
		ms.setPrezime(m.getPrezime());
	
		//ms.setKlinika(null);// treba da se namesti konvertor IZ DTO
		ms.setKontakt(m.getKontakt());
		
		ms.setLozinka(m.getLozinka());
	
	//	ms.setRadKalendar(null);// i ovooooooooooo
		ms.setRadvr_kraj(m.getRadvr_kraj());
		
		ms.setRadvr_pocetak(m.getRadvr_pocetak());
		
	
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar l) {
		Lekar le = new Lekar();
		le.setAdresa(l.getAdresa());
		le.setDrzava(l.getDrzava());
		le.setEmail(l.getEmail());
		le.setGrad(l.getGrad());
		le.setIme(l.getIme());
		le.setPrezime(l.getPrezime());
		//le.setKlinika(null);// treba da se namesti konvertor IZ DTO
		le.setKontakt(l.getKontakt());
		le.setLozinka(l.getLozinka());
		//le.setRadKalendar(null);// i ovooooooooooo
		le.setRvKraj(l.getRvKraj());
		le.setRvPocetak(l.getRvPocetak());

	}

}