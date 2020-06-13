package main.mrs.dto;
import java.util.*;

import main.mrs.model.ZKarton;

public class ZKartonDTO {
	private Integer id;
   private String krvnaGrupa;
   private Double visina;
   private Double tezina;
   private Double dioptrija;
   private String pol;
   private Date datumRodjenja;
   
   public Set<PregledDTO> pregled;
   public Set<LekDTO> lek;
   public Set<OperacijaDTO> operacija;
   
   public ZKartonDTO(ZKarton zk)
   {
	   this.datumRodjenja = zk.getDatumRodjenja();
	   this.dioptrija = zk.getDioptrija();
	   this.id = zk.getId();
	   this.krvnaGrupa = zk.getKrvnaGrupa();
	   this.lek = new java.util.HashSet<LekDTO>(); // ovo mora drugacije
	   this.operacija = new java.util.HashSet<OperacijaDTO>(); // i ovo
	   this.pol = zk.getPol();
	   this.pregled = new java.util.HashSet<PregledDTO>(); // i ovo
	   this.tezina = zk.getTezina();
	   this.visina = zk.getVisina();
   }
   
   public ZKartonDTO() {
	// TODO Auto-generated constructor stub
}

public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   public Set<PregledDTO> getPregled() {
      if (pregled == null)
         pregled = new java.util.HashSet<PregledDTO>();
      return pregled;
   }
   
   public void setPregled(Set<PregledDTO> newPregled) {
      this.pregled = newPregled;
   }
   
   public void addPregled(PregledDTO newPregled) {
      if (newPregled == null)
         return;
      if (this.pregled == null)
         this.pregled = new java.util.HashSet<PregledDTO>();
      if (!this.pregled.contains(newPregled))
         this.pregled.add(newPregled);
   }
   
   public void removePregled(PregledDTO oldPregled) {
      if (oldPregled == null)
         return;
      if (this.pregled != null)
         if (this.pregled.contains(oldPregled))
            this.pregled.remove(oldPregled);
   }
   
   public void removeAllPregled() {
      if (pregled != null)
         pregled.clear();
   }
   public Set<LekDTO> getLek() {
      if (lek == null)
         lek = new java.util.HashSet<LekDTO>();
      return lek;
   }
   public void setLek(Set<LekDTO> newLek) {
      this.lek = newLek;
   }
   
   public void addLek(LekDTO newLek) {
      if (newLek == null)
         return;
      if (this.lek == null)
         this.lek = new java.util.HashSet<LekDTO>();
      if (!this.lek.contains(newLek))
         this.lek.add(newLek);
   }
   
   public void removeLek(LekDTO oldLek) {
      if (oldLek == null)
         return;
      if (this.lek != null)
         if (this.lek.contains(oldLek))
            this.lek.remove(oldLek);
   }
   
   public void removeAllLek() {
      if (lek != null)
         lek.clear();
   }
   public Set<OperacijaDTO> getOperacija() {
      if (operacija == null)
         operacija = new java.util.HashSet<OperacijaDTO>();
      return operacija;
   }

   
   public void setOperacija(Set<OperacijaDTO> newOperacija) {
      this.operacija = newOperacija;
   }
   
   public void addOperacija(OperacijaDTO newOperacija) {
      if (newOperacija == null)
         return;
      if (this.operacija == null)
         this.operacija = new java.util.HashSet<OperacijaDTO>();
      if (!this.operacija.contains(newOperacija))
         this.operacija.add(newOperacija);
   }
   
   public void removeOperacija(OperacijaDTO oldOperacija) {
      if (oldOperacija == null)
         return;
      if (this.operacija != null)
         if (this.operacija.contains(oldOperacija))
            this.operacija.remove(oldOperacija);
   }
   
   public void removeAllOperacija() {
      if (operacija != null)
         operacija.clear();
   }

public String getKrvnaGrupa() {
	return krvnaGrupa;
}

public void setKrvnaGrupa(String krvnaGrupa) {
	this.krvnaGrupa = krvnaGrupa;
}

public Double getVisina() {
	return visina;
}

public void setVisina(Double visina) {
	this.visina = visina;
}

public Double getTezina() {
	return tezina;
}

public void setTezina(Double tezina) {
	this.tezina = tezina;
}

public Double getDioptrija() {
	return dioptrija;
}

public void setDioptrija(Double dioptrija) {
	this.dioptrija = dioptrija;
}

public String getPol() {
	return pol;
}

public void setPol(String pol) {
	this.pol = pol;
}

public Date getDatumRodjenja() {
	return datumRodjenja;
}

public void setDatumRodjenja(Date datumRodjenja) {
	this.datumRodjenja = datumRodjenja;
}

}