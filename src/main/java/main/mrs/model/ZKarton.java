package main.mrs.model;
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
import javax.persistence.OneToMany;
@Entity
public class ZKarton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="krvnaGrupa", unique=false, nullable=true)
    private String krvnaGrupa;
	@Column(name="visina", unique=false, nullable=true)
    private Double visina;
	@Column(name="tezina", unique=false, nullable=true)
    private Double tezina;
	@Column(name="dioptrija", unique=false, nullable=true)
    private Double dioptrija;
	@Column(name="pol", unique=false, nullable=true)
    private String pol;
	@Column(name="datumRodjenja", unique=false, nullable=true)
    private Date datumRodjenja;
   
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="zkarton_id", nullable=true)
    public Set<Pregled> pregled;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Lek> lek;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="zkarton_id", nullable=true)
    public Set<Operacija> operacija;
   

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   
   public Set<Pregled> getPregled() {
      if (pregled == null)
         pregled = new java.util.HashSet<Pregled>();
      return pregled;
   }
   
   public void setPregled(Set<Pregled> newPregled) {
      this.pregled = newPregled;
   }
   
   public void addPregled(Pregled newPregled) {
      if (newPregled == null)
         return;
      if (this.pregled == null)
         this.pregled = new java.util.HashSet<Pregled>();
      if (!this.pregled.contains(newPregled))
         this.pregled.add(newPregled);
   }
   
   public void removePregled(Pregled oldPregled) {
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
   public Set<Operacija> getOperacija() {
      if (operacija == null)
         operacija = new java.util.HashSet<Operacija>();
      return operacija;
   }

   
   public void setOperacija(Set<Operacija> newOperacija) {
      this.operacija = newOperacija;
   }
   
   public void addOperacija(Operacija newOperacija) {
      if (newOperacija == null)
         return;
      if (this.operacija == null)
         this.operacija = new java.util.HashSet<Operacija>();
      if (!this.operacija.contains(newOperacija))
         this.operacija.add(newOperacija);
   }
   
   public void removeOperacija(Operacija oldOperacija) {
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