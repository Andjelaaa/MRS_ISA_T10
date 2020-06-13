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
import javax.persistence.OneToMany;

@Entity
public class KlinickiCentar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="naziv", nullable=false)
	private String naziv;
	
	@OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="kc_id", nullable=true)
    public Set<Klinika> klinika;
   
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   public Set<Klinika> getKlinika() {
      if (klinika == null)
         klinika = new HashSet<Klinika>();
      return klinika;
   }

   public void setKlinika(Set<Klinika> newKlinika) {
      this.klinika = newKlinika;
   }
   
   public void addKlinika(Klinika newKlinika) {
      if (newKlinika == null)
         return;
      if (this.klinika == null)
         this.klinika = new HashSet<Klinika>();
      if (!this.klinika.contains(newKlinika))
         this.klinika.add(newKlinika);
   }
   
   public void removeKlinika(Klinika oldKlinika) {
      if (oldKlinika == null)
         return;
      if (this.klinika != null)
         if (this.klinika.contains(oldKlinika))
            this.klinika.remove(oldKlinika);
   }
   
   public void removeAllKlinika() {
      if (klinika != null)
         klinika.clear();
   }

}