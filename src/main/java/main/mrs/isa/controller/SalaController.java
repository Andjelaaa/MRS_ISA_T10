package main.mrs.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.PregledDTO;
import main.mrs.dto.SalaDTO;
import main.mrs.dto.ZauzecaSlobodniDTO;
import main.mrs.dto.ZauzeceDTO;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Operacija;
import main.mrs.model.Pregled;
import main.mrs.model.Sala;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.OperacijaService;
import main.mrs.service.PregledService;
import main.mrs.service.SalaService;

@RestController
@RequestMapping(value="api/sala")
public class SalaController {

	@Autowired
	private SalaService SalaService;
	
	@Autowired
	private PregledService PregledService;
	
	
	@Autowired
	private OperacijaService OperacijaService;
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	private SimpleDateFormat sdf;

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getAllSalas() {

		List<Sala> Salas = SalaService.findAll();

		// convert Salas to DTOs
		List<SalaDTO> SalasDTO = new ArrayList<>();
		for (Sala s : Salas) {
			SalasDTO.add(new SalaDTO(s));
		}

		return new ResponseEntity<>(SalasDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/all/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getAllSalasByKlinika(@PathVariable Integer idAdmina) {
		
		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		List<Sala> Salas = SalaService.findAllByIdKlinike(ak.getKlinika().getId());
		

		// convert Salas to DTOs
		List<SalaDTO> SalasDTO = new ArrayList<>();
		for (Sala s : Salas) {
			SalasDTO.add(new SalaDTO(s));
		}

		return new ResponseEntity<>(SalasDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json", value="/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> saveSala(@RequestBody SalaDTO SalaDTO, @PathVariable Integer idAdmina) {

		Sala Sala = new Sala();
		Sala.setNaziv(SalaDTO.getNaziv());
		Sala.setBroj(SalaDTO.getBroj());
		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		Sala.setKlinika(ak.getKlinika());
		Sala.setIzmena(1);
		
		try {
			Sala = SalaService.save(Sala);
		} catch (Exception e) {
			return new ResponseEntity<>(new SalaDTO(), HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(new SalaDTO(Sala), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/search/{searchParam}/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSearchSala(@PathVariable String searchParam, @PathVariable Integer idAdmina) {
		System.out.println(searchParam);
		List<Sala> Salas = SalaService.findSearchNaziv(searchParam.toUpperCase());
		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);

		// convert Salas to DTOs
		List<SalaDTO> SalasDTO = new ArrayList<>();
		for (Sala s : Salas) {
			if(s.getKlinika().getId() == ak.getKlinika().getId())
				SalasDTO.add(new SalaDTO(s));
		}

		return new ResponseEntity<>(SalasDTO, HttpStatus.OK);
	}
	
	@Transactional // obavezno ova anotacija, inace puca
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Void> deleteSala(@PathVariable Integer id) {
		Sala Sala = SalaService.findOne(id);
		List<Operacija> Operacija = OperacijaService.findAllBySalaId(Sala.getId());
		try {
			if (Sala != null) {
				// Provera da li je sala zauzeta ili rezervisana (postoje pregledi vezani za tu salu
				if(!Sala.getPregled().isEmpty() || !Operacija.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				SalaService.remove(id);	
			}

		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", value = "/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<SalaDTO> updateSala(@RequestBody SalaDTO SalaDTO, @PathVariable Integer id) {

		// a Sala must exist
		Sala Sala = SalaService.findOne(id);
		List<Operacija> Operacija = OperacijaService.findAllBySalaId(Sala.getId());
		
		if (Sala == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		// Provera da li je sala zauzeta ili rezervisana (postoje pregledi vezani za tu salu
		if(!Sala.getPregled().isEmpty() || !Operacija.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Sala.setNaziv(SalaDTO.getNaziv());
		Sala.setBroj(SalaDTO.getBroj());
		try {
		Sala = SalaService.save(Sala);
		return new ResponseEntity<>(new SalaDTO(Sala), HttpStatus.OK);}
		catch(Exception e) {
			return new ResponseEntity<>(new SalaDTO(Sala), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/zauzece/{idPregleda}/{idSale}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<ZauzecaSlobodniDTO> getZauzeca(@PathVariable int idPregleda, @PathVariable int idSale) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Pregled pregled = PregledService.findById(idPregleda);
		System.out.println(pregled.getDatumVreme());
		Sala sala = SalaService.findOne(idSale);
		List<Pregled> Pregleds = PregledService.findAll();
		
		List<ZauzeceDTO> zauzecaDTO = new ArrayList<ZauzeceDTO>();
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		for(Pregled p: Pregleds) {
			if(p.getSala() != null) {
				if(p.getSala().getId() == sala.getId() && sdf.format(p.getDatumVreme()).equals(sdf.format(pregled.getDatumVreme()))) {
					
				    long curTimeInMs = p.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(p.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}
		List<Operacija> operacije = OperacijaService.findAll();
		for(Operacija o: operacije) {
			
			if(o.getSala() != null) {
				if(o.getSala().getId() == sala.getId() && sdf.format(o.getDatumVreme()).equals(sdf.format(pregled.getDatumVreme()))) {
					
				    long curTimeInMs = o.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (o.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(o.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}
		ZauzecaSlobodniDTO zs = new ZauzecaSlobodniDTO();
		zs.setZauzeca(zauzecaDTO);
		zs.setPrviSlobodan(pregled.getDatumVreme());

		return new ResponseEntity<>(zs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/slobodnesale/{idPregleda}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSlobodne(@PathVariable int idPregleda) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		Pregled pregled = PregledService.findById(idPregleda);
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		long curTimeInMs = pregled.getDatumVreme().getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (pregled.getTrajanje() * ONE_MINUTE_IN_MILLIS));
		
		
		System.out.println(pregled.getDatumVreme());
		List<Pregled> SviPregledi = PregledService.findAll();
		List<Sala> ZauzeteSale = new ArrayList<Sala>();
		
		List<Operacija> SveOperacije=  OperacijaService.findAll();
		for (Pregled p : SviPregledi) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(pregled.getDatumVreme())) || 
				(p.getDatumVreme().after(pregled.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(pregled.getDatumVreme()) && p.getDatumVreme().before(pregled.getDatumVreme())) ||
				(p.getDatumVreme().after(pregled.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		for (Operacija p : SveOperacije) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(pregled.getDatumVreme())) || 
				(p.getDatumVreme().after(pregled.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(pregled.getDatumVreme()) && p.getDatumVreme().before(pregled.getDatumVreme())) ||
				(p.getDatumVreme().after(pregled.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		List<Sala> slobodne = new ArrayList<Sala>();
		AdminKlinike ak = (AdminKlinike) adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		List<Sala> sveSale = SalaService.findAllByIdKlinike(ak.getKlinika().getId());
		for (Sala sala : sveSale) {
			if(!ZauzeteSale.contains(sala)) {
				System.out.println(sala.getNaziv());
				slobodne.add(sala);
			}
		}
		
		List<SalaDTO> SalasDTO = new ArrayList<>();
		for (Sala s : slobodne) {
			SalasDTO.add(new SalaDTO(s));
		}

		return new ResponseEntity<>(SalasDTO, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/prvislobodan/{datumStr}/{idSale}/{idPregleda}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<ZauzecaSlobodniDTO> getZauzecaZaDatum(@PathVariable String datumStr, @PathVariable Integer idSale, @PathVariable Integer idPregleda) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Pregled pregled = PregledService.findById(idPregleda);
		Sala sala = SalaService.findOne(idSale);
		List<Pregled> Pregleds = PregledService.findAll();
		List<Operacija> operacije = OperacijaService.findAll();
		Date datum = null;
		try {
			datum = sdf.parse(datumStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ne sme rezervisati za proslost
		if(datum.before(new Date()))
			return new ResponseEntity<>(new ZauzecaSlobodniDTO(), HttpStatus.BAD_REQUEST);
		
		List<ZauzeceDTO> zauzecaDTO = new ArrayList<ZauzeceDTO>();
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		for(Pregled p: Pregleds) {
			if(p.getSala() != null) {
				if(p.getSala().getId() == sala.getId() && sdf.format(p.getDatumVreme()).equals(sdf.format(datum))) {
					
				    long curTimeInMs = p.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(p.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}
		for(Operacija p: operacije) {
			if(p.getSala() != null) {
				if(p.getSala().getId() == sala.getId() && sdf.format(p.getDatumVreme()).equals(sdf.format(datum))) {
					
				    long curTimeInMs = p.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(p.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}

		
		Collections.sort(zauzecaDTO, new Comparator<ZauzeceDTO>() {
			@Override
			public int compare(ZauzeceDTO o1, ZauzeceDTO o2) {
				return o1.getPocetak().compareTo(o2.getPocetak());
			}         
        });		
		
		Date prviSlobodan = datum;
		prviSlobodan.setHours(pregled.getDatumVreme().getHours()-2);
		prviSlobodan.setMinutes(pregled.getDatumVreme().getMinutes());
		if(!zauzecaDTO.isEmpty()) {
			prviSlobodan = zauzecaDTO.get(0).getKraj();
			for (int i = 0; i<zauzecaDTO.size()-1;i++) {
				if(zauzecaDTO.get(i+1).getPocetak().getTime() - zauzecaDTO.get(i).getKraj().getTime() >= pregled.getTrajanje()*ONE_MINUTE_IN_MILLIS) {
					prviSlobodan = zauzecaDTO.get(i).getKraj();
					break;
				}			
			}
		}		
		ZauzecaSlobodniDTO retVal = new ZauzecaSlobodniDTO();
		retVal.setZauzeca(zauzecaDTO);
		prviSlobodan.setHours(prviSlobodan.getHours()+2);
		retVal.setPrviSlobodan(prviSlobodan);

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/zauzeceop/{idOperacije}/{idSale}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<ZauzecaSlobodniDTO> getZauzecaOP(@PathVariable Integer idOperacije, @PathVariable int idSale) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Operacija operacija = OperacijaService.findOne(idOperacije);
		Sala sala = SalaService.findOne(idSale);
		List<Pregled> Pregleds = PregledService.findAll();
		
		List<ZauzeceDTO> zauzecaDTO = new ArrayList<ZauzeceDTO>();
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		for(Pregled p: Pregleds) {
			if(p.getSala() != null) {
				if(p.getSala().getId() == sala.getId() && sdf.format(p.getDatumVreme()).equals(sdf.format(operacija.getDatumVreme()))) {
					
				    long curTimeInMs = p.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(p.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}
		List<Operacija> operacije = OperacijaService.findAll();
		for(Operacija o: operacije) {
			
			if(o.getSala() != null) {
				if(o.getSala().getId() == sala.getId() && sdf.format(o.getDatumVreme()).equals(sdf.format(operacija.getDatumVreme()))) {
					
				    long curTimeInMs = o.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (o.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(o.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}
		ZauzecaSlobodniDTO zs = new ZauzecaSlobodniDTO();
		zs.setZauzeca(zauzecaDTO);
		zs.setPrviSlobodan(operacija.getDatumVreme());

		return new ResponseEntity<>(zs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/slobodnesaleop/{idOperacije}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<SalaDTO>> getSlobodneOP(@PathVariable Integer idOperacije) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		Operacija operacija = OperacijaService.findOne(idOperacije);
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		long curTimeInMs = operacija.getDatumVreme().getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS));
		
		List<Pregled> SviPregledi = PregledService.findAll();
		List<Sala> ZauzeteSale = new ArrayList<Sala>();
		
		List<Operacija> SveOperacije=  OperacijaService.findAll();
		for (Pregled p : SviPregledi) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(operacija.getDatumVreme())) || 
				(p.getDatumVreme().after(operacija.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(operacija.getDatumVreme()) && p.getDatumVreme().before(operacija.getDatumVreme())) ||
				(p.getDatumVreme().after(operacija.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		for (Operacija p : SveOperacije) {
			long l = p.getDatumVreme().getTime();
		    Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if((p.getDatumVreme().equals(operacija.getDatumVreme())) || 
				(p.getDatumVreme().after(operacija.getDatumVreme()) && p.getDatumVreme().before(afterAddingMins)) ||
				(krajPregleda.after(operacija.getDatumVreme()) && p.getDatumVreme().before(operacija.getDatumVreme())) ||
				(p.getDatumVreme().after(operacija.getDatumVreme()) && krajPregleda.before(afterAddingMins))){
					ZauzeteSale.add(p.getSala());				
				}
		}
		List<Sala> slobodne = new ArrayList<Sala>();
		AdminKlinike ak = (AdminKlinike) adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		List<Sala> sveSale = SalaService.findAllByIdKlinike(ak.getKlinika().getId());
		for (Sala sala : sveSale) {
			if(!ZauzeteSale.contains(sala)) {
				System.out.println(sala.getNaziv());
				slobodne.add(sala);
			}
		}
		
		List<SalaDTO> SalasDTO = new ArrayList<>();
		for (Sala s : slobodne) {
			SalasDTO.add(new SalaDTO(s));
		}

		return new ResponseEntity<>(SalasDTO, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/prvislobodanop/{datumStr}/{idSale}/{idOperacije}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<ZauzecaSlobodniDTO> getZauzecaZaDatumOP(@PathVariable String datumStr, @PathVariable Integer idSale,
			@PathVariable Integer idOperacije) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		Operacija operacija = OperacijaService.findOne(idOperacije);
		Sala sala = SalaService.findOne(idSale);
		List<Pregled> Pregleds = PregledService.findAll();
		List<Operacija> operacije = OperacijaService.findAll();
		Date datum = null;
		try {
			datum = sdf.parse(datumStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// ne sme rezervisati za proslost
		if(datum.before(new Date()))
			return new ResponseEntity<>(new ZauzecaSlobodniDTO(), HttpStatus.BAD_REQUEST);
		
		List<ZauzeceDTO> zauzecaDTO = new ArrayList<ZauzeceDTO>();
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		for(Pregled p: Pregleds) {
			if(p.getSala() != null) {
				if(p.getSala().getId() == sala.getId() && sdf.format(p.getDatumVreme()).equals(sdf.format(datum))) {
					
				    long curTimeInMs = p.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(p.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}
		for(Operacija p: operacije) {
			if(p.getSala() != null) {
				if(p.getSala().getId() == sala.getId() && sdf.format(p.getDatumVreme()).equals(sdf.format(datum))) {
					
				    long curTimeInMs = p.getDatumVreme().getTime();
				    Date afterAddingMins = new Date(curTimeInMs + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
					ZauzeceDTO z = new ZauzeceDTO(p.getDatumVreme(), afterAddingMins);
					zauzecaDTO.add(z);
				}
			}
		}

		
		Collections.sort(zauzecaDTO, new Comparator<ZauzeceDTO>() {
			@Override
			public int compare(ZauzeceDTO o1, ZauzeceDTO o2) {
				return o1.getPocetak().compareTo(o2.getPocetak());
			}         
        });		
		
		Date prviSlobodan = datum;
		prviSlobodan.setHours(operacija.getDatumVreme().getHours()-2);
		prviSlobodan.setMinutes(operacija.getDatumVreme().getMinutes());
		if(!zauzecaDTO.isEmpty()) {
			prviSlobodan = zauzecaDTO.get(0).getKraj();
			for (int i = 0; i<zauzecaDTO.size()-1;i++) {
				if(zauzecaDTO.get(i+1).getPocetak().getTime() - zauzecaDTO.get(i).getKraj().getTime() >= operacija.getTrajanje()*ONE_MINUTE_IN_MILLIS) {
					prviSlobodan = zauzecaDTO.get(i).getKraj();
					break;
				}			
			}
		}		
		ZauzecaSlobodniDTO retVal = new ZauzecaSlobodniDTO();
		retVal.setZauzeca(zauzecaDTO);
		prviSlobodan.setHours(prviSlobodan.getHours()+2);
		retVal.setPrviSlobodan(prviSlobodan);

		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	
}
