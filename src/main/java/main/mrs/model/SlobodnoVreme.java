package main.mrs.model;

import java.util.Date;

public class SlobodnoVreme {
	// vremenski intervala kada je slobodan lekar
	
	public Date pocetak;
	public Date kraj;
	
	
	
	public SlobodnoVreme() {
		super();
	}



	public SlobodnoVreme(Date pocetak, Date kraj) {
		super();
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	
	
	
}
