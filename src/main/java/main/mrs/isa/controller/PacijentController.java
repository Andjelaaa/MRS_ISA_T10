package main.mrs.isa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.PacijentDTO;
import main.mrs.dto.SearchPacijent;
import main.mrs.model.Klinika;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.service.KlinikaService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.PacijentService;

@RestController
@RequestMapping(value = "api/pacijent")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private LekarService lekarService;

	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private MedSestraService medSestraService;
	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<PacijentDTO>> getAllPacijents() {
		
		List<Pacijent> Pacijents = pacijentService.findAll();

		// convert Pacijents to DTOs
		List<PacijentDTO> PacijentsDTO = new ArrayList<>();
		for (Pacijent s : Pacijents) {
			PacijentsDTO.add(new PacijentDTO(s));
		}

		return new ResponseEntity<>(PacijentsDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/all/{email}")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<PacijentDTO>> getAllPacijentii(@PathVariable String email) {
		//korisnik moze biti med sestra ili korisnik
		Lekar lekar = lekarService.findByEmail(email);
		MedSestra meds =  medSestraService.findByEmail(email);
	
		
		List<Pacijent> Pacijents = pacijentService.findAll();
		
		// convert Pacijents to DTOs
		List<PacijentDTO> PacijentsDTO = new ArrayList<>();
		for (Pacijent s : Pacijents) {
			   if((lekar != null && lekar.getKlinika().getPacijent().contains(s))
					   ||( meds != null && meds.getKlinika().getPacijent().contains(s))) {
				   PacijentsDTO.add(new PacijentDTO(s));
			   }
		}

		return new ResponseEntity<>(PacijentsDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT')")
	public ResponseEntity<PacijentDTO> savePacijent(@RequestBody PacijentDTO PacijentDTO) {

		Pacijent Pacijent = new Pacijent();
		Pacijent.setIme(PacijentDTO.getIme());
		Pacijent.setPrezime(PacijentDTO.getPrezime());
		Pacijent.setEmail(PacijentDTO.getEmail());
		Pacijent.setLozinka(PacijentDTO.getLozinka());
		Pacijent.setGrad(PacijentDTO.getGrad());
		Pacijent.setAdresa(PacijentDTO.getAdresa());
		Pacijent.setDrzava(PacijentDTO.getDrzava());
		Pacijent.setLbo(PacijentDTO.getLbo());
		Pacijent.setKontakt(PacijentDTO.getKontakt());
	
		Pacijent = pacijentService.save(Pacijent);
		return new ResponseEntity<>(new PacijentDTO(Pacijent), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/search/{email}")
	@PreAuthorize("hasAnyRole( 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<PacijentDTO>> getSearchLekars(@RequestBody SearchPacijent sp, @PathVariable String email) {
		Lekar lekar = lekarService.findByEmail(email);
		MedSestra meds = medSestraService.findByEmail(email);
		
		//System.out.println(sp.getIme()+sp.getPrezime());
		List<Pacijent> pacijenti = pacijentService.findByImeAndPrezimeAndLbo(sp.getIme().toUpperCase(), sp.getPrezime().toUpperCase(), sp.getLbo().toUpperCase());

		// convert Lekars to DTOs
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent s : pacijenti) {
			 if((lekar != null && lekar.getKlinika().getPacijent().contains(s))
					   ||( meds != null && meds.getKlinika().getPacijent().contains(s))) {
				 	pacijentiDTO.add(new PacijentDTO(s));
			 }
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{lbo}")
	@PreAuthorize("hasAnyRole( 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<PacijentDTO> getPacijentByLbo(@PathVariable String lbo) {

		Pacijent Pacijents = pacijentService.findByLbo(lbo);
		PacijentDTO PacijentsDTO = new PacijentDTO(Pacijents);
		
		return new ResponseEntity<>(PacijentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "promenaLozinke/{id}/{novaLozinka}")
	@PreAuthorize("hasAnyRole('PACIJENT')")
	public ResponseEntity<PacijentDTO> updatePacijentLozinka(@PathVariable Integer id, @PathVariable String novaLozinka) {

		Pacijent l = pacijentService.findOne(id);

		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		l.setLozinka(pacijentService.encodePassword(novaLozinka));
		l.setPromenioLozinku(true);
		try {
			l = pacijentService.save(l);
			return new ResponseEntity<>(new PacijentDTO(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	

}