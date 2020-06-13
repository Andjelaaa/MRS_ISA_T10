package main.mrs.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Cenovnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="cenovnik", nullable=true)
    public Set<StavkaCenovnika> stavkaCenovnika;
   
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   public Set<StavkaCenovnika> getStavkaCenovnika() {
      if (stavkaCenovnika == null)
         stavkaCenovnika = new HashSet<StavkaCenovnika>();
      return stavkaCenovnika;
   }
   
   public void setStavkaCenovnika(Set<StavkaCenovnika> newStavkaCenovnika) {
      this.stavkaCenovnika = newStavkaCenovnika;
   }
   
   public void addStavkaCenovnika(StavkaCenovnika newStavkaCenovnika) {
      if (newStavkaCenovnika == null)
         return;
      if (this.stavkaCenovnika == null)
         this.stavkaCenovnika = new HashSet<StavkaCenovnika>();
      if (!this.stavkaCenovnika.contains(newStavkaCenovnika))
         this.stavkaCenovnika.add(newStavkaCenovnika);
   }
   
   public void removeStavkaCenovnika(StavkaCenovnika oldStavkaCenovnika) {
      if (oldStavkaCenovnika == null)
         return;
      if (this.stavkaCenovnika != null)
         if (this.stavkaCenovnika.contains(oldStavkaCenovnika))
            this.stavkaCenovnika.remove(oldStavkaCenovnika);
   }
   
   public void removeAllStavkaCenovnika() {
      if (stavkaCenovnika != null)
         stavkaCenovnika.clear();
   }

}