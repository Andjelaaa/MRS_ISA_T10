package main.mrs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import main.mrs.dto.AdminKlinikeDTO;
import main.mrs.dto.LekarDTO;
import main.mrs.dto.MedSestraDTO;
import main.mrs.dto.SearchLekar;
import main.mrs.model.AdminKC;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.MedSestra;
import main.mrs.model.Operacija;
import main.mrs.model.Pacijent;
import main.mrs.model.PomocnaKlasa5;
import main.mrs.model.PomocnaKlasa6;
import main.mrs.model.Pregled;
import main.mrs.model.TipPregleda;
import main.mrs.service.AdminKCService;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.AutoritetService;
import main.mrs.service.LekarService;
import main.mrs.service.MedSestraService;
import main.mrs.service.OperacijaService;
import main.mrs.service.PacijentService;
import main.mrs.service.PregledService;
import main.mrs.service.TipPregledaService;

@RestController
@RequestMapping(value="api/lekar")
public class LekarContoller {
	private SimpleDateFormat sdf;
	
	@Autowired
	private LekarService LekarService;
	@Autowired
	private OperacijaService OperacijaService;
	@Autowired
	private PregledService PregledService;
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
	private MedSestraService MedSestraService;
	
	@Autowired
	TipPregledaService tps = new TipPregledaService();	

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> getAllLekars() {

		List<Lekar> Lekars = LekarService.findAll();

		// convert Lekars to DTOs
		List<LekarDTO> LekarsDTO = new ArrayList<>();
		for (Lekar s : Lekars) {
			LekarsDTO.add(new LekarDTO(s));
		}

		return new ResponseEntity<>(LekarsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/all/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> getAllLekariKlinike(@PathVariable Integer idAdmina) {

		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		List<Lekar> Lekars = LekarService.findAllByIdKlinike(ak.getKlinika().getId());
		
		// convert Lekars to DTOs
		List<LekarDTO> LekarsDTO = new ArrayList<>();
		for (Lekar s : Lekars) {
			LekarsDTO.add(new LekarDTO(s));
		}

		return new ResponseEntity<>(LekarsDTO, HttpStatus.OK);
	}

	 @Transactional
	@PostMapping(consumes = "application/json", value="/{IdAdmina}")
	 @PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<LekarDTO> saveLekar(@RequestBody LekarDTO LekarDTO, @PathVariable Integer IdAdmina) {

		 Pacijent pacijent = PacijentService.findByEmail(LekarDTO.getEmail());
		 if(pacijent != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		 }

		 AdminKC akc = AdminKCService.findByEmail(LekarDTO.getEmail());
		 if(akc != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		}
		 AdminKlinike l = AdminKlinikeService.findByEmail(LekarDTO.getEmail());
		if(l != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		}
		MedSestra ms = MedSestraService.findByEmail(LekarDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		}
		 

		Lekar Lekar = new Lekar();
		Lekar.setIme(LekarDTO.getIme());
		Lekar.setPrezime(LekarDTO.getPrezime());
		Lekar.setEmail(LekarDTO.getEmail());
		Lekar.setLozinka(PacijentService.encodePassword(LekarDTO.getLozinka()));
		Lekar.setGrad(LekarDTO.getGrad());
		Lekar.setAdresa(LekarDTO.getAdresa());
		Lekar.setDrzava(LekarDTO.getDrzava());
		Lekar.setRvPocetak(LekarDTO.getRvPocetak());
		Lekar.setRvKraj(LekarDTO.getRvKraj());		
		Lekar.setKontakt(LekarDTO.getKontakt());
		Lekar.setProsecnaOcena(0.0);
		Lekar.setBrojOcena(0);
		TipPregleda tp= tps.findByNaziv(LekarDTO.getTipPregleda().getNaziv()); 
		Lekar.setTipPregleda(tp);
		Lekar.setAutoriteti(autoritetService.findByName("ROLE_LEKAR"));
		Lekar.setPromenioLozinku(false);
		Lekar.setIzmenaRezervisanja(1);
		AdminKlinike ak = adminKlinikeService.findOne(IdAdmina);
		Lekar.setKlinika(ak.getKlinika());
		
		try {
			Lekar = LekarService.save(Lekar);
		} catch (Exception e) {
			return new ResponseEntity<>(new LekarDTO(Lekar), HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(new LekarDTO(Lekar), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/search/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> getSearchLekars(@RequestBody SearchLekar sl, @PathVariable Integer idAdmina) {
		System.out.println(sl.getIme()+sl.getPrezime());
		AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		List<Lekar> Lekars = LekarService.findByImeAndPrezime(sl.getIme().toUpperCase(), sl.getPrezime().toUpperCase());

		// convert Lekars to DTOs
		List<LekarDTO> LekarsDTO = new ArrayList<>();
		for (Lekar s : Lekars) {
			if(s.getKlinika().getId() == ak.getKlinika().getId())
				LekarsDTO.add(new LekarDTO(s));
		}

		return new ResponseEntity<>(LekarsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/lekariKlinike/{klinikaId}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<List<LekarDTO>> getLekariKlinike(@PathVariable Integer klinikaId) {

		List<Lekar> Lekars = LekarService.findAllByIdKlinike(klinikaId);

		// convert Lekars to DTOs
		List<LekarDTO> LekarsDTO = new ArrayList<>();
		for (Lekar s : Lekars) {
			LekarsDTO.add(new LekarDTO(s));
		}
		return new ResponseEntity<>(LekarsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/search/klinika/{idKlinike}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<List<LekarDTO>> getSearchLekariKlinike(@RequestBody SearchLekar sl, @PathVariable Integer idKlinike) {
		//System.out.println(sl.getIme()+sl.getPrezime());
		//AdminKlinike ak = adminKlinikeService.findOne(idAdmina);
		List<Lekar> Lekars = LekarService.findByImeAndPrezimeAndKlinika(sl.getIme().toUpperCase(), sl.getPrezime().toUpperCase(), idKlinike);

		// convert Lekars to DTOs
		List<LekarDTO> LekarsDTO = new ArrayList<>();
		for (Lekar s : Lekars) {
			
			LekarsDTO.add(new LekarDTO(s));
		}
		// selekcija za ocenu

		return new ResponseEntity<>(LekarsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value ="/slobodniLekari/search")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<List<PomocnaKlasa5>> pretragaSlobodnihLkeara(@RequestBody PomocnaKlasa6 data)
	{
		
		List<PomocnaKlasa5> retVal = new ArrayList<PomocnaKlasa5>();
		for(PomocnaKlasa5 lekarTermini  : data.lekariTermini)
		{
			if (!data.ime.equals(""))
			{
				if(lekarTermini.lekar.getIme().toUpperCase().contains(data.ime.toUpperCase()))
				{
					//poklapanje po imenu
					if (!data.prezime.equals(""))
					{
						if(lekarTermini.lekar.getPrezime().toUpperCase().contains(data.prezime.toUpperCase()))
						{
							// poklapanje i po prezimenu ako je uneto
							if(data.ocena != 0)
							{
								if(lekarTermini.lekar.getProsecnaOcena()>= data.ocena && lekarTermini.lekar.getProsecnaOcena()<=data.ocena+1)
								{
									// poklapanje po svim kriterijumima
									// uzmi ovog
									retVal.add(lekarTermini);
									continue;
								}
								else {
									// nema poklapanja po oceni
									// preskoci
									continue;
								}
							} else {
								// poklapanje po imenu i prezimenu
								// uzmi ovog nije uneta ocena
								retVal.add(lekarTermini);
								continue;

							}
						}else
						{
							// ima poklapanje po imenu, ali nema po porezimenu
							// preskocoi
							continue;
						}
					}
					else if(data.ocena != 0)
					{
						// uneto ime i ocena
						// nije uneto prezime
						if(lekarTermini.lekar.getProsecnaOcena()>= data.ocena && lekarTermini.lekar.getProsecnaOcena()<=data.ocena+1)
						{
							// uzmi oog lekara
							retVal.add(lekarTermini);
							continue;
						}
						else {
							// nema poklapanja sa ocenom
							// preskoci ga
							continue;
						}
					}
					// nije uneto ni prezime ni ocena samo ime
					// uzmi ovog lekara
					retVal.add(lekarTermini);
					continue;
				}
			}
			else if (!data.prezime.equals(""))
			{
				// ime = null
				// prezime = "Uneto"
				if(lekarTermini.lekar.getPrezime().toUpperCase().contains(data.prezime.toUpperCase()))
				{
					// poklapanje po prezimenu
					if(data.ocena != 0)
					{
						// ime = null
						// prezime = "Uneto"
						// ocena = broj
						if(lekarTermini.lekar.getProsecnaOcena()>= data.ocena && lekarTermini.lekar.getProsecnaOcena()<=data.ocena+1)
						{
							// poklapanje 
							// uzmi ovog
							retVal.add(lekarTermini);
							continue;
						}
						else {
							// nema poklapanja po oceni, ne uzimaj ga
							// preskoci
							continue;
						}
					}
					else
					{
						// uneta pretraga samo po prezimenu
						// uzmi ovog
						retVal.add(lekarTermini);
						continue;
					}
					
				}else
				{
					// nema poklapanja po porezimenu idi dalje
					// preskoci
					continue;
				}
				
			}
			else if(data.ocena != 0)
			{
				// unet samo kriterijum ocene
				if(lekarTermini.lekar.getProsecnaOcena()>= data.ocena && lekarTermini.lekar.getProsecnaOcena()<=data.ocena+1)
				{
					// uneta samo pretraga po oceni
					// uzmi ovog
					retVal.add(lekarTermini);
					continue;
				}
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@Transactional // obavezno ova anotacija, inace puca
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Void> deleteLekar(@PathVariable Integer id) {
		Lekar Lekar = LekarService.findOne(id);
		List<Pregled> preglediLekara = PregledService.findByLekarIdNotFinished(id);
		List<Integer> idOperacijeLekara = OperacijaService.findByLekarId(id);
		List<Operacija> operacijeLekara = new ArrayList<Operacija>();
		for (Integer i : idOperacijeLekara) {
			Operacija o = OperacijaService.findOneIfNotFinished(i);
			operacijeLekara.add(o);
		}
		
		try
		{		
			if (Lekar != null && preglediLekara.isEmpty() && operacijeLekara.isEmpty()) {			
			LekarService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	

	@PostMapping(value = "/dobaviSlobodneZaDatum/{id_operacije}")
	@PreAuthorize("hasRole( 'ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> getAllLekareZaOperaciju(@PathVariable Integer id_operacije,@RequestBody String datum) {
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		sdf = new SimpleDateFormat("yyyy-MM-dd'+'HH'%3A'mm'='");
		try {
			Date date =sdf.parse(datum);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		//dobavi mi operaciju 
		Operacija operacija = OperacijaService.findOne(id_operacije);
		
		//moram da id_operacije iz operacije_lekar pronadji lekara koji je zakazao op
		Integer idLekara = LekarService.findByIdOp(id_operacije);
		Lekar lekar = LekarService.findOne(idLekara);

		
		// dobavi mi sve lekare
		List<Lekar> lekari = LekarService.findAllByIdKlinike(lekar.getKlinika().getId());
		
		//izbaci onog koji je zakazao 
		int index = lekari.indexOf(lekar);
		lekari.remove(index);
		
		
        
		//Kroz preglede, ako je lekar u listi nadji njegov index i izbaci iz liste ako je zauzet 
		//treba iz id odgovarajuce klinike da se filtriraa
		List<Pregled> pregledi = PregledService.findAll(); //svi
		
		List<Lekar> ukloniZauzete = new ArrayList<>();
		
		for(Pregled p : pregledi) {
			Date pocetakPregleda = p.getDatumVreme();
		    Date krajPregleda = new Date(p.getDatumVreme().getTime() + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
		    Date pocetakOperacije = operacija.getDatumVreme();
		    Date krajOperacije = new Date(operacija.getDatumVreme().getTime() + (operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			
		    if(lekari.contains(p.getLekar()) 	&&
		    		(
		    		((pocetakOperacije.before(pocetakPregleda) || pocetakOperacije.equals(pocetakPregleda)) 
				    				&& pocetakPregleda.before(krajOperacije))
						    		||
						    		
		    		((pocetakOperacije.after(pocetakPregleda) || pocetakOperacije.equals(pocetakPregleda)) 
		    				&& pocetakOperacije.before(krajPregleda))
		    				||
		    				
		    		((krajOperacije.before(krajPregleda) || krajOperacije.equals(krajPregleda)) 
		    				&& krajOperacije.before(pocetakPregleda))
		    				||
		    		
		    		
		    		((pocetakOperacije.before(krajPregleda) || pocetakOperacije.equals(krajPregleda)) 
				    				&& krajPregleda.before(krajOperacije)))
		    		
					) {
					if(!ukloniZauzete.contains(p.getLekar())) {
						ukloniZauzete.add(p.getLekar());
					}
			}
			
		}
		
		//Kroz operacije, ako je lekar u listi nadji njegov index i izbaci iz liste ako je zauzet 
		//treba iz id odgovarajuce klinike da se filtriraa
		List<Operacija> operacije = OperacijaService.findAll(); //svi
		
		for(Operacija p : operacije) {
			Date pocetakPregleda = p.getDatumVreme();
		    Date krajPregleda = new Date(p.getDatumVreme().getTime() + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
		    Date pocetakOperacije = operacija.getDatumVreme();
		    Date krajOperacije = new Date(operacija.getDatumVreme().getTime() + (operacija.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			for(Lekar l: p.getLekar()) {
				
			}
		    if(((pocetakOperacije.before(pocetakPregleda) || pocetakOperacije.equals(pocetakPregleda)) 
				    				&& pocetakPregleda.before(krajOperacije))
						    		||
						    		
		    		((pocetakOperacije.after(pocetakPregleda) || pocetakOperacije.equals(pocetakPregleda)) 
		    				&& pocetakOperacije.before(krajPregleda))
		    				||
		    				
		    		((krajOperacije.before(krajPregleda) || krajOperacije.equals(krajPregleda)) 
		    				&& krajOperacije.before(pocetakPregleda))
		    				||
		    		
		    		
		    		((pocetakOperacije.before(krajPregleda) || pocetakOperacije.equals(krajPregleda)) 
				    				&& krajPregleda.before(krajOperacije))){
		    	for(Lekar l: p.getLekar()) {
					if(!ukloniZauzete.contains(l)) {
						ukloniZauzete.add(l);
					}
		    	}
			}
			
		}
		
        //ukloni sve 
		
		for(Lekar l: ukloniZauzete) {
		    if(lekari.contains(l)) {
		    	lekari.remove(l);
		    }
		}
	    
		List<LekarDTO> lekariDTO = new ArrayList<>();
		
		for (Lekar s : lekari) {
			lekariDTO.add(new LekarDTO(s));
		}

		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", value = "/{id}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<LekarDTO> updateLekar(@RequestBody LekarDTO lDTO, @PathVariable Integer id) {

		Lekar l = LekarService.findOne(id);

		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Pacijent pacijent = PacijentService.findByEmail(lDTO.getEmail());
		 if(pacijent != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		 }

		 AdminKC akc = AdminKCService.findByEmail(lDTO.getEmail());
		 if(akc != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		}
		 AdminKlinike ll = AdminKlinikeService.findByEmail(lDTO.getEmail());
		if(ll != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		}
		MedSestra ms = MedSestraService.findByEmail(lDTO.getEmail());
		if(ms != null) {
			return new ResponseEntity<>(new LekarDTO(),HttpStatus.BAD_REQUEST);
		}

		l.setIme(lDTO.getIme());
		l.setPrezime(lDTO.getPrezime());
		l.setEmail(lDTO.getEmail());
		l.setAdresa(lDTO.getAdresa());
		l.setGrad(lDTO.getGrad());
		l.setDrzava(lDTO.getDrzava());
		l.setKontakt(lDTO.getKontakt());
		try {
			l = LekarService.save(l);
			return new ResponseEntity<>(new LekarDTO(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping(value = "promenaLozinke/{id}/{novaLozinka}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<LekarDTO> updateLekarLozinka(@PathVariable Integer id, @PathVariable String novaLozinka) {

		Lekar l = LekarService.findOne(id);

		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		l.setLozinka(PacijentService.encodePassword(novaLozinka));
		l.setPromenioLozinku(true);
		try {
			l = LekarService.save(l);
			return new ResponseEntity<>(new LekarDTO(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
