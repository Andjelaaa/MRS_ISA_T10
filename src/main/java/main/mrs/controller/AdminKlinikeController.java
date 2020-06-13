package main.mrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.AdminKCDTO;
import main.mrs.dto.AdminKlinikeDTO;
import main.mrs.dto.KlinikaDTO;
import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
import main.mrs.dto.PacijentDTO;
import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Klinika;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.LekarService;
import main.mrs.service.PacijentService;
import main.mrs.service.KlinikaService;
import main.mrs.service.AdminKCService;
import main.mrs.service.MedSestraService;

@RestController
@RequestMapping(value="api/admini")
public class AdminKlinikeController {

	@Autowired
	private AdminKlinikeService AdminKlinikeService;
	@Autowired
	private PacijentService PacijentService;
	
	@Autowired
	private KlinikaService KlinikaService;
	
	@Autowired
	private AdminKCService AdminKCService;
	
	@Autowired
	private LekarService LekarService;
	@Autowired
	private MedSestraService MedSestraService;
	
	@PostMapping(consumes = "application/json", value = "/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminKlinikeDTO> saveKlinikaAdmin(@PathVariable Integer id,@RequestBody AdminKlinikeDTO AdminKlinikeDTO) {
		
		Pacijent pacijent = PacijentService.findByEmail(AdminKlinikeDTO.getEmail());
		if(pacijent != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}

		AdminKC akc = AdminKCService.findByEmail(AdminKlinikeDTO.getEmail());
		if(akc != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}
		Lekar l = LekarService.findByEmail(AdminKlinikeDTO.getEmail());
		if(l != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}
		MedSestra ms = MedSestraService.findByEmail(AdminKlinikeDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}
		
		
		AdminKlinike admin = new AdminKlinike();
		admin.setIme(AdminKlinikeDTO.getIme());
		admin.setPrezime(AdminKlinikeDTO.getPrezime());
		admin.setEmail(AdminKlinikeDTO.getEmail());
		admin.setKontakt(AdminKlinikeDTO.getKontakt());
		admin.setAdresa(AdminKlinikeDTO.getAdresa());
		admin.setGrad(AdminKlinikeDTO.getGrad());
		admin.setLozinka(PacijentService.encodePassword(AdminKlinikeDTO.getLozinka()));
		admin.setDrzava(AdminKlinikeDTO.getDrzava());
		Klinika kl = KlinikaService.findOne(id);
		admin.setKlinika(kl);
		
		try {
			admin = AdminKlinikeService.save(admin);
		} catch (Exception e) {
			return new ResponseEntity<>(new AdminKlinikeDTO(admin), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new AdminKlinikeDTO(admin), HttpStatus.CREATED);
	}
	
	
	@GetMapping(value="/klinika")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<KlinikaDTO> dobaviKlinikuAdmina(){
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		
		AdminKlinike ak = AdminKlinikeService.findByEmail(trenutniKorisnik.getName());
		Klinika Klinika = ak.getKlinika();
		System.out.println(Klinika.getNaziv());
		return new ResponseEntity<>(new KlinikaDTO(Klinika), HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", value = "/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> updateAdminKlinikeProfil(@RequestBody AdminKlinikeDTO lDTO, @PathVariable Integer id) {

		AdminKlinike l = AdminKlinikeService.findOne(id);

		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pacijent pacijent = PacijentService.findByEmail(lDTO.getEmail());
		if(pacijent != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}

		AdminKC akc = AdminKCService.findByEmail(lDTO.getEmail());
		if(akc != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}
		Lekar le = LekarService.findByEmail(lDTO.getEmail());
		if(le != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}
		MedSestra ms = MedSestraService.findByEmail(lDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new AdminKlinikeDTO(),HttpStatus.BAD_REQUEST);
		}

		l.setIme(lDTO.getIme());
		l.setPrezime(lDTO.getPrezime());
		l.setEmail(lDTO.getEmail());
		l.setAdresa(lDTO.getAdresa());
		l.setGrad(lDTO.getGrad());
		l.setDrzava(lDTO.getDrzava());
		l.setKontakt(lDTO.getKontakt());
		try {
			l = AdminKlinikeService.save(l);
			return new ResponseEntity<>(new AdminKlinikeDTO(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping(value = "promenaLozinke/{id}/{novaLozinka}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> updatePotvrdiLozinku(@PathVariable Integer id, @PathVariable String novaLozinka) {
      
		AdminKlinike l = AdminKlinikeService.findOne(id);

		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		l.setLozinka(PacijentService.encodePassword(novaLozinka));
		l.setPromenioLozinku(true);

		try {
			l = AdminKlinikeService.save(l);
			return new ResponseEntity<>(new AdminKlinikeDTO(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}


}
