package main.mrs.isa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.DijagnozaDTO;
import main.mrs.dto.LekDTO;
import main.mrs.model.Dijagnoza;
import main.mrs.model.Lek;
import main.mrs.model.PomocnaKlasa2;
import main.mrs.model.PomocnaKlasa3;
import main.mrs.service.DijagnozaService;

@RestController
@RequestMapping(value="api/dijagnoze")
public class DijagnozaController {

	@Autowired
	private DijagnozaService DijagnozaService;
	

	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'LEKAR')")
	public ResponseEntity<List<DijagnozaDTO>> getAllDijagnoze() {

		List<Dijagnoza> dijagnoze = DijagnozaService.findAll();

		List<DijagnozaDTO> DijagnozeeDTO = new ArrayList<>();
		for (Dijagnoza s : dijagnoze) {
			DijagnozeeDTO.add(new DijagnozaDTO(s));
		}
        
		return new ResponseEntity<>(DijagnozeeDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<DijagnozaDTO> saveLekove(@RequestBody DijagnozaDTO DijagnozaDTO) {

		Dijagnoza dijagnoza = new Dijagnoza();
		dijagnoza.setNaziv(DijagnozaDTO.getNaziv());
		dijagnoza.setSifra(DijagnozaDTO.getSifra());
		
		try {
			List<Dijagnoza> dijagnoze = DijagnozaService.findAll();

			for (Dijagnoza s : dijagnoze) {
				if(s.getNaziv().equalsIgnoreCase(dijagnoza.getNaziv())) {
					throw new Exception();
					
				}
				if(s.getSifra().equalsIgnoreCase(dijagnoza.getSifra())) {
					throw new Exception();
				}
								
			}
			dijagnoza = DijagnozaService.save(dijagnoza);
		} catch (Exception e) {
			return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new DijagnozaDTO(dijagnoza), HttpStatus.CREATED);
	}
	@PostMapping(value="/izmena",consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<DijagnozaDTO> changeDijagnoza(@RequestBody PomocnaKlasa3 data) {
		
        DijagnozaDTO novaDijagnoza = data.nova;
        DijagnozaDTO staraDijagnoza = data.stara;
		try {
			    List<Dijagnoza> dijagnoze = DijagnozaService.findAll();
			    
		        Dijagnoza stariNadji = DijagnozaService.findByNaziv(staraDijagnoza.getNaziv());
				for (Dijagnoza s : dijagnoze) {
					if(!s.getNaziv().equalsIgnoreCase(staraDijagnoza.getNaziv()) && 
							s.getNaziv().equalsIgnoreCase(novaDijagnoza.getNaziv())) {
						
						throw new Exception();
						
					}
					if(!s.getSifra().equalsIgnoreCase(staraDijagnoza.getSifra()) && 
							s.getSifra().equalsIgnoreCase(novaDijagnoza.getSifra())) {
						
						throw new Exception();
					}
									
				}
				stariNadji.setNaziv(novaDijagnoza.getNaziv());
		        stariNadji.setSifra(novaDijagnoza.getSifra());
		        stariNadji = DijagnozaService.save(stariNadji);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>( HttpStatus.OK);
	}
}
