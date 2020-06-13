package main.mrs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.LekDTO;
import main.mrs.model.Lek;
import main.mrs.model.Pacijent;
import main.mrs.model.PomocnaKlasa2;
import main.mrs.model.PomocnaKlasa9;
import main.mrs.model.Pregled;
import main.mrs.service.LekService;
import main.mrs.service.PacijentService;
import main.mrs.service.PregledService;

@RestController
@RequestMapping(value="api/lekovi")
public class LekController {

	@Autowired
	private LekService LekService;
	
	@Autowired
	private PacijentService PacijentService;
	
	@Autowired
	private PregledService PregledService;
	

	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'LEKAR')")
	public ResponseEntity<List<LekDTO>> getAllLekove() {
			
		List<Lek> lekovi = LekService.findAll();

		List<LekDTO> LeksDTO = new ArrayList<>();
		for (Lek s : lekovi) {
			LeksDTO.add(new LekDTO(s));
		}

		return new ResponseEntity<>(LeksDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/pacijent")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<List<PomocnaKlasa9>> getDijagnozePacijenta() {
		List<PomocnaKlasa9> result = new ArrayList<PomocnaKlasa9>();
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Pacijent p = PacijentService.findByEmail(trenutniKorisnik.getName());
		// dobavim zavrsene preglede
		// pregled->izvestaj->recept->setLekova
		
		List<Pregled> pregledi = PregledService.findZavrsenePacijent(p.getId());
		for (Pregled s : pregledi) {
	
			if(s.getIzvestaj().getRecept() == null)
				continue;
			for(Lek l: s.getIzvestaj().getRecept().getLek())
			{
				PomocnaKlasa9 pk = new PomocnaKlasa9(s.getDatumVreme(), l.getNaziv(), s.getLekar().getIme() + " " + s.getLekar().getPrezime());
				result.add(pk);
			}
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<LekDTO> saveLekove(@RequestBody LekDTO LekDTO) {

		Lek lek = new Lek();
		lek.setNaziv(LekDTO.getNaziv());
		lek.setSifra(LekDTO.getSifra());
		
		try {
			
			List<Lek> lekovi = LekService.findAll();
			
			for (Lek s : lekovi) {
				if(s.getNaziv().equalsIgnoreCase(lek.getNaziv())) {
					throw new Exception();
					
				}
				if(s.getSifra().equalsIgnoreCase(lek.getSifra())) {
					throw new Exception();
				}
			}
			lek = LekService.save(lek);
		} catch (Exception e) {
			return new ResponseEntity<>(new LekDTO(lek), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new LekDTO(lek), HttpStatus.CREATED);
	}
	@PostMapping(value="/izmena",consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<LekDTO> changeLek(@RequestBody PomocnaKlasa2 data) {
        LekDTO izmenjen = data.novi;
        LekDTO stariPodaci = data.stari;

		try {
			List<Lek> lekovii = LekService.findAll();
		    Lek stariNadji = LekService.findByNaziv(stariPodaci.getNaziv());
			for (Lek s : lekovii) {
				//System.out.println(s.getNaziv().equalsIgnoreCase(stariPodaci.getNaziv())+"DSAD");
				//System.out.println(s.getNaziv().equalsIgnoreCase(izmenjen.getNaziv())+"DSAD");
				if(!s.getNaziv().equalsIgnoreCase(stariPodaci.getNaziv()) && 
						s.getNaziv().equalsIgnoreCase(izmenjen.getNaziv())) {
					throw new Exception();
					
				}
				if(!s.getSifra().equalsIgnoreCase(stariPodaci.getSifra()) && 
						s.getSifra().equalsIgnoreCase(izmenjen.getSifra())) {
					throw new Exception();
				}
			}
			stariNadji.setNaziv(izmenjen.getNaziv());
			stariNadji.setSifra(izmenjen.getSifra());
			stariNadji = LekService.save(stariNadji);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>( HttpStatus.OK);
	}
}
