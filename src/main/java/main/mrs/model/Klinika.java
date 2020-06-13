package main.mrs.model;
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
import javax.persistence.OneToOne;

@Entity
public class Klinika {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="naziv", unique=true, nullable=false)
	private String naziv;
	@Column(name="adresa", unique=true, nullable=false)
    private String adresa;
	@Column(name="opis", unique=false, nullable=true)
    private String opis;
	@Column(name="prosecnaOcena", unique=false, nullable=true)
    private Double prosecnaOcena;
	@Column(name="brojOcena", unique=false, nullable=true)
    private int brojOcena;
	@Column(name="emailKlinike", unique=true, nullable=false)
	private String emailKlinike;
	@Column(name="kontaktKlinike", unique=true, nullable=false)
	private String kontaktKlinike;
	
	@ManyToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="klinika_id", nullable=true)
	public Set<Pacijent> pacijent;
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="klinika_id", nullable=true)
	public Set<Pregled> pregled;
	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	public Cenovnik cenovnik;
	@OneToMany(mappedBy="klinika",fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    public Set<AdminKlinike> adminKlinike;
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="klinika_id", nullable=true)
    public Set<Operacija> operacija;
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="klinika_id", nullable=true)
    public Set<Lekar> lekar;
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="klinika_id", nullable=true)
    public Set<MedSestra> medSestra;
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="klinika_id", nullable=true)
    public Set<Sala> sale;
   
	public Klinika() {}
	
	
	public String getEmailKlinike() {
		return emailKlinike;
	}


	public void setEmailKlinike(String emailKlinike) {
		this.emailKlinike = emailKlinike;
	}


	public String getKontaktKlinike() {
		return kontaktKlinike;
	}


	public void setKontaktKlinike(String kontaktKlinike) {
		this.kontaktKlinike = kontaktKlinike;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   
   public void setPacijent(Set<Pacijent> newpacijent) {
      this.pacijent = newpacijent;
   }
   
   public void addpacijent(Pacijent newpacijent) {
      if (newpacijent == null)
         return;
      if (this.pacijent == null)
         this.pacijent = new java.util.HashSet<Pacijent>();
      if (!this.pacijent.contains(newpacijent))
         this.pacijent.add(newpacijent);
   }
   
   public void removepacijent(Pacijent oldpacijent) {
      if (oldpacijent == null)
         return;
      if (this.pacijent != null)
         if (this.pacijent.contains(oldpacijent))
            this.pacijent.remove(oldpacijent);
   }
   
   public void removeAllpacijent() {
      if (pacijent != null)
         pacijent.clear();
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
   public Cenovnik getCenovnik() {
      if (cenovnik == null)
         cenovnik = new Cenovnik();
      return cenovnik;
   }
   
   public void setCenovnik(Cenovnik newCenovnik) {
      this.cenovnik = newCenovnik;
   }
   
   public Set<AdminKlinike> getAdminKlinike() {
      if (adminKlinike == null)
         adminKlinike = new java.util.HashSet<AdminKlinike>();
      return adminKlinike;
   }
   
   public void setAdminKlinike(Set<AdminKlinike> newAdminKlinike) {
      this.adminKlinike = newAdminKlinike;
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
   public Set<Lekar> getLekar() {
      if (lekar == null)
         lekar = new java.util.HashSet<Lekar>();
      return lekar;
   }
   
   public void setLekar(Set<Lekar> newLekar) {
      this.lekar = newLekar;
   }
   
   public void addLekar(Lekar newLekar) {
      if (newLekar == null)
         return;
      if (this.lekar == null)
         this.lekar = new java.util.HashSet<Lekar>();
      if (!this.lekar.contains(newLekar))
         this.lekar.add(newLekar);
   }
   
   public void removeLekar(Lekar oldLekar) {
      if (oldLekar == null)
         return;
      if (this.lekar != null)
         if (this.lekar.contains(oldLekar))
            this.lekar.remove(oldLekar);
   }
   
   public void removeAllLekar() {
      if (lekar != null)
         lekar.clear();
   }
   public Set<MedSestra> getMedSestra() {
      if (medSestra == null)
         medSestra = new java.util.HashSet<MedSestra>();
      return medSestra;
   }

   public void setMedSestra(Set<MedSestra> newMedSestra) {
      this.medSestra = newMedSestra;
   }
   
   public void addMedSestra(MedSestra newMedSestra) {
      if (newMedSestra == null)
         return;
      if (this.medSestra == null)
         this.medSestra = new java.util.HashSet<MedSestra>();
      if (!this.medSestra.contains(newMedSestra))
         this.medSestra.add(newMedSestra);
   }
   
   public void removeMedSestra(MedSestra oldMedSestra) {
      if (oldMedSestra == null)
         return;
      if (this.medSestra != null)
         if (this.medSestra.contains(oldMedSestra))
            this.medSestra.remove(oldMedSestra);
   }
   
   public void removeAllMedSestra() {
      if (medSestra != null)
         medSestra.clear();
   }

public String getNaziv() {
	return naziv;
}

public void setNaziv(String naziv) {
	this.naziv = naziv;
}

public String getAdresa() {
	return adresa;
}

public void setAdresa(String adresa) {
	this.adresa = adresa;
}

public String getOpis() {
	return opis;
}

public void setOpis(String opis) {
	this.opis = opis;
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

public Set<Pacijent> getPacijent() {
	return pacijent;
}

}