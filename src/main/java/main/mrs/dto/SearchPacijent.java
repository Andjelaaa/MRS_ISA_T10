package main.mrs.dto;

public class SearchPacijent {
	public String ime;
	public String prezime;	
	public String lbo;
	
	public String getLbo() {
		return lbo;
	}
	public void setLbo(String lbo) {
		this.lbo = lbo;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
}
