package main.mrs.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Izvestaj {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="opis", unique=false, nullable = false)
	private String opis; 
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="dijagnoza_id", nullable = true)
	public Dijagnoza dijagnoza;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="recept_id", nullable = true)
	public Recept recept;
   
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   
   
   public Dijagnoza getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(Dijagnoza dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

    public String getOpis() {
	    return opis;
    }

	public void setOpis(String opis) {
		this.opis = opis;
	}

}