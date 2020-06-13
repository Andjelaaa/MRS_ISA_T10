package main.mrs.dto;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import main.mrs.model.Korisnik;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Odsustvo;
import main.mrs.model.Status;

public class OdsustvoDTO {
	private Integer id;
	private StatusDTO status;
	private String opis;
	private MedSestraDTO medSestra;
	private LekarDTO lekar;
	private Date pocetak;
	private Date kraj;
	private String tip;
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public OdsustvoDTO() {
		  
	}
	public OdsustvoDTO(Odsustvo s) {
		this(s.getId(), s.getKraj(),s.getOpis(), s.getPocetak(), s.getStatus(), s.getTip());
	}	
		
	public OdsustvoDTO(Integer id2, Date kraj2, String opis2, Date pocetak2,
			Status status2, String tip2) {
		this.id = id2;
		this.status = StatusDTO.valueOf(status2.toString());
		this.opis = opis2;
		this.medSestra =null;
		this.lekar = null;
		this.kraj = kraj2;
		this.pocetak = pocetak2;
		this.tip = tip2;
	}
	
	
	public static MedSestraDTO setujSestra(MedSestra sestra) {
		
		MedSestraDTO ms = new MedSestraDTO();
		System.out.println(sestra.getAdresa().toString()+"dasdasa");
		ms.setAdresa(sestra.getAdresa());
		ms.setDrzava(sestra.getDrzava());
		ms.setEmail(sestra.getEmail());
		ms.setGrad(sestra.getGrad());
		ms.setIme(sestra.getIme());
		ms.setPrezime(sestra.getPrezime());
		//ms.setKlinika(null);// treba da se namesti konvertor IZ DTO
		ms.setKontakt(sestra.getKontakt());
		ms.setLozinka(sestra.getLozinka());
		//ms.setRadKalendar(null);// i ovooooooooooo
		ms.setRadvr_kraj(sestra.getRadvr_kraj());
		ms.setRadvr_pocetak(sestra.getRadvr_pocetak());
		return ms;
	}
	public LekarDTO setujLekar(Lekar lekar) {
		LekarDTO le = new LekarDTO();
		le.setAdresa(lekar.getAdresa());
		le.setDrzava(lekar.getDrzava());
		le.setEmail(lekar.getEmail());
		le.setGrad(lekar.getGrad());
		le.setIme(lekar.getIme());
		le.setPrezime(lekar.getPrezime());
		le.setKlinika(null);// treba da se namesti konvertor IZ DTO
		le.setKontakt(lekar.getKontakt());
		le.setLozinka(lekar.getLozinka());
		//le.setRadKalendar(null);// i ovooooooooooo
		le.setRvKraj(lekar.getRvKraj());
		le.setRvPocetak(lekar.getRvPocetak());
		return le;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
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
	public MedSestraDTO getSestra() {
		return medSestra;
	}

	public void setSestra(MedSestraDTO sestra) {
		this.medSestra = sestra;
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}

	

}