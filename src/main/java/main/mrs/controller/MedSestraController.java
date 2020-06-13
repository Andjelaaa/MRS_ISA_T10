package main.mrs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
import main.mrs.dto.SearchLekar;
import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.service.AdminKCService;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.AutoritetService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.PacijentService;


@RestController
@RequestMapping(value="api/medsestraa")
public class MedSestraController {
	@Autowired
	private MedSestraService MedSestraService;
	
	@Autowired
	private PacijentService PacijentService;
	@Autowired
	private AutoritetService autoritetService;
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	

	@Autowired
	private AdminKlinikeService AdminKlinikeService;

	@Autowired
	private AdminKCService AdminKCService;
	@Autowired
	private LekarService LekarService;

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<MedSestraDTO>> getAllMedSestrs() {

		List<MedSestra> sestre =  MedSestraService.findAll();

		List<MedSestraDTO> medsDTO = new ArrayList<>();
		for (MedSestra s : sestre) {
			medsDTO.add(new MedSestraDTO(s));
		}

		return new ResponseEntity<>(medsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/all/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<MedSestraDTO>> getAllMedSestrs(@PathVariable Integer idAdmina) {
		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		List<MedSestra> sestre = MedSestraService.findAllByIdKlinike(ak.getKlinika().getId());

		List<MedSestraDTO> medsDTO = new ArrayList<>();
		for (MedSestra s : sestre) {
			medsDTO.add(new MedSestraDTO(s));
		}

		return new ResponseEntity<>(medsDTO, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping(consumes = "application/json", value="/{idAdmina}")
    @PreAuthorize("hasRole('ADMIN_KLINIKE')") 
	public ResponseEntity<MedSestraDTO> saveSestra(@RequestBody MedSestraDTO MedSestraDTO, @PathVariable Integer idAdmina) {

		
		Pacijent pacijent = PacijentService.findByEmail(MedSestraDTO.getEmail());
		 if(pacijent != null) {
			return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
	     }

		AdminKC akc = AdminKCService.findByEmail(MedSestraDTO.getEmail());
		if(akc != null) {
			return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
		}
		 AdminKlinike l = AdminKlinikeService.findByEmail(MedSestraDTO.getEmail());
		if(l != null) {
			return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
		}
		Lekar ms = LekarService.findByEmail(MedSestraDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
		}
		 
		MedSestra m = new MedSestra();
		m.setIme(MedSestraDTO.getIme());
		m.setPrezime(MedSestraDTO.getPrezime());
		m.setEmail(MedSestraDTO.getEmail());
		m.setLozinka(PacijentService.encodePassword(MedSestraDTO.getLozinka()));
		m.setGrad(MedSestraDTO.getGrad());
		m.setAdresa(MedSestraDTO.getAdresa());
		m.setDrzava(MedSestraDTO.getDrzava());
		m.setKontakt(MedSestraDTO.getKontakt());
		m.setRadvr_pocetak(MedSestraDTO.getRadvr_pocetak());
		m.setRadvr_kraj(MedSestraDTO.getRadvr_kraj());				
		m.setAutoriteti(autoritetService.findByName("ROLE_MED_SESTRA"));
		m.setPromenioLozinku(false);
		
		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		m.setKlinika(ak.getKlinika());
		
		try {
			m = MedSestraService.save(m);
		} catch (Exception e) {
			return new ResponseEntity<>(new MedSestraDTO(m), HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(new MedSestraDTO(), HttpStatus.CREATED);
	
	 	}
	 
	 @PostMapping(value = "/search/{idAdmina}")
	 @PreAuthorize("hasRole('ADMIN_KLINIKE')")
		public ResponseEntity<List<MedSestraDTO>> getSearchMedSestras(@RequestBody SearchLekar sl, @PathVariable Integer idAdmina) {
		 	AdminKlinike ak = adminKlinikeService.findOne(idAdmina);		 	
			List<MedSestra> sestre = MedSestraService.findByImeAndPrezime(sl.getIme().toUpperCase(), sl.getPrezime().toUpperCase());

			List<MedSestraDTO> sestreDTO = new ArrayList<>();
			for (MedSestra s : sestre) {
				if(s.getKlinika().getId() == ak.getKlinika().getId())
					sestreDTO.add(new MedSestraDTO(s));
			}

			return new ResponseEntity<>(sestreDTO, HttpStatus.OK);
		}
	 
		@Transactional // obavezno ova anotacija, inace puca
		@DeleteMapping(value = "/{id}")
		@PreAuthorize("hasRole('ADMIN_KLINIKE')")
		public ResponseEntity<Void> deleteMedSestra(@PathVariable Integer id) {
			MedSestra ms = MedSestraService.findOne(id);

			if (ms != null) {
				MedSestraService.remove(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		@PutMapping(consumes = "application/json", value = "/{id}")
		@PreAuthorize("hasRole('MED_SESTRA')")
		public ResponseEntity<MedSestraDTO> updateMedSestra(@RequestBody MedSestraDTO msDTO, @PathVariable Integer id) {

			MedSestra ms = MedSestraService.findOne(id);

			if (ms == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Pacijent pacijent = PacijentService.findByEmail(msDTO.getEmail());
			 if(pacijent != null) {
				return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
		     }

			AdminKC akc = AdminKCService.findByEmail(msDTO.getEmail());
			if(akc != null) {
				return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
			}
			 AdminKlinike l = AdminKlinikeService.findByEmail(msDTO.getEmail());
			if(l != null) {
				return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
			}
			Lekar mss = LekarService.findByEmail(msDTO.getEmail());
			if(mss != null) {
				return new ResponseEntity<>(new MedSestraDTO(),HttpStatus.BAD_REQUEST);
			}

			ms.setIme(msDTO.getIme());
			ms.setPrezime(msDTO.getPrezime());
			ms.setEmail(msDTO.getEmail());
			ms.setAdresa(msDTO.getAdresa());
			ms.setGrad(msDTO.getGrad());
			ms.setDrzava(msDTO.getDrzava());
			ms.setKontakt(msDTO.getKontakt());
			try {
				ms = MedSestraService.save(ms);
				return new ResponseEntity<>(new MedSestraDTO(), HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		@GetMapping(value = "promenaLozinke/{id}/{novaLozinka}")
		@PreAuthorize("hasAnyRole('MED_SESTRA')")
		public ResponseEntity<MedSestraDTO> updateMedSestraLozinka(@PathVariable Integer id, @PathVariable String novaLozinka) {

			MedSestra ms = MedSestraService.findOne(id);

			if (ms == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			ms.setLozinka(PacijentService.encodePassword(novaLozinka));
			ms.setPromenioLozinku(true);

			try {
				ms = MedSestraService.save(ms);
				return new ResponseEntity<>(new MedSestraDTO(), HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
		}
		 
}
