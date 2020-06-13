package main.mrs.controller;

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

import main.mrs.dto.AdminKCDTO;
import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.model.PomocnaKlasa;
import main.mrs.model.ZahtevReg;
import main.mrs.service.AdminKCService;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.EmailService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.PacijentService;
import main.mrs.service.ZahtevRegService;


@RestController
@RequestMapping(value = "api/adminkc")
public class AdminKCController {

	@Autowired
	private AdminKCService adminKCService;
	
	@Autowired
	private ZahtevRegService zahtevService;


	@Autowired
	private AdminKlinikeService AdminKlinikeService;

	@Autowired
	private LekarService LekarService;

	@Autowired
	private MedSestraService MedSestraService;
	

	@Autowired
	private EmailService emailService;

	@Autowired
	private PacijentService pacijentService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<AdminKCDTO>> getAllAdminKCs() {

		List<AdminKC> AdminKCs = adminKCService.findAll();

		// convert AdminKCs to DTOs
		List<AdminKCDTO> AdminKCsDTO = new ArrayList<>();
		for (AdminKC s : AdminKCs) {
			AdminKCsDTO.add(new AdminKCDTO(s));
		}

		return new ResponseEntity<>(AdminKCsDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminKCDTO> saveAdminKC(@RequestBody AdminKCDTO AdminKCDTO) {
 
		
		Pacijent pacijent = pacijentService.findByEmail(AdminKCDTO.getEmail());
		if(pacijent != null) {
			return new ResponseEntity<>(new AdminKCDTO(),HttpStatus.BAD_REQUEST);
		}

		AdminKlinike akc = AdminKlinikeService.findByEmail(AdminKCDTO.getEmail());
		if(akc != null) {
			return new ResponseEntity<>(new AdminKCDTO(),HttpStatus.BAD_REQUEST);
		}
		Lekar l = LekarService.findByEmail(AdminKCDTO.getEmail());
		if(l != null) {
			return new ResponseEntity<>(new AdminKCDTO(),HttpStatus.BAD_REQUEST);
		}
		MedSestra ms = MedSestraService.findByEmail(AdminKCDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new AdminKCDTO(),HttpStatus.BAD_REQUEST);
		}
		
		
		
		AdminKC AdminKC = new AdminKC();
		AdminKC.setIme(AdminKCDTO.getIme());
		AdminKC.setPrezime(AdminKCDTO.getPrezime());
		AdminKC.setEmail(AdminKCDTO.getEmail());
		AdminKC.setLozinka(AdminKCDTO.getLozinka());
		AdminKC.setGrad(AdminKCDTO.getGrad());
		AdminKC.setAdresa(AdminKCDTO.getAdresa());
		AdminKC.setDrzava(AdminKCDTO.getDrzava());
		
        try {
        	AdminKC = adminKCService.save(AdminKC);
        }catch(Exception e) {
        	return new ResponseEntity<>(new AdminKCDTO(), HttpStatus.BAD_REQUEST);
        }
		
		return new ResponseEntity<>(new AdminKCDTO(AdminKC), HttpStatus.CREATED);
	}
	
	@PostMapping(value= "/denied",  consumes="application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
   	public ResponseEntity deniedRegAsync(@RequestBody PomocnaKlasa data){
		ZahtevReg user = new ZahtevReg();
		
		user.setAdresa(data.user.getAdresa());
		user.setIme(data.user.getIme());
		user.setPrezime(data.user.getPrezime());
		user.setEmail(data.user.getEmail());
		user.setKontakt(data.user.getKontakt());
		user.setGrad(data.user.getGrad());
		user.setLozinka(data.user.getLozinka());
		user.setDrzava(data.user.getDrzava());
		user.setLbo(data.user.getLbo());
		
		
		String opis = data.opis;
		try {
			zahtevService.delete(user);
			emailService.sendNotificaitionDeniedAsync(user, opis);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch( Exception e ){
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "promenaLozinke/{id}/{novaLozinka}")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminKCDTO> updateAdminKCLozinka(@PathVariable Integer id, @PathVariable String novaLozinka) {

		AdminKC l = adminKCService.findOne(id);

		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		l.setLozinka(pacijentService.encodePassword(novaLozinka));
		l.setPromenioLozinku(true);
		try {
			l = adminKCService.save(l);
			return new ResponseEntity<>(new AdminKCDTO(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
