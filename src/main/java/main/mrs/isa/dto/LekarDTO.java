package main.mrs.isa.dto;
import java.util.HashSet;
import java.util.Set;

import main.mrs.model.Lekar;
import main.mrs.model.Odsustvo;
import main.mrs.model.Operacija;
import main.mrs.model.Pregled;
import main.mrs.model.TipPregleda;



/** Lekar ima recnik pregleda, kljuc je datum, vrednost pregled
 * 
 *  */
public class LekarDTO extends KorisnikDTO {
   private Double prosecnaOcena;
   private int brojOcena;
   
   // mozda promeniti
   private String rvPocetak;
   private String rvKraj;
   
   // specijalizacija
   public TipPregledaDTO tipPregleda;
  
   public KlinikaDTO klinika;
   public Set<OdsustvoDTO> odsustvo;
   private int izmenaRezervisanja;
   private Long version;
   
   public LekarDTO() {}
   
   public LekarDTO(Lekar s) {
		this(s.getId(), s.getEmail(), s.getLozinka(), s.getIme(), s.getPrezime(), s.getAdresa(), s.getGrad(),
				s.getDrzava(), s.getRvPocetak(), s.getRvKraj(), s.getKontakt(), s.getProsecnaOcena(), s.getBrojOcena(),
				s.getOdsustvo(), s.isPromenioLozinku());
		this.tipPregleda = new TipPregledaDTO(s.getTipPregleda());
	}


	public LekarDTO(Integer id, String email, String lozinka, String ime, String prezime, String adresa, String grad,
			String drzava, String rvPoc, String rvKraj, String Kontakt, Double po, int bo, Set<Odsustvo> odsustv, boolean promenio) {
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.prosecnaOcena = po;
		this.brojOcena = bo;
		//this.tipPregleda = new TipPregledaDTO(); 
		this.rvPocetak = rvPoc;
		this.rvKraj = rvKraj;
		this.odsustvo = konvertuj(odsustv);
		this.klinika = new KlinikaDTO();
		this.kontakt = Kontakt;
		this.promenioLozinku = promenio;
	}
   
 
	private Set<OdsustvoDTO> konvertuj(Set<Odsustvo> odsustv) {
		   Set<OdsustvoDTO> novi = new  HashSet<OdsustvoDTO>();
			 for(Odsustvo o: odsustv) {
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
      if (odsustvo == null)
         odsustvo = new java.util.HashSet<OdsustvoDTO>();
      return odsustvo;
   }
   
   public void setOdsustvo(Set<OdsustvoDTO> newOdsustvo) {
      this.odsustvo = newOdsustvo;
   }
   
   public void addOdsustvo(OdsustvoDTO newOdsustvo) {
      if (newOdsustvo == null)
         return;
      if (this.odsustvo == null)
         this.odsustvo = new java.util.HashSet<OdsustvoDTO>();
      if (!this.odsustvo.contains(newOdsustvo))
         this.odsustvo.add(newOdsustvo);
   }
   
   public void removeOdsustvo(OdsustvoDTO oldOdsustvo) {
      if (oldOdsustvo == null)
         return;
      if (this.odsustvo != null)
         if (this.odsustvo.contains(oldOdsustvo))
            this.odsustvo.remove(oldOdsustvo);
   }
   
   public void removeAllOdsustvo() {
      if (odsustvo != null)
         odsustvo.clear();
   }

public Double getProsecnaOcena() {
	return prosecnaOcena;
}

public void setProsecnaOcena(Double prosecnaOcena) {
	this.prosecnaOcena = prosecnaOcena;
}

public int getBrojOcena() {
	return brojOcena;
}

public void setBrojOcena(int brojOcena) {
	this.brojOcena = brojOcena;
}

public String getRvPocetak() {
	return rvPocetak;
}

public void setRvPocetak(String rvPocetak) {
	this.rvPocetak = rvPocetak;
}

public String getRvKraj() {
	return rvKraj;
}

public void setRvKraj(String rvKraj) {
	this.rvKraj = rvKraj;
}

public TipPregledaDTO getTipPregleda() {
	return tipPregleda;
}

public void setTipPregleda(TipPregledaDTO tipPregleda) {
	this.tipPregleda = tipPregleda;
}

public Long getVersion() {
	return version;
}

public void setVersion(Long version) {
	this.version = version;
}

public int getIzmenaRezervisanja() {
	return izmenaRezervisanja;
}

public void setIzmenaRezervisanja(int izmenaRezervisanja) {
	this.izmenaRezervisanja = izmenaRezervisanja;
}



}