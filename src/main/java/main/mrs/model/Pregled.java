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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
@Entity
public class Pregled {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="datumVreme", unique=false, nullable=false)
    private Date datumVreme;
	@Column(name="trajanje", unique=false, nullable=true)
    private int trajanje;
	@Column(name="popust", unique=false, nullable=true)
    private Double popust;
	@Column(name="status", unique=false, nullable=false)
    private Status status;
   
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name="lekar_id", nullable=true)
    public Lekar lekar;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public Sala sala;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="tip_pregleda_id", nullable=true)
    public TipPregleda tipPregleda;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public Izvestaj izvestaj;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
   
    public Set<Lek> lek;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    public Set<Dijagnoza> dijagnoza;
   
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="pacijent_id", nullable=true)
    public Pacijent pacijent;
    
    @Version
   // @Column(name="version", unique = false, nullable = true)
    private int version;
    
    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
   
   public Pacijent getPacijent() {
	return pacijent;
	}
	
	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
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
   public Set<Dijagnoza> getDijagnoza() {
      if (dijagnoza == null)
         dijagnoza = new java.util.HashSet<Dijagnoza>();
      return dijagnoza;
   }
   
   public void setDijagnoza(Set<Dijagnoza> newDijagnoza) {
      this.dijagnoza = newDijagnoza;
   }
   
   public void addDijagnoza(Dijagnoza newDijagnoza) {
      if (newDijagnoza == null)
         return;
      if (this.dijagnoza == null)
         this.dijagnoza = new java.util.HashSet<Dijagnoza>();
      if (!this.dijagnoza.contains(newDijagnoza))
         this.dijagnoza.add(newDijagnoza);
   }
   
   public void removeDijagnoza(Dijagnoza oldDijagnoza) {
      if (oldDijagnoza == null)
         return;
      if (this.dijagnoza != null)
         if (this.dijagnoza.contains(oldDijagnoza))
            this.dijagnoza.remove(oldDijagnoza);
   }
   
   public void removeAllDijagnoza() {
      if (dijagnoza != null)
         dijagnoza.clear();
   }

	public Date getDatumVreme() {
		return datumVreme;
	}
	
	public void setDatumVreme(Date datumVreme) {
		this.datumVreme = datumVreme;
	}
	
	public int getTrajanje() {
		return trajanje;
	}
	
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	
	public Double getPopust() {
		return popust;
	}
	
	public void setPopust(Double popust) {
		this.popust = popust;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Lekar getLekar() {
		return lekar;
	}
	
	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	
	public Sala getSala() {
		return sala;
	}
	
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}
	
	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
	
	public Izvestaj getIzvestaj() {
		return izvestaj;
	}
	
	public void setIzvestaj(Izvestaj izvestaj) {
		this.izvestaj = izvestaj;
	}

}