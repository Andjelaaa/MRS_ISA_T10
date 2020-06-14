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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.OcenaKlinikaDTO;
import main.mrs.dto.OcenaLekarDTO;
import main.mrs.model.Klinika;
import main.mrs.model.Lekar;
import main.mrs.model.OcenaKlinika;
import main.mrs.model.OcenaLekar;
import main.mrs.model.Pacijent;
import main.mrs.service.KlinikaService;
import main.mrs.service.LekarService;
import main.mrs.service.OcenaKlinikaService;
import main.mrs.service.PacijentService;

@RestController
@RequestMapping(value="api/ocenaklinika")
public class OcenaKlinikaController {

	@Autowired
	private OcenaKlinikaService OcenaKlinikaService;
	
	@Autowired
	private KlinikaService KlinikaService;
	
	@Autowired
	private LekarService LekarService;
	
	@Autowired
	private PacijentService PacijentService;


	@GetMapping(value = "/all")
	public ResponseEntity<List<OcenaKlinikaDTO>> getAllOcenaKlinika() {

		List<OcenaKlinika> ocene = OcenaKlinikaService.findAll();

		List<OcenaKlinikaDTO> OcenaKlinikasDTO = new ArrayList<>();
		for (OcenaKlinika o : ocene) {
			OcenaKlinikasDTO.add(new OcenaKlinikaDTO(o));
		}

		return new ResponseEntity<>(OcenaKlinikasDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/oceni/{lekarId}/{ocena}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<OcenaKlinikaDTO> oceniKliniku(@PathVariable int lekarId, @PathVariable int ocena) {
		// dobavi staru ocenu
		//System.out.println("Ovde cu da ocenjujem kliniku");
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Pacijent p = PacijentService.findByEmail(trenutniKorisnik.getName());
		Lekar lekar = LekarService.findById(lekarId);
		OcenaKlinika staraOcena = OcenaKlinikaService.findOcenu(lekar.getKlinika().getId(), p.getId());
		Klinika klinika = KlinikaService.findOne(lekar.getKlinika().getId());
		// izracunaj novu prosecnu ocenu klinike
				
		double prosecnaOcena = 0.0;
		if(staraOcena != null)
		{
			
			prosecnaOcena = (klinika.getProsecnaOcena()*klinika.getBrojOcena() - staraOcena.getOcena() + ocena) / klinika.getBrojOcena();
			staraOcena.setOcena(ocena);
			klinika.setProsecnaOcena(prosecnaOcena);
			// sacuvaj izmenjenu staru ocenu
			try {
				staraOcena = OcenaKlinikaService.save(staraOcena);
			}
			catch  (Exception e)
			{
				return  new ResponseEntity<>(new OcenaKlinikaDTO(staraOcena), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(new OcenaKlinikaDTO(staraOcena), HttpStatus.ACCEPTED);
		}
		else
		{
			prosecnaOcena = (klinika.getProsecnaOcena()*klinika.getBrojOcena() + ocena) / (klinika.getBrojOcena()+1);
			klinika.setProsecnaOcena(prosecnaOcena);
			klinika.setBrojOcena(klinika.getBrojOcena()+1);
			// dodaj u tabelu novo ocenjivanje
			OcenaKlinika novaOcena = new OcenaKlinika();
			novaOcena.setKlinikaId(lekar.getKlinika().getId());
			novaOcena.setPacijentId(p.getId());
			novaOcena.setOcena(ocena);
			System.out.println("NOVA ocena KLINIKE: " +  novaOcena.getOcena());
			try {
				novaOcena = OcenaKlinikaService.save(novaOcena);
			}
			catch(Exception e)
			{
				return  new ResponseEntity<>(new OcenaKlinikaDTO(staraOcena), HttpStatus.BAD_REQUEST);
			}
				
			return new ResponseEntity<>(new OcenaKlinikaDTO(novaOcena), HttpStatus.CREATED);
		}
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<OcenaKlinikaDTO> saveOceneKlinika(@RequestBody OcenaKlinikaDTO OcenaDTO) {

		OcenaKlinika ocena = new OcenaKlinika();
		ocena.setKlinikaId(OcenaDTO.getKlinikaId());
		ocena.setPacijentId(OcenaDTO.getPacijentId());
		ocena.setOcena(OcenaDTO.getOcena());

		
		try {
			ocena = OcenaKlinikaService.save(ocena);
		} catch (Exception e) {
			return new ResponseEntity<>(new OcenaKlinikaDTO(ocena), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OcenaKlinikaDTO(ocena), HttpStatus.CREATED);
	}
	
}
