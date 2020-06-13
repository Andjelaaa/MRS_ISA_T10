package main.mrs.dto;

import java.util.Date;

public class ZauzeceDTO {
	public Date pocetak;
	public Date kraj;
	public ZauzeceDTO() {
		super();
	}
	public ZauzeceDTO(Date pocetak, Date kraj) {
		super();
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
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
}
