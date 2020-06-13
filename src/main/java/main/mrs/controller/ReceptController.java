package main.mrs.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.DijagnozaDTO;
import main.mrs.dto.LekDTO;
import main.mrs.dto.ReceptDTO;
import main.mrs.model.Dijagnoza;
import main.mrs.model.Izvestaj;
import main.mrs.model.Lek;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.model.PomocnaKlasa8;
import main.mrs.model.Pregled;
import main.mrs.model.Recept;
import main.mrs.service.DijagnozaService;
import main.mrs.service.IzvestajService;
import main.mrs.service.LekService;
import main.mrs.service.MedSestraService;
import main.mrs.service.PacijentService;
import main.mrs.service.PregledService;
import main.mrs.service.ReceptService;

@RestController
@RequestMapping(value="api/recept")
public class ReceptController {
	@Autowired
	private ReceptService ReceptService;
	
	@Autowired
	private MedSestraService MedSestraService;
	@Autowired
	private LekService LekService;
	@Autowired
	private PregledService PregledService;
	@Autowired
	private PacijentService PacijentService;
	
	@Autowired
	private IzvestajService IzvestajService;
	
	@Autowired
	private DijagnozaService DijagnozaService;
	
	@GetMapping(value = "/neovereni/{email}")
	@PreAuthorize("hasRole( 'MED_SESTRA')")
	public ResponseEntity<List<ReceptDTO>> getAllRecepte(@PathVariable String email) {
		MedSestra meds = MedSestraService.findByEmail(email);
		List<Pregled> pregledi = PregledService.findZavrsene(meds.getKlinika().getId()); //zavrseni i id klinike

		List<ReceptDTO> receptiDTO = new ArrayList<>();
		
		for(Pregled p : pregledi) {
			if(p.getIzvestaj().getRecept().getMedSestra() == null) {
				Recept r = p.getIzvestaj().getRecept(); // ako ne radi dobavi iz baze
				ReceptDTO recept = new ReceptDTO(r);
				recept.setImePacijenta(r.getImePacijenta());
				recept.setPrezimePacijenta(r.getPrezimePacijenta());
				receptiDTO.add(recept);
			}
			
		}
		
		
		return new ResponseEntity<>(receptiDTO, HttpStatus.OK);
	}
	@PutMapping(value = "/izmeni/{email}", consumes = "application/json")
	@PreAuthorize("hasRole('MED_SESTRA')")
	public ResponseEntity<String> overiRecept(@RequestBody ReceptDTO ReceptDTO, @PathVariable String email) {
		
		Recept recept = ReceptService.findOne(ReceptDTO.getId());
		
		recept.setLek(Recept.changeDTO(ReceptDTO.getLek()));
		if(recept.getMedSestra() !=null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		MedSestra m = MedSestraService.findByEmail(email);
		recept.setMedSestra(m);
		
		try {
			recept = ReceptService.save(recept);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>( HttpStatus.OK);
		
	}
	@PutMapping(value = "/izmeniDijagnozu/{pregled_id}/{izvestaj_id}", consumes = "application/json")
	@PreAuthorize("hasAnyRole('LEKAR')")
	public ResponseEntity<String> izmeniDijagnozuRecept(@RequestBody DijagnozaDTO DijagnozaDTO, @PathVariable Integer pregled_id,
			@PathVariable Integer izvestaj_id) {
		//samo dijagnoza se menja
		Pregled pregled = PregledService.findById(pregled_id);
		//pronadji pacijenta
		Pacijent pacijent = PacijentService.findById(pregled.getPacijent().getId());
		
		//pa izvestaj njegov odgovarajuci 
		
		Izvestaj izvestaj = IzvestajService.findOne(pregled_id);
		Dijagnoza dijagnoza = DijagnozaService.findByNaziv(DijagnozaDTO.getNaziv());
		izvestaj.setDijagnoza(dijagnoza);

		try {
			izvestaj = IzvestajService.save(izvestaj);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>( HttpStatus.OK);
		
	}
	@PutMapping(value = "/izmeniLekove/{pregled_id}/{izvestaj_id}", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<String> izmeniLekoveRecept(@RequestBody PomocnaKlasa8 pom,
			@PathVariable Integer pregled_id,@PathVariable Integer izvestaj_id) {
		//samo lekovi
		Pregled pregled = PregledService.findById(pregled_id);
		//pronadji pacijenta
		Pacijent pacijent = PacijentService.findById(pregled.getPacijent().getId());
		
		//pa izvestaj njegov odgovarajuci 
		
		Izvestaj izvestaj = IzvestajService.findOne(pregled_id);
		
		//Pa njegov recept
		Recept recept = ReceptService.findOne(izvestaj.getRecept().getId());
		
		Set<Lek> lekovi = new HashSet<Lek>();
		//prebaci lekove
		for(LekDTO l: pom.lekoviDTO) {
			Lek lek = LekService.findByNaziv(l.getNaziv());
			lekovi.add(lek);	
			
		}
		
		recept.setLek(lekovi);
		try {
			recept = ReceptService.save(recept);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>( HttpStatus.OK);
		
		
	}
	@PutMapping(value = "/izmeniOba/{pregled_id}/{izvestaj_id}", consumes = "application/json")
	@PreAuthorize("hasAnyRole('LEKAR')")
	public ResponseEntity<String> izmeniObaRecept(@RequestBody PomocnaKlasa8 pom,
			@PathVariable Integer pregled_id,@PathVariable Integer izvestaj_id) {
		//oba menjaj
		Pregled pregled = PregledService.findById(pregled_id);
		//pronadji pacijenta
		Pacijent pacijent = PacijentService.findById(pregled.getPacijent().getId());
				
		//pa izvestaj njegov odgovarajuci 
				
		Izvestaj izvestaj = IzvestajService.findOne(pregled_id);
		Dijagnoza dijagnoza = DijagnozaService.findByNaziv(pom.dijagnozaDTO.getNaziv());
		izvestaj.setDijagnoza(dijagnoza);
		//Pa njegov recept
		Recept recept = ReceptService.findOne(izvestaj.getRecept().getId());
				
		Set<Lek> lekovi = new HashSet<Lek>();
				//prebaci lekove
		for(LekDTO l: pom.lekoviDTO) {
			Lek lek = LekService.findByNaziv(l.getNaziv());
			lekovi.add(lek);	
					
		}
		recept.setLek(lekovi);
		try {
			izvestaj = IzvestajService.save(izvestaj);
			recept = ReceptService.save(recept);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	    return new ResponseEntity<>( HttpStatus.OK);
		
	}



}
