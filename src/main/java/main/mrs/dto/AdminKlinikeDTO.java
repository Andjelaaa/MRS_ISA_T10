package main.mrs.dto;
import java.util.*;

import main.mrs.model.AdminKlinike;

public class AdminKlinikeDTO extends KorisnikDTO {
   public Set<OdsustvoDTO> odsustvo;
   public KlinikaDTO klinika;
   
   
   public AdminKlinikeDTO() {}
   
   public AdminKlinikeDTO(AdminKlinike s) {
	   this(s.getId(), s.getEmail(), s.getLozinka(), s.getIme(), s.getPrezime(), s.getAdresa(), s.getGrad(),
				s.getDrzava(), s.getKontakt(), s.isPromenioLozinku());
   }
   
      
   public AdminKlinikeDTO(Integer id, String email, String lozinka, String ime, String prezime, String adresa, String grad,
		String drzava, String kontakt, boolean promenio) {
	this.id = id;
	this.email = email;
	this.lozinka = lozinka;
	this.ime = ime;
	this.prezime = prezime;
	this.adresa = adresa;
	this.grad = grad;
	this.drzava = drzava;
	this.kontakt = kontakt;
	this.promenioLozinku = promenio;
}

public Set<OdsustvoDTO> getOdsustvo() {
      if (odsustvo == null)
         odsustvo = new HashSet<OdsustvoDTO>();
      return odsustvo;
   }
   
   public void setOdsustvo(Set<OdsustvoDTO> newOdsustvo) {
      this.odsustvo = newOdsustvo;
   }
   
   public void addOdsustvo(OdsustvoDTO newOdsustvo) {
      if (newOdsustvo == null)
         return;
      if (this.odsustvo == null)
         this.odsustvo = new HashSet<OdsustvoDTO>();
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
   public KlinikaDTO getKlinika() {
      return klinika;
   }
   
   public void setKlinika(KlinikaDTO newKlinika) {
      if (this.klinika == null || !this.klinika.equals(newKlinika))
      {
         if (this.klinika != null)
         {
            KlinikaDTO oldKlinika = this.klinika;
            this.klinika = null;
            oldKlinika.removeAdminKlinike(this);
         }
         if (newKlinika != null)
         {
            this.klinika = newKlinika;
            this.klinika.addAdminKlinike(this);
         }
      }
   }

}