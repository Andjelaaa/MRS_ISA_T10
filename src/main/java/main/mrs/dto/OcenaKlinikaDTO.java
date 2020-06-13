package main.mrs.dto;

import main.mrs.model.OcenaKlinika;

public class OcenaKlinikaDTO {

	private Integer id;
	private int klinikaId;
	private int pacijentId;
	private int ocena;
	
	public OcenaKlinikaDTO() {
		super();
	}


	public OcenaKlinikaDTO(Integer id, int klinikaId, int pacijentId, int ocena) {
		super();
		this.id = id;
		this.klinikaId = klinikaId;
		this.pacijentId = pacijentId;
		this.ocena = ocena;
	}



	public OcenaKlinikaDTO(OcenaKlinika ocena2) {
		// TODO Auto-generated constructor stub
		this(ocena2.getKlinikaId(), ocena2.getPacijentId(), ocena2.getOcena());
	}


	public OcenaKlinikaDTO(int klinikaId2, int pacijentId2, int ocena2) {
		// TODO Auto-generated constructor stub
		this.klinikaId = klinikaId2;
		this.pacijentId = pacijentId2;
		this.ocena = ocena2;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getKlinikaId() {
		return klinikaId;
	}



	public void setKlinikaId(int klinikaId) {
		this.klinikaId = klinikaId;
	}



	public int getPacijentId() {
		return pacijentId;
	}



	public void setPacijentId(int pacijentId) {
		this.pacijentId = pacijentId;
	}



	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	
	
	
	
}
