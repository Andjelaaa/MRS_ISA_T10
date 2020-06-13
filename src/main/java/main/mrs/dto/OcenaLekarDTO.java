package main.mrs.dto;

import main.mrs.model.OcenaLekar;

public class OcenaLekarDTO {
	
	private Integer id;
	private int lekarId;
	private int pacijentId;
	private int ocena;
	
	public OcenaLekarDTO() {
		super();
	}

	
	public OcenaLekarDTO(Integer id, int lekarId, int pacijentId, int ocena) {
		super();
		this.id = id;
		this.lekarId = lekarId;
		this.pacijentId = pacijentId;
		this.ocena = ocena;
	}


	public OcenaLekarDTO(OcenaLekar o) {
		// TODO Auto-generated constructor stub
		this(o.getLekarId(), o.getPacijentId(), o.getOcena());
	}


	public OcenaLekarDTO(int lekarId2, int pacijentId2, int ocena2) {
		// TODO Auto-generated constructor stub
		this.lekarId = lekarId2;
		this.pacijentId = pacijentId2;
		this.ocena = ocena2;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getLekarId() {
		return lekarId;
	}


	public void setLekarId(int lekarId) {
		this.lekarId = lekarId;
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
