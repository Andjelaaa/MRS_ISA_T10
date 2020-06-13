package main.mrs.dto;


import main.mrs.model.Dijagnoza;
import main.mrs.model.Izvestaj;
import main.mrs.model.Recept;

public class IzvestajDTO {
	private Integer id;
	private String opis;   
	public DijagnozaDTO dijagnoza;
	public ReceptDTO recept;
   
	public IzvestajDTO() {
		super();
	}
	
	public IzvestajDTO(Izvestaj s) {
		this(s.getDijagnoza(), s.getId(),s.getOpis(), s.getRecept());
		
	}

	public IzvestajDTO(Dijagnoza dijagnoza2, Integer id2, String opis2, Recept recept2) {
		this.id = id2;
		this.opis = opis2;
		
		if(dijagnoza2 != null)
		    this.dijagnoza = new DijagnozaDTO(dijagnoza2);
		else
		    this.dijagnoza = null;
		
		if(recept2 != null)
			this.recept = new ReceptDTO(recept2);
		else
		    this.recept = null;
		
	}

	public Integer getId() {
		return id;
	}

	

	public void setId(Integer id) {
		this.id = id;
	}
   
   
   public DijagnozaDTO getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(DijagnozaDTO dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public ReceptDTO getRecept() {
		return recept;
	}

	public void setRecept(ReceptDTO recept) {
		this.recept = recept;
	}

	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}

}