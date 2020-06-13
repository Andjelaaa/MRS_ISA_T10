package main.mrs.dto;

import java.util.Set;


import main.mrs.model.TipPregleda;

public class TipPregledaDTO {
	
	private Integer id;
	private String naziv;
	private String opis;
	
    private int brojAktvnih;
    private StavkaCenovnikaDTO stavka;
    private Set<LekarDTO> lekar;

    public TipPregledaDTO() {
    	
    }
    
    public TipPregledaDTO(TipPregleda s) {
		this(s.getId(), s.getNaziv(), s.getOpis(), s.getBrojAktvnih());
	}

	public TipPregledaDTO(int id2, String naziv2, String opis2, int brojAktivnih) {
		this.id = id2;
		this.naziv = naziv2;
		this.opis = opis2;
		this.brojAktvnih = brojAktivnih;
		this.stavka = new StavkaCenovnikaDTO();
	}

	public Integer getId() {
    	return id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getBrojAktvnih() {
		return brojAktvnih;
	}

	public void setBrojAktvnih(int brojAktvnih) {
		this.brojAktvnih = brojAktvnih;
}

	public StavkaCenovnikaDTO getStavka() {
		return stavka;
	}

	public void setStavka(StavkaCenovnikaDTO stavka) {
		this.stavka = stavka;
	}

	public Set<LekarDTO> getLekar() {
		return lekar;
	}

	public void setLekar(Set<LekarDTO> lekar) {
		this.lekar = lekar;
	}

}