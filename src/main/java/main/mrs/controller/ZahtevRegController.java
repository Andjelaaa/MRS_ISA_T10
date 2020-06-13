package main.mrs.controller;

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

import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
import main.mrs.dto.ZahtevRegDTO;
import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.model.ZahtevReg;
import main.mrs.service.AdminKCService;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.PacijentService;
import main.mrs.service.ZahtevRegService;

@RestController
@RequestMapping(value="api/zahtevreg")
public class ZahtevRegController {
	@Autowired
	private AdminKCService AdminKCService;
	@Autowired
	private PacijentService PacijentService;


	@Autowired
	private AdminKlinikeService AdminKlinikeService;
	@Autowired
	private LekarService LekarService;
	@Autowired
	private MedSestraService MedSestraService;
	@Autowired
	private ZahtevRegService ZahtevRegService;

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')") 
	public ResponseEntity<List<ZahtevRegDTO>> getAllZahtevRegs() {

		List<ZahtevReg> ZahtevRegs = ZahtevRegService.findAll();

		// convert ZahtevRegs to DTOs
		List<ZahtevRegDTO> ZahtevRegsDTO = new ArrayList<>();
		for (ZahtevReg s : ZahtevRegs) {
			ZahtevRegsDTO.add(new ZahtevRegDTO(s));
		}

		return new ResponseEntity<>(ZahtevRegsDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	
	public ResponseEntity<ZahtevRegDTO> saveZahtevReg(@RequestBody ZahtevRegDTO ZahtevRegDTO) {
		
		Pacijent pacijent = PacijentService.findByEmail(ZahtevRegDTO.getEmail());
		if(pacijent != null) {
			return new ResponseEntity<>(new ZahtevRegDTO(),HttpStatus.BAD_REQUEST);
	    }

		AdminKC akc = AdminKCService.findByEmail(ZahtevRegDTO.getEmail());
		if(akc != null) {
			return new ResponseEntity<>(new ZahtevRegDTO(),HttpStatus.BAD_REQUEST);
		}
		 AdminKlinike l = AdminKlinikeService.findByEmail(ZahtevRegDTO.getEmail());
		if(l != null) {
			return new ResponseEntity<>(new ZahtevRegDTO(),HttpStatus.BAD_REQUEST);
		}
		Lekar ms = LekarService.findByEmail(ZahtevRegDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new ZahtevRegDTO(),HttpStatus.BAD_REQUEST);
		}
		MedSestra n = MedSestraService.findByEmail(ZahtevRegDTO.getEmail());
		if(n != null) {
			return new ResponseEntity<>(new ZahtevRegDTO(),HttpStatus.BAD_REQUEST);
		}
		
		
		ZahtevReg zahtevReg = new ZahtevReg();
		zahtevReg.setAdresa(ZahtevRegDTO.getAdresa());
		zahtevReg.setDrzava(ZahtevRegDTO.getDrzava());
		zahtevReg.setEmail(ZahtevRegDTO.getEmail());
		zahtevReg.setGrad(ZahtevRegDTO.getGrad());
		zahtevReg.setIme(ZahtevRegDTO.getIme());
		zahtevReg.setKontakt(ZahtevRegDTO.getKontakt());
		zahtevReg.setLozinka(ZahtevRegDTO.getLozinka());
		zahtevReg.setPrezime(ZahtevRegDTO.getPrezime());
		zahtevReg.setLbo(ZahtevRegDTO.getLbo());
		
		//ovde mi proveravaaj sve validne podatke sa svim korisnicima
		try {
			
			zahtevReg = ZahtevRegService.save(zahtevReg);
		} catch (Exception e) {
			return new ResponseEntity<>(new ZahtevRegDTO(zahtevReg), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ZahtevRegDTO(zahtevReg), HttpStatus.CREATED);
	}
}
