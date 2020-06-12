package main.mrs.isa.dto;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



import main.mrs.model.Lekar;
import main.mrs.model.Operacija;
import main.mrs.model.Pacijent;
import main.mrs.model.Sala;
import main.mrs.model.Status;
import main.mrs.model.StavkaCenovnika;

public class OperacijaDTO {
	private Integer id;
   private Date datumVreme;
   private int trajanje;
   private StatusDTO status;
   
   public StavkaCenovnikaDTO stavkaCenovnika;
   public SalaDTO sala;
   public Set<LekarDTO> lekar;
   public PacijentDTO pacijent;
  
   public OperacijaDTO() {
			
		}
   public OperacijaDTO(Operacija s) {
	    this.id = s.getId();
		this.trajanje = s.getTrajanje();
		this.datumVreme = s.getDatumVreme();
		this.lekar = konvertujLekare(s.getLekar());
		if (s.getSala() != null)
			this.sala = new SalaDTO(s.getSala());
		if(s.getPacijent() != null)
			this.pacijent = new PacijentDTO(s.getPacijent());
		this.status = StatusDTO.valueOf(s.getStatus().toString());
	}
	
	public OperacijaDTO(Date datumVreme2, Integer id2, Set<Lekar> lekar2, Sala sala2, Status status2,
		StavkaCenovnika stavkaCenovnika2, int trajanje2, Pacijent p) {
		this.id =id2;
		this.datumVreme= datumVreme2;
		this.trajanje = trajanje2;
		this.status = StatusDTO.valueOf(status2.toString());
		this.stavkaCenovnika = null; // Ovo treba izmeniti 
		this.sala = konertujSalu(sala2);
		this.lekar = konvertujLekare(lekar2);
		this.pacijent = konvertujPacijenta(p);

	}
	

	private PacijentDTO konvertujPacijenta(Pacijent p) {
		PacijentDTO dto = new PacijentDTO();
		dto.setAdresa(p.getAdresa());
		dto.setAktivan(p.isAktivan());
		dto.setDrzava(p.getDrzava());
		dto.setEmail(p.getEmail());
		dto.setGrad(p.getGrad());
		dto.setIme(p.getIme());
		dto.setPrezime(p.getPrezime());
		//le.setKlinika(null);// treba da se namesti konvertor IZ DTO
		dto.setKontakt(p.getKontakt());
		dto.setLozinka(p.getLozinka());
		dto.setLbo(p.getLbo());
		dto.setPregled(null);
		dto.setId(p.getId());
		//dto.Set.setZKarton(p.getzKarton());
		
		return dto;
	}
	private Set<LekarDTO> konvertujLekare(Set<Lekar> lekar2) {
		Set<LekarDTO> dtos = new HashSet<LekarDTO>();
		for(Lekar l : lekar2) {
			
			LekarDTO dto = new LekarDTO();
			dto.setAdresa(l.getAdresa());
			dto.setDrzava(l.getDrzava());
			dto.setEmail(l.getEmail());
			dto.setGrad(l.getGrad());
			dto.setIme(l.getIme());
			dto.setPrezime(l.getPrezime());
			//le.setKlinika(null);// treba da se namesti konvertor IZ DTO
			dto.setKontakt(l.getKontakt());
			dto.setLozinka(l.getLozinka());
			dto.setRvKraj(l.getRvKraj());
			dto.setRvPocetak(l.getRvPocetak());
			dtos.add(dto);
		}
		
		
		
		return dtos;
	}
	 public PacijentDTO getPacijent() {
			return pacijent;
		}
		public void setPacijent(PacijentDTO pacijent) {
			this.pacijent = pacijent;
		}
	private SalaDTO konertujSalu(Sala sala2) {
		
		SalaDTO dto = new SalaDTO();
		dto.setId(sala2.getId());
		dto.setBroj(sala2.getBroj());
		dto.setKlinika(null); // ovo izmeniitiiiiiiiiiiiiii
		dto.setNaziv(sala2.getNaziv());
		dto.setPregled(null); //izmeeeeeniiitiitiitiiititiitiit
		
		return dto;
	}
	public Integer getId() {
			return id;
		}
	
		public void setId(Integer id) {
			this.id = id;
		}
	public StavkaCenovnikaDTO getStavkaCenovnika() {
		return stavkaCenovnika;
	}
	
	public void setStavkaCenovnika(StavkaCenovnikaDTO stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}
	
	public SalaDTO getSala() {
		return sala;
	}
	
	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}
	
	public Date getDatumVreme() {
		return datumVreme;
	}
	
	public void setDatumVreme(Date datumVreme) {
		this.datumVreme = datumVreme;
	}
	
	public int getTrajanje() {
		return trajanje;
	}
	
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	
	public StatusDTO getStatus() {
		return status;
	}
	
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	
	public Set<LekarDTO> getLekar() {
		return lekar;
	}
	
	public void setLekar(Set<LekarDTO> lekar) {
		this.lekar = lekar;
	}
	
	
	}