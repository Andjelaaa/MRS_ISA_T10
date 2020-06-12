package main.mrs.isa.controller;

import java.util.ArrayList;
import java.util.Date;
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

import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
import main.mrs.dto.OdsustvoDTO;
import main.mrs.dto.OperacijaDTO;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Odsustvo;
import main.mrs.model.Status;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.EmailService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.OdsustvoService;;
@RestController
@RequestMapping(value="api/zahteviodsustvo")
public class OdsustvoController {
	
	@Autowired
	private OdsustvoService OdsustvoService;
	
	@Autowired
	private MedSestraService MedSestraService;
	
	@Autowired
	private LekarService LekarService;
	
	@Autowired
	private EmailService EmailService;
	
	@Autowired
	private AdminKlinikeService AdminKlinikeService;
	
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('ADMIN_KLINIKE', 'LEKAR','MED_SESTRA')")
	public ResponseEntity<List<OdsustvoDTO>> getAllOdsustva() {

		List<Odsustvo> odsustva = OdsustvoService.findAll();

		List<OdsustvoDTO> odsustvaDTO = new ArrayList<>();
		for (Odsustvo o : odsustva) {
			OdsustvoDTO oDTO = new OdsustvoDTO(o);
			if(o.getLekar().getIme() != null) {
				oDTO.setLekar(new LekarDTO(o.getLekar()));
			}
			else {
				oDTO.setSestra(new MedSestraDTO(o.getSestra()));
			}
			odsustvaDTO.add(oDTO);			
		}

		return new ResponseEntity<>(odsustvaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/all/zahtevi/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<OdsustvoDTO>> getAllZahteviOdsustva(@PathVariable Integer idAdmina) {

		List<Odsustvo> odsustva = OdsustvoService.findAllZahtevi();
		AdminKlinike ak = AdminKlinikeService.findOne(idAdmina);
		List<OdsustvoDTO> odsustvaDTO = new ArrayList<>();
		for (Odsustvo o : odsustva) {
			if((o.getLekar() != null && o.getLekar().getKlinika().getId() == ak.getKlinika().getId()) || 
					(o.getSestra() != null && o.getSestra().getKlinika().getId() == ak.getKlinika().getId())) {
				OdsustvoDTO oDTO = new OdsustvoDTO(o);
				if(o.getLekar() != null) {
					oDTO.setLekar(new LekarDTO(o.getLekar()));
				}
				else {
					oDTO.setSestra(new MedSestraDTO(o.getSestra()));
				}
				odsustvaDTO.add(oDTO);	
			}
		}

		return new ResponseEntity<>(odsustvaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", value= "/{email}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	@Transactional
	public ResponseEntity<OdsustvoDTO> saveOdsustvo(@RequestBody OdsustvoDTO OdsustvoDTO, @PathVariable String email) {
	
		//ne moze u proslosti da bude pcoetak
		if(OdsustvoDTO.getPocetak().before(new Date()))
			return new ResponseEntity<>(new OdsustvoDTO(), HttpStatus.BAD_REQUEST);
		//ne moze kraj pre pocetka ostalo ne mora
		else if(OdsustvoDTO.getKraj().before(OdsustvoDTO.getPocetak()))
			return new ResponseEntity<>(new OdsustvoDTO(), HttpStatus.BAD_REQUEST);
		
		Odsustvo zahtev = new Odsustvo();
		zahtev.setTip(OdsustvoDTO.getTip());
		zahtev.setStatus(Status.zahtev);
		zahtev.setPocetak(OdsustvoDTO.getPocetak());
		zahtev.setKraj(OdsustvoDTO.getKraj());
        zahtev.setOpis(OdsustvoDTO.getOpis());
		MedSestra m = MedSestraService.findByEmail(email);
		
		if(m != null) {
			zahtev.setSestra(m);
			m.getOdsustvo().add(zahtev);
			zahtev.setLekar(new Lekar());
		}
		
		Lekar l = LekarService.findByEmail(email);
		
		if(l != null) {
			zahtev.setLekar(l);
			l.getOdsustvo().add(zahtev);
			zahtev.setSestra(new MedSestra());
		}
		
		try {
			zahtev = OdsustvoService.save(zahtev);
		} catch (Exception e) {
			return new ResponseEntity<>(new OdsustvoDTO(zahtev), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OdsustvoDTO(zahtev), HttpStatus.CREATED);
	}
	
	@PostMapping(consumes = "application/json", value= "/odobri")
	@PreAuthorize("hasRole( 'ADMIN_KLINIKE')")
	public ResponseEntity<OdsustvoDTO> odobriOdsustvo(@RequestBody OdsustvoDTO OdsustvoDTO) {
	
		Odsustvo o = OdsustvoService.findOne(OdsustvoDTO.getId());
		
		o.setStatus(Status.odobreno);
		
		if(o.getLekar().getIme() == null) {
			// mejl sestri
			EmailService.posaljiOdobrenoOdsustvo(o.getSestra().getEmail(), o);
		}
		else {
			// mejl lekaru
			EmailService.posaljiOdobrenoOdsustvo(o.getLekar().getEmail(), o);
		}
				
		try {
			o = OdsustvoService.save(o);
		} catch (Exception e) {
			return new ResponseEntity<>(new OdsustvoDTO(o), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OdsustvoDTO(o), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", value= "/odbij/{obrazlozenje}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<OdsustvoDTO> odbijOdsustvo(@RequestBody OdsustvoDTO OdsustvoDTO, @PathVariable String obrazlozenje) {
	
		Odsustvo o = OdsustvoService.findOne(OdsustvoDTO.getId());
		
		o.setStatus(Status.odbijeno);
		
		if(o.getLekar().getIme() == null) {
			// mejl sestri
			EmailService.posaljiOdbijenoOdsustvo(o.getSestra().getEmail(), o, obrazlozenje);
		}
		else {
			// mejl lekaru
			EmailService.posaljiOdbijenoOdsustvo(o.getLekar().getEmail(), o, obrazlozenje);
		}
				
		try {
			o = OdsustvoService.save(o);
		} catch (Exception e) {
			return new ResponseEntity<>(new OdsustvoDTO(o), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OdsustvoDTO(o), HttpStatus.OK);
	}
	
	
}
