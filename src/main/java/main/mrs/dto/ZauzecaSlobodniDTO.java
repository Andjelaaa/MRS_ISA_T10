package main.mrs.dto;

import java.util.List;
import java.util.Date;

public class ZauzecaSlobodniDTO {
	private List<ZauzeceDTO> zauzeca;
	
	private Date prviSlobodan;
	public List<ZauzeceDTO> getZauzeca() {
		return zauzeca;
	}
	public void setZauzeca(List<ZauzeceDTO> zauzeca) {
		this.zauzeca = zauzeca;
	}
	public Date getPrviSlobodan() {
		return prviSlobodan;
	}
	public void setPrviSlobodan(Date prviSlobodan) {
		this.prviSlobodan = prviSlobodan;
	}
}
