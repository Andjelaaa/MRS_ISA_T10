package main.mrs.dto;
import java.util.*;

public class CenovnikDTO {
   private Integer id;
   public Set<StavkaCenovnikaDTO> stavkaCenovnika;
   
   
   public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Set<StavkaCenovnikaDTO> getStavkaCenovnika() {
      if (stavkaCenovnika == null)
         stavkaCenovnika = new HashSet<StavkaCenovnikaDTO>();
      return stavkaCenovnika;
   }
   
   public void setStavkaCenovnika(Set<StavkaCenovnikaDTO> newStavkaCenovnika) {
      this.stavkaCenovnika = newStavkaCenovnika;
   }
   
   public void addStavkaCenovnika(StavkaCenovnikaDTO newStavkaCenovnika) {
      if (newStavkaCenovnika == null)
         return;
      if (this.stavkaCenovnika == null)
         this.stavkaCenovnika = new HashSet<StavkaCenovnikaDTO>();
      if (!this.stavkaCenovnika.contains(newStavkaCenovnika))
         this.stavkaCenovnika.add(newStavkaCenovnika);
   }
   
   public void removeStavkaCenovnika(StavkaCenovnikaDTO oldStavkaCenovnika) {
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