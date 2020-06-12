package main.mrs.isa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.LekDTO;
import main.mrs.dto.OcenaLekarDTO;
import main.mrs.model.Lekar;
import main.mrs.model.OcenaLekar;
import main.mrs.service.LekarService;
import main.mrs.service.OcenaLekarService;

@RestController
@RequestMapping(value="api/ocenalekar")
public class OcenaLekarController {

	
	@Autowired
	private OcenaLekarService OcenaLekarService;
	
	@Autowired
	private LekarService LekarService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<OcenaLekarDTO>> getAllOcenaLekar() {

		List<OcenaLekar> ocene = OcenaLekarService.findAll();

		List<OcenaLekarDTO> OcenaLekarsDTO = new ArrayList<>();
		for (OcenaLekar o : ocene) {
			OcenaLekarsDTO.add(new OcenaLekarDTO(o));
		}

		return new ResponseEntity<>(OcenaLekarsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value="/oceni/{lekarId}/{pacijentId}/{ocena}")
	public ResponseEntity<OcenaLekarDTO> oceniLekara(@PathVariable int lekarId, @PathVariable int pacijentId, @PathVariable int ocena) {
		
		// dobavi staru ocenu
		OcenaLekar staraOcena = OcenaLekarService.findOcenu(lekarId, pacijentId);
		Lekar lekar = LekarService.findOne(lekarId);
		
		// izracunaj novu prosecnu ocenu lekara
		
		double prosecnaOcena = 0.0;
		if(staraOcena != null)
		{
			prosecnaOcena = (lekar.getProsecnaOcena()*lekar.getBrojOcena() - staraOcena.getOcena() + ocena) / lekar.getBrojOcena();
			staraOcena.setOcena(ocena);
			lekar.setProsecnaOcena(prosecnaOcena);
			// sacuvaj izmenjenu staru ocenu
			try {
				staraOcena = OcenaLekarService.save(staraOcena);
			}
			catch  (Exception e)
			{
				return  new ResponseEntity<>(new OcenaLekarDTO(staraOcena), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(new OcenaLekarDTO(staraOcena), HttpStatus.ACCEPTED);
		}
		else
		{
			prosecnaOcena = (lekar.getProsecnaOcena()*lekar.getBrojOcena() + ocena) / (lekar.getBrojOcena()+1);
			lekar.setProsecnaOcena(prosecnaOcena);
			lekar.setBrojOcena(lekar.getBrojOcena()+1);
			// dodaj u tabelu novo ocenjivanje
			OcenaLekar novaOcena = new OcenaLekar();
			novaOcena.setLekarId(lekarId);
			novaOcena.setPacijentId(pacijentId);
			novaOcena.setOcena(ocena);
			try {
				novaOcena = OcenaLekarService.save(novaOcena);
			}
			catch(Exception e)
			{
				return  new ResponseEntity<>(new OcenaLekarDTO(staraOcena), HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<>(new OcenaLekarDTO(staraOcena), HttpStatus.CREATED);
		}
		
		
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<OcenaLekarDTO> saveOceneLekar(@RequestBody OcenaLekarDTO OcenaDTO) {

		OcenaLekar ocena = new OcenaLekar();
		ocena.setLekarId(OcenaDTO.getLekarId());
		ocena.setPacijentId(OcenaDTO.getPacijentId());
		ocena.setOcena(OcenaDTO.getOcena());

		
		try {
			ocena = OcenaLekarService.save(ocena);
		} catch (Exception e) {
			return new ResponseEntity<>(new OcenaLekarDTO(ocena), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OcenaLekarDTO(ocena), HttpStatus.CREATED);
	}
	
}
