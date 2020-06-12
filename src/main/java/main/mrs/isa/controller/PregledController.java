package main.mrs.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import main.mrs.dto.PregledDTO;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Dijagnoza;
import main.mrs.model.Izvestaj;
import main.mrs.model.Lekar;
import main.mrs.model.OcenaKlinika;
import main.mrs.model.OcenaLekar;
import main.mrs.model.Ocene;
import main.mrs.model.Operacija;
import main.mrs.model.Pacijent;
import main.mrs.model.PomocnaKlasa4;
import main.mrs.model.Pregled;
import main.mrs.model.Recept;
import main.mrs.model.Sala;
import main.mrs.model.Status;
import main.mrs.model.TipPregleda;
import main.mrs.model.ZKarton;
import main.mrs.model.ZahtevPregled;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.DijagnozaService;
import main.mrs.service.EmailService;
import main.mrs.service.IzvestajService;
import main.mrs.service.KlinikaService;
import main.mrs.service.LekarService;
import main.mrs.service.OcenaKlinikaService;
import main.mrs.service.OcenaLekarService;
import main.mrs.service.OperacijaService;
import main.mrs.service.PacijentService;
import main.mrs.service.PregledService;
import main.mrs.service.ReceptService;
import main.mrs.service.SalaService;
import main.mrs.service.TipPregledaService;

