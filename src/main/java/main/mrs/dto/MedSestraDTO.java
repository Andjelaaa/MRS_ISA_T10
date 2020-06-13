package main.mrs.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import main.mrs.model.MedSestra;
import main.mrs.model.Odsustvo;


public class MedSestraDTO extends KorisnikDTO {
   public Set<OdsustvoDTO> odsustvo;
   public KlinikaDTO klinika;
   private String radvr_pocetak;
   private String radvr_kraj;
   public MedSestraDTO() {
	
	}
	
   public MedSestraDTO(MedSestra s) {
	   this(s.getOdsustvo(),s.getId(), s.getEmail(), s.getLozinka(), s.getIme(), s.getPrezime(), s.getAdresa(), s.getGrad(),
				s.getDrzava(), s.getKontakt(), s.getRadvr_kraj(), s.getRadvr_pocetak(), s.isPromenioLozinku());
	}
	
	
	
	public MedSestraDTO(Set<Odsustvo> set, Integer integer, String email, String lozinka, String ime, String prezime, String adresa, 
			String grad, String drzava, String kontakt, String kraj, String pocetak, boolean promenio) {
		this.odsustvo = konvertuj(set);
		this.id = integer;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.kontakt = kontakt;
		this.drzava = drzava;
		this.radvr_kraj= kraj;
		this.radvr_pocetak = pocetak;
		this.promenioLozinku = promenio;
	}


	private Set<OdsustvoDTO> konvertuj(Set<Odsustvo> set) {
		 Set<OdsustvoDTO> novi = new  HashSet<OdsustvoDTO>();
		 for(Odsustvo o: set) {
			 OdsustvoDTO od = new OdsustvoDTO(o);

			 novi.add(od);
		 }
		return novi;
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	
	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
	
	

	public Set<OdsustvoDTO> getOdsustvo() {
		return odsustvo;
	}


	public void setOdsustvo(Set<OdsustvoDTO> odsustvo) {
		this.odsustvo = odsustvo;
	}


	public String getRadvr_pocetak() {
		return radvr_pocetak;
	}


	public void setRadvr_pocetak(String radvr_pocetak) {
		this.radvr_pocetak = radvr_pocetak;
	}


	public String getRadvr_kraj() {
		return radvr_kraj;
	}


	public void setRadvr_kraj(String radvr_kraj) {
		this.radvr_kraj = radvr_kraj;
	}
	
	
	
}