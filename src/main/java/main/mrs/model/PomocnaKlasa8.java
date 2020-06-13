package main.mrs.model;

import java.util.HashSet;
import java.util.Set;

import main.mrs.dto.DijagnozaDTO;
import main.mrs.dto.LekDTO;

public class PomocnaKlasa8 {
	public DijagnozaDTO dijagnozaDTO;
	public Set<LekDTO> lekoviDTO;
	public PomocnaKlasa8() {
	
	}
	public PomocnaKlasa8(DijagnozaDTO dijagnozaDTO, HashSet<LekDTO> lekoviDTO) {
		super();
		this.dijagnozaDTO = dijagnozaDTO;
		this.lekoviDTO = lekoviDTO;
	}
	
	
}
