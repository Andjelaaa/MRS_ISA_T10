package main.mrs.model;

public class ZahtevPregled {
	public String lekarEmail;
	public String tipPregledaNaziv;
	public String datum;
	public String vreme;
	public String klinikaId;
	public String pacijentId;
	
	public ZahtevPregled() {
		super();
	}
	public ZahtevPregled(String lekar, String tipPregledaNaziv, String datum, String vreme, String klinikaId,
			String pacijentId) {
		super();
		this.lekarEmail = lekar;
		this.tipPregledaNaziv = tipPregledaNaziv;
		this.datum = datum;
		this.vreme = vreme;
		this.klinikaId = klinikaId;
		this.pacijentId = pacijentId;
	}
	
	
}
