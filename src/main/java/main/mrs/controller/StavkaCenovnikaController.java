package main.mrs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.StavkaCenovnikaDTO;
import main.mrs.model.Cenovnik;
import main.mrs.model.StavkaCenovnika;
import main.mrs.model.TipPregleda;
import main.mrs.service.CenovnikService;
import main.mrs.service.StavkaCenovnikaService;
import main.mrs.service.TipPregledaService;

@RestController
@RequestMapping(value="api/stavkacenovnika")
public class StavkaCenovnikaController {
	
	@Autowired
	private StavkaCenovnikaService StavkaCenovnikaService;
	@Autowired
	private TipPregledaService TipPregledaService;
	@Autowired
	private CenovnikService CenovnikService;

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<StavkaCenovnikaDTO>> getAllStavkaCenovnikas() {

		List<StavkaCenovnika> StavkaCenovnikas = StavkaCenovnikaService.findAll();

		// convert StavkaCenovnikas to DTOs
		List<StavkaCenovnikaDTO> StavkaCenovnikasDTO = new ArrayList<>();
		for (StavkaCenovnika s : StavkaCenovnikas) {
			StavkaCenovnikasDTO.add(new StavkaCenovnikaDTO(s));
		}

		return new ResponseEntity<>(StavkaCenovnikasDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/cena/{tipPregleda}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<Double> cenaPregleda(@PathVariable String tipPregleda) {

		//Klinika Klinika = KlinikaService.findOneById(klinikaId)
		
		
		TipPregleda tip = TipPregledaService.findByNaziv(tipPregleda);
		return new ResponseEntity<>(tip.getStavka().getCena(), HttpStatus.OK);
	}
	@Transactional
	@PostMapping(consumes = "application/json", value = "/{nazivTipaPregleda}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<StavkaCenovnikaDTO> saveStavkaCenovnika(@RequestBody StavkaCenovnikaDTO StavkaCenovnikaDTO, @PathVariable String nazivTipaPregleda) {

		
		TipPregleda tp = TipPregledaService.findByNaziv(nazivTipaPregleda);
		if(tp.getStavka()!= null)
			StavkaCenovnikaService.remove(tp.getStavka().getId());
		Cenovnik c = CenovnikService.findOne(1);
		StavkaCenovnika StavkaCenovnika = new StavkaCenovnika();
		StavkaCenovnika.setCena(StavkaCenovnikaDTO.getCena());
		StavkaCenovnika.setTipPregleda(tp);
		tp.setStavka(StavkaCenovnika);
		StavkaCenovnika.setCenovnik(c);
		
		
		try {
			StavkaCenovnika = StavkaCenovnikaService.save(StavkaCenovnika);
		} catch (Exception e) {
			return new ResponseEntity<>(new StavkaCenovnikaDTO(StavkaCenovnika), HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(new StavkaCenovnikaDTO(StavkaCenovnika), HttpStatus.CREATED);
	}

}