@RestController
@RequestMapping(value = "api/pregled")
public class PregledController {
	private SimpleDateFormat sdf;
	@Autowired
	private PregledService PregledService;
	@Autowired
	private DijagnozaService DijagnozaService;
	@Autowired
	private ReceptService ReceptService;
	@Autowired
	private LekarService LekarService;
	@Autowired
	private SalaService SalaService;
	@Autowired
	private IzvestajService IzvestajService;
	@Autowired
	private TipPregledaService TipPregledaService;
	@Autowired
	private EmailService EmailService;
	@Autowired
	private PacijentService PacijentService;
	@Autowired
	private KlinikaService KlinikaService;
	@Autowired
	private OcenaLekarService OcenaLekarService;
	@Autowired
	private OcenaKlinikaService OcenaKlinikaService;
	@Autowired
	private OperacijaService OperacijaService;
	@Autowired
	private AdminKlinikeService AdminKlinikeService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<PregledDTO>> getAllPregleds() {

		List<Pregled> Pregleds = PregledService.findAll();

		// convert Pregleds to DTOs
		List<PregledDTO> PregledsDTO = new ArrayList<>();
		for (Pregled s : Pregleds) {
			PregledDTO pregled = new PregledDTO(s);
			pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			pregled.setPopust(s.getPopust());
			PregledsDTO.add(pregled);

		}

		return new ResponseEntity<>(PregledsDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/ocene")
	public ResponseEntity<Ocene> dobaviOcene(@RequestBody PregledDTO data) {
		System.out.println("Dosao sam ovde da dobavim ocene");
		Ocene ocene = new Ocene(); // podesi ocene na 0, ako je 0 onda nije ni ocenjeno

		// dobavimo stare ocene ako postoje
		// kad namestimmo da se setuje i klinika za lekara odkomentarisatiii
		// int klinikaId = data.getLekar().getKlinika().getId();
		int klinikaId = 1;
		int pacijentId = data.getPacijent().getId();
		int lekarId = data.getLekar().getId();
		OcenaLekar ocenaLekara = OcenaLekarService.findOcenu(lekarId, pacijentId);
		OcenaKlinika ocenaKlinike = OcenaKlinikaService.findOcenu(klinikaId, pacijentId);

		if (ocenaLekara != null) {
			ocene.ocenaLekar = ocenaLekara.getOcena();
		}
		if (ocenaKlinike != null) {
			ocene.ocenaKlinika = ocenaKlinike.getOcena();
		}

		return new ResponseEntity<>(ocene, HttpStatus.OK);
	}

	@GetMapping(value = "/slobodniPregledi/{klinikaId}")
	public ResponseEntity<List<PregledDTO>> dobaviSlobodnePreglede(@PathVariable int klinikaId) {

		List<Pregled> Pregleds = PregledService.getUnscheduled(klinikaId);

		// convert Pregleds to DTOs
		List<PregledDTO> PregledsDTO = new ArrayList<>();
		for (Pregled s : Pregleds) {
			PregledDTO pregled = new PregledDTO(s);
			pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			pregled.setPopust(s.getPopust());
			PregledsDTO.add(pregled);
		}

		return new ResponseEntity<>(PregledsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/zakazaniPregledi/{pacijentId}")
	public ResponseEntity<List<PregledDTO>> dobaviZakazanePreglede(@PathVariable int pacijentId) {

		List<Pregled> Pregleds = PregledService.getScheduled(pacijentId);

		// convert Pregleds to DTOs
		List<PregledDTO> PregledsDTO = new ArrayList<>();
		for (Pregled s : Pregleds) {
			PregledDTO pregled = new PregledDTO(s);
			pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			pregled.setPopust(s.getPopust());
			PregledsDTO.add(pregled);
		}

		return new ResponseEntity<>(PregledsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{pacijent_id}/{lekar_id}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<PregledDTO> dobaviPregledeZaDan(@PathVariable Integer pacijent_id,
			@PathVariable Integer lekar_id) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<Pregled> pregledi = PregledService.getPreglediByPL(pacijent_id, lekar_id);
		System.out.println(pregledi.size()+"SIZEEEEEEEEEEEE");
		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled s : pregledi) {

			if (sdf.format(s.getDatumVreme()).equals(sdf.format(new Date()))) {
				PregledDTO pregled = new PregledDTO(s);
				preglediDTO.add(pregled);
			}

		}
		if (preglediDTO.isEmpty())
			return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(preglediDTO.get(0), HttpStatus.OK);
	}

	@GetMapping(value = "/istorijaPregleda/{pacijentId}")
	@PreAuthorize("hasAnyRole('LEKAR','MED_SESTRA' )")
	public ResponseEntity<List<PregledDTO>> dobaviIstorijuPregleda(@PathVariable int pacijentId) {

		List<Pregled> Pregleds = PregledService.dobaviIstoriju(pacijentId);
		
		
        List<PregledDTO> PregledsDTO = new ArrayList<>();
		try {
		       
		        for (Pregled s : Pregleds) {
		        	Dijagnoza dijagnoza = null;
		    		Recept recept = null;
		    		Izvestaj izvestaj = null;
			        PregledDTO pregled = new PregledDTO(s);
			        
			        pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			        pregled.setPopust(s.getPopust());
			        izvestaj = IzvestajService.findOne(s.getIzvestaj().getId());
			        
			        if(s.getIzvestaj().getDijagnoza() != null) {
				        dijagnoza = DijagnozaService.findOne(s.getIzvestaj().getDijagnoza().getId());
				        izvestaj.setDijagnoza(dijagnoza);
			        }

			        if(s.getIzvestaj().getRecept() != null) {
			        	recept = ReceptService.findOne(s.getIzvestaj().getRecept().getId());
			        	izvestaj.setRecept(recept);
			        }

			        pregled.setIzvestaj(izvestaj);
			        PregledsDTO.add(pregled);
			        
		        }
		}catch(Exception e) {
		
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(PregledsDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/otkazi/{pregledId}/{pacijentId}")
	@PreAuthorize("hasAnyRole('LEKAR', 'PACIJENT')")
	public ResponseEntity<PregledDTO> otkaziPregled(@PathVariable long pregledId, @PathVariable int pacijentId) {
		Pregled p = PregledService.findById(pregledId);
		try {
			long minutes1 = p.getDatumVreme().getTime() / 60000 - 120; // omasi za 2 sata
			long minutes2 = new Date().getTime() / 60000;
			if (minutes1 - minutes2 > 24 * 60) {
				Pacijent pacijent = PacijentService.findById(pacijentId);
				p.setPacijent(null);
				// pacijent.addPregled(p);
				p = PregledService.save(p);
				EmailService.posaljiObavestenjeOtkazanPregled(pacijent, p);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new PregledDTO(p), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new PregledDTO(p), HttpStatus.OK);
	}

	@GetMapping(value = "/lekarpre/{id}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getAllPregledeLekara(@PathVariable Integer id) {
		List<Pregled> pregledi = PregledService.findByLekarId(id);

		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled s : pregledi) {
			if (s.getStatus() == Status.odobreno)
				preglediDTO.add(new PregledDTO(s));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/{pregledId}/{pacijentId}")
	public ResponseEntity<PregledDTO> zakaziPregled(@PathVariable long pregledId, @PathVariable int pacijentId) {
		Pregled p = PregledService.findById(pregledId);
		Pacijent pacijent = PacijentService.findById(pacijentId);
		p.setPacijent(pacijent);
		// pacijent.addPregled(p);

		try {
			p = PregledService.save(p);
			EmailService.posaljiObavestenjeZakazanPregled(pacijent, p);

		} catch (Exception e) {
			return new ResponseEntity<>(new PregledDTO(p), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new PregledDTO(p), HttpStatus.OK);
	}

	@PostMapping(value = "izmenikarton/{pacijentId}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<String> izmeniKarton(@RequestBody PomocnaKlasa4 klasa, @PathVariable Integer pacijentId) {
		Pacijent pacijent = PacijentService.findById(pacijentId);
		ZKarton karton = pacijent.getzKarton();

		karton.setDioptrija(klasa.dioptrija);
		karton.setTezina(klasa.tezina);
		karton.setVisina(klasa.visina);
		karton.setKrvnaGrupa(klasa.krvnaGrupa);

		try {
			pacijent = PacijentService.save(pacijent);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/tip/{tipPregleda}")
	public ResponseEntity<List<PregledDTO>> zaTipPregleda(@PathVariable String tipPregleda) {
		TipPregleda tp = TipPregledaService.findByNaziv(tipPregleda);
		List<Pregled> result = PregledService.findByTipPregleda(tp.getId());
		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled s : result) {
			PregledDTO pregled = new PregledDTO(s);
			pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			pregled.setPopust(s.getPopust());
			preglediDTO.add(pregled);
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/datum/{datum}")
	public ResponseEntity<List<PregledDTO>> posleDatuma(@PathVariable String datum) {
		Date date1 = null;
		try {
			System.out.println(datum);
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		System.out.println(date1);
		List<Pregled> result = PregledService.findAfterDate(date1);
		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled s : result) {
			PregledDTO pregled = new PregledDTO(s);
			pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			pregled.setPopust(s.getPopust());
			preglediDTO.add(pregled);
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json;charset=UTF-8")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<PregledDTO> savePregled(@RequestBody PregledDTO PregledDTO) {
		Pregled PregledNovi = new Pregled();
		Date datum = PregledDTO.getDatumVreme();
		datum.setHours(datum.getHours() - 2);
		
		// provera da li je proslost
		if(datum.before(new Date()))
			return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);

		final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
		long curTimeInMs = datum.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (PregledDTO.getTrajanje() * ONE_MINUTE_IN_MILLIS));

		List<Pregled> SviPregledi = PregledService.findAll();
		List<Sala> ZauzeteSale = new ArrayList<Sala>();

		List<Operacija> SveOperacije = OperacijaService.findAll();
		for (Pregled p : SviPregledi) {
			long l = p.getDatumVreme().getTime();
			Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if ((p.getDatumVreme().equals(datum))
					|| (p.getDatumVreme().after(datum) && p.getDatumVreme().before(afterAddingMins))
					|| (krajPregleda.after(datum) && p.getDatumVreme().before(datum))
					|| (p.getDatumVreme().after(datum) && krajPregleda.before(afterAddingMins))) {
				ZauzeteSale.add(p.getSala());
			}
		}
		for (Operacija p : SveOperacije) {
			long l = p.getDatumVreme().getTime();
			Date krajPregleda = new Date(l + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
			if ((p.getDatumVreme().equals(datum))
					|| (p.getDatumVreme().after(datum) && p.getDatumVreme().before(afterAddingMins))
					|| (krajPregleda.after(datum) && p.getDatumVreme().before(datum))
					|| (p.getDatumVreme().after(datum) && krajPregleda.before(afterAddingMins))) {
				ZauzeteSale.add(p.getSala());
			}
		}

		PregledNovi.setDatumVreme(datum);
		PregledNovi.setTrajanje(PregledDTO.getTrajanje());
		PregledNovi.setStatus(Status.odobreno);
		PregledNovi.setPopust(PregledDTO.getPopust());
	
		TipPregleda tp = TipPregledaService.findByNaziv(PregledDTO.getTipPregleda().getNaziv());
		PregledNovi.setTipPregleda(tp);
		Sala s = SalaService.findByNaziv(PregledDTO.getSala().getNaziv());
		if (!ZauzeteSale.contains(s)) {
			PregledNovi.setSala(s);
		} else {
			return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);
		}
		Lekar l = LekarService.findByEmail(PregledDTO.getLekar().getEmail());		
		// provera da li je lekar validan
		SimpleDateFormat sdform = new SimpleDateFormat("HH:mm");
		String dd = sdform.format(datum);
		try {
			if(sdform.parse(dd).after(sdform.parse(l.getRvPocetak()))  && sdform.parse(dd).before(sdform.parse(l.getRvKraj())))
				System.out.println("Radno vreme!!!");
			else
				return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);		
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
				
		PregledNovi.setLekar(l);
		l.getKlinika().addpacijent(PregledNovi.getPacijent());
		try {
			PregledNovi = PregledService.save(PregledNovi);
		} catch (Exception e) {
			return new ResponseEntity<>(new PregledDTO(PregledNovi), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new PregledDTO(PregledNovi), HttpStatus.CREATED);
	}

	@GetMapping(value = "/zahtevi/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> getZahtevi(@PathVariable Integer idAdmina) {
		AdminKlinike ak = AdminKlinikeService.findOne(idAdmina);
		List<Pregled> Pregleds = PregledService.findAllZahteviKlinike(ak.getKlinika().getId());

		// convert Pregleds to DTOs
		List<PregledDTO> PregledsDTO = new ArrayList<>();
		for (Pregled s : Pregleds) {
				PregledDTO pregled = new PregledDTO(s);
				pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
				pregled.setPopust(s.getPopust());
				PregledsDTO.add(pregled);

		}

		return new ResponseEntity<>(PregledsDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json;charset=UTF-8", value = "/lekarzahtev")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<PregledDTO> saveZahtevLekar(@RequestBody PregledDTO PregledDTO) {
		Pregled Pregled = new Pregled();
		
		// ne sme zakazivati za proslost
		if(PregledDTO.getDatumVreme().before(new Date()))
			return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);

		Pregled.setDatumVreme(PregledDTO.getDatumVreme());
		Pregled.setTrajanje(PregledDTO.getTrajanje());
		Pregled.setStatus(Status.zahtev_lekar);
		Pregled.setPopust(0.0);
		TipPregleda tp = TipPregledaService.findByNaziv(PregledDTO.getLekar().getTipPregleda().getNaziv());
		Pregled.setTipPregleda(tp);
		Pregled.setSala(null);
		Lekar l = LekarService.findByEmail(PregledDTO.getLekar().getEmail());
		
		// provera da li je u radnom vremenu
		SimpleDateFormat sdform = new SimpleDateFormat("HH:mm");
		String dd = sdform.format(PregledDTO.getDatumVreme());
		try {
			if(sdform.parse(dd).after(sdform.parse(l.getRvPocetak()))  && sdform.parse(dd).before(sdform.parse(l.getRvKraj())))
				System.out.println("Radno vreme!!!");
			else {
				return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);	
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Pregled.setLekar(l);
		l.getKlinika().addPregled(Pregled);
		Pacijent p = PacijentService.findByEmail(PregledDTO.getPacijent().getEmail());
		Pregled.setPacijent(p);

		// OVO NE BRISATI
		System.out.println(l.getKlinika().getAdminKlinike().isEmpty());

		try {
			Pregled = PregledService.save(Pregled);
			EmailService.mailAdminuZakazanPregled(Pregled);
		} catch (Exception e) {
			return new ResponseEntity<>(new PregledDTO(Pregled), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new PregledDTO(Pregled), HttpStatus.CREATED);
	}

	@PostMapping(consumes = "application/json;charset=UTF-8", value = "/pacijentzahtev")
	public ResponseEntity<PregledDTO> saveZahtevPacijent(@RequestBody ZahtevPregled zahtev) {
		System.out.println("E cao saljem zahtev za dan " + zahtev.datum);
		Pregled Pregled = new Pregled();
		// Pregled.setDatumVreme(PregledDTO.getDatumVreme());
		String datumVreme = zahtev.datum + " " + zahtev.vreme;
		// 2020-05-21 hh:mm
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-mm HH:mm");

		try {
			System.out.println("Sad cu da parsiram datum");
			Pregled.setDatumVreme((Date) (sdf.parse(datumVreme)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			System.out.println("E nije uspelo parsiranje");
			e1.printStackTrace();
		}
		Pacijent p = PacijentService.findById(Integer.parseInt(zahtev.pacijentId));
		Pregled.setPacijent(p);
		// Klinika k = KlinikaService.findOne(Integer.parseInt(zahtev.klinikaId));
		Pregled.setTrajanje(30);
		Pregled.setStatus(Status.zahtev);
		Pregled.setPopust(0.0);
		TipPregleda tp = TipPregledaService.findByNaziv(zahtev.tipPregledaNaziv);
		Pregled.setTipPregleda(tp);
		Pregled.setSala(null);
		Lekar l = LekarService.findByEmail(zahtev.lekarEmail);
		Pregled.setLekar(l);

		try {
			System.out.println(" pokusacu da ga sacuvam");
			Pregled = PregledService.save(Pregled);
		} catch (Exception e) {
			System.out.println("E nije uspelo cuvanje");
			return new ResponseEntity<>(new PregledDTO(Pregled), HttpStatus.BAD_REQUEST);
		}
		System.out.println("E SVE JE OOKKKKKK");
		return new ResponseEntity<>(new PregledDTO(Pregled), HttpStatus.CREATED);
	}

	@GetMapping(value = "/rezervisi/{pregledId}/{salaId}/{prviSlobodan}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINIKE', 'LEKAR')")
	public ResponseEntity<PregledDTO> rezervisiSaluZaPregled(@PathVariable Integer pregledId,
			@PathVariable Integer salaId, @PathVariable String prviSlobodan) {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");

		Date datum = null;
		try {
			datum = sdf.parse(prviSlobodan);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Pregled p = PregledService.findById(pregledId);
		Sala s = SalaService.findOne(salaId);		
		s.setIzmena(s.getIzmena()+1);
		p.setSala(s);
		p.setStatus(Status.odobreno);
		p.getLekar().getKlinika().addpacijent(p.getPacijent());
		datum.setHours(datum.getHours() + 2);
		p.setDatumVreme(datum);
		try {
			SalaService.save(s);
			PregledService.save(p);	
			EmailService.posaljiPacijentuOdobrenPregled(p);
			EmailService.posaljiLekaruOdobrenPregled(p);
		} catch (Exception e) {
			return new ResponseEntity<>(new PregledDTO(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new PregledDTO(), HttpStatus.OK);
	}

	@GetMapping(value = "/zakazaniZaLekara/{lekarId}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<List<PregledDTO>> dobaviZakazane(@PathVariable int lekarId) {

		List<Pregled> Pregleds = PregledService.getScheduledForDr(lekarId);

		// convert Pregleds to DTOs
		List<PregledDTO> PregledsDTO = new ArrayList<>();
		for (Pregled s : Pregleds) {
			PregledDTO pregled = new PregledDTO(s);
			pregled.getTipPregleda().getStavka().setCena(s.getTipPregleda().getStavka().getCena());
			pregled.setPopust(s.getPopust());
			PregledsDTO.add(pregled);
		}

		return new ResponseEntity<>(PregledsDTO, HttpStatus.OK);
	}

}
