package main.mrs.dto;

import java.util.Date;

public class SearchIzvestaj {
	private Date pocetak;
	private Date kraj;
	public Date getPocetak() {
		return pocetak;
	}
	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}
	public Date getKraj() {
		return kraj;
	}
	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}
	public SearchIzvestaj(Date pocetak, Date kraj) {
		super();
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	public SearchIzvestaj() {
		super();
	}
	
	
}
