package main.mrs.dto;
import java.util.*;

import javax.persistence.Column;

import main.mrs.model.Lek;
import main.mrs.model.MedSestra;
import main.mrs.model.Recept;

public class ReceptDTO {
   private Integer id;
   public MedSestraDTO medSestra;
   public Set<LekDTO> lek;
   public String imePacijenta;
   public String prezimePacijenta;
   private Long version;

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getImePacijenta() {
			return imePacijenta;
	}
	public void setImePacijenta(String imePacijenta) {
		this.imePacijenta = imePacijenta;
	}
	public String getPrezimePacijenta() {
		return prezimePacijenta;
	}
	public void setPrezimePacijenta(String prezimePacijenta) {
		this.prezimePacijenta = prezimePacijenta;
	}
   public ReceptDTO() {
		
	}
   public ReceptDTO(Recept r) {
		this(r.getId(), r.getLek());
	}
	
	public ReceptDTO(Integer id2, Set<Lek> lek2) {
		this.id = id2;
		this.lek = konvertuj(lek2);
		this.medSestra = null;
		this.imePacijenta = null;
		this.prezimePacijenta = null;
		
	}
	
	private Set<LekDTO> konvertuj(Set<Lek> lek2) {
		Set<LekDTO> novi = new HashSet<LekDTO>();
		for(Lek l: lek2) {
			LekDTO ld = new LekDTO();
			ld.setNaziv(l.getNaziv());
			ld.setId(l.getId());
			ld.setSifra(l.getSifra());
			novi.add(ld);
		}
		return novi;
	}

	public MedSestraDTO getMedSestra() {
		return medSestra;
	}

	public void setMedSestra(MedSestraDTO medSestra) {
		this.medSestra = medSestra;
	}

	public Integer getId() {
			return id;
		}

	public void setId(Integer id) {
		this.id = id;
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

}