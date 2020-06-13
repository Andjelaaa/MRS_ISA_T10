package main.mrs.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import main.mrs.dto.LekDTO;
@Entity
public class Recept {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name="med_sestra", nullable=true)
    public MedSestra medSestra;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public Set<Lek> lek;
	
	@Column(name="imePacijenta", unique=false, nullable=false)
	public String imePacijenta;

	@Column(name="prezimePacijenta", unique=false, nullable=false)
	public String prezimePacijenta;
   
	@Version
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
	public MedSestra getMedSestra() {
		return medSestra;
	}
	public static Set<Lek> changeDTO(Set<LekDTO> lek2) {
		Set<Lek> novi = new HashSet<Lek>();
		for(LekDTO l: lek2) {
			Lek ld = new Lek();
			ld.setNaziv(l.getNaziv());
			ld.setId(l.getId());
			ld.setSifra(l.getSifra());
			novi.add(ld);
		}
		return novi;
	}

	public void setMedSestra(MedSestra medSestra) {
		this.medSestra = medSestra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   
   public Set<Lek> getLek() {
      if (lek == null)
         lek = new java.util.HashSet<Lek>();
      return lek;
   }
   
   public void setLek(Set<Lek> newLek) {
      this.lek = newLek;
   }
   
   public void addLek(Lek newLek) {
      if (newLek == null)
         return;
      if (this.lek == null)
         this.lek = new java.util.HashSet<Lek>();
      if (!this.lek.contains(newLek))
         this.lek.add(newLek);
   }
   
   public void removeLek(Lek oldLek) {
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