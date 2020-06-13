package main.mrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import main.mrs.dto.KorisnikDTO;
import main.mrs.dto.KorisnikTokenDTO;
import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
import main.mrs.dto.PacijentDTO;
import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Autoritet;
import main.mrs.model.Korisnik;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Pacijent;
import main.mrs.security.TokenUtils;
import main.mrs.security.auth.JwtAuthenticationRequest;
import main.mrs.service.AdminKCService;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.PacijentService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	@Autowired
	private AdminKCService adminKCService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private MedSestraService medSestraService;	


	@GetMapping(value = "/dobaviUlogovanog")
	public ResponseEntity<KorisnikDTO> dobaviUlogovanog() {
		
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		
		Pacijent ulogovan = (Pacijent) pacijentService.findByEmail(trenutniKorisnik.getName());
		if(ulogovan != null) {
			PacijentDTO korisnikDTO = new PacijentDTO(ulogovan);
			return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
		}
		AdminKlinike ak = (AdminKlinike) adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		if(ak != null) {
			AdminKlinikeDTO korisnikDTO = new AdminKlinikeDTO(ak);
			return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
		}
		AdminKC akc = (AdminKC) adminKCService.findByEmail(trenutniKorisnik.getName());
		if(akc != null) {
			AdminKCDTO korisnikDTO = new AdminKCDTO(akc);
			return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
		}
		Lekar l = (Lekar) lekarService.findByEmail(trenutniKorisnik.getName());
		if(l != null) {
			LekarDTO korisnikDTO = new LekarDTO(l);
			return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
		}
		MedSestra ms = (MedSestra) medSestraService.findByEmail(trenutniKorisnik.getName());
		if(ms != null) {
			MedSestraDTO korisnikDTO = new MedSestraDTO(ms);
			return new ResponseEntity<>(korisnikDTO, HttpStatus.OK);
		}		
		return new ResponseEntity<>(new KorisnikDTO(), HttpStatus.BAD_REQUEST);
	}

	
	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<KorisnikTokenDTO> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest) {

		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.ok(new KorisnikTokenDTO());
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Korisnik korisnik = (Korisnik) authentication.getPrincipal();
		
		String jwt = tokenUtils.generateToken(korisnik.getEmail());
		int expiresIn = tokenUtils.getExpiredIn();

		return ResponseEntity.ok(new KorisnikTokenDTO(jwt, expiresIn));
	}
	
	@PutMapping(value = "dobaviulogu", consumes = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<String> dobaviUlogu(@RequestBody KorisnikDTO korisnikDTO) {

		Pacijent korisnik = pacijentService.findByEmail(korisnikDTO.getEmail());
		if(korisnik != null) {
			Autoritet a = korisnik.getAutoriteti().get(0);
			return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
		}
		AdminKlinike ak = adminKlinikeService.findByEmail(korisnikDTO.getEmail());
		if(ak != null) {
			Autoritet a = ak.getAutoriteti().get(0);
			return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
		}
		
		AdminKC akc = adminKCService.findByEmail(korisnikDTO.getEmail());
		if(akc != null) {
			Autoritet a = akc.getAutoriteti().get(0);
			return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
		}
		Lekar l = lekarService.findByEmail(korisnikDTO.getEmail());
		if(l != null) {
			Autoritet a = l.getAutoriteti().get(0);
			return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
		}
		MedSestra ms = medSestraService.findByEmail(korisnikDTO.getEmail());
		if(ms != null) {
			Autoritet a = ms.getAutoriteti().get(0);
			return new ResponseEntity<>(a.getIme(), HttpStatus.OK);
		}
		return null;
		
	}




}
