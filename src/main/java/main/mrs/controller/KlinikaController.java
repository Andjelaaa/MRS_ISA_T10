package main.mrs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import main.mrs.dto.KlinikaDTO;
import main.mrs.dto.LekarDTO;
import main.mrs.dto.SearchIzvestaj;
import main.mrs.model.Cenovnik;
import main.mrs.model.KlinickiCentar;
import main.mrs.model.Klinika;
import main.mrs.model.Lekar;
import main.mrs.model.Odsustvo;
import main.mrs.model.PomocnaKlasa5;
import main.mrs.model.Pregled;
import main.mrs.model.SlobodnoVreme;
import main.mrs.model.StavkaCenovnika;
import main.mrs.model.TipPregleda;
import main.mrs.service.KlinickiCentarService;
import main.mrs.service.KlinikaService;
import main.mrs.service.LekarService;
import main.mrs.service.OdsustvoService;
import main.mrs.service.PregledService;
import main.mrs.service.TipPregledaService;;

@RestController
@RequestMapping(value="api/klinika")
public class KlinikaController {

	@Autowired
	private KlinikaService KlinikaService;
	@Autowired
	private TipPregledaService TipPregledaService;
	@Autowired
	private LekarService LekarService;
	@Autowired
	private OdsustvoService OdsustvoService;
	
	@Autowired
	private KlinickiCentarService KlinickiCentarService;
	@Autowired
	private PregledService PregledService;
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'ROLE_PACIJENT')")
	public ResponseEntity<List<KlinikaDTO>> getAllKlinike() {

		List<Klinika> Klinike = KlinikaService.findAll();

		// convert Klinike to DTOs
		List<KlinikaDTO> KlinikeDTO = new ArrayList<>();
		for (Klinika s : Klinike) {
			KlinikeDTO.add(new KlinikaDTO(s));
		}

		return new ResponseEntity<>(KlinikeDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detalji/{klinikaId}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<KlinikaDTO> detaljiKlinike(@PathVariable int klinikaId) {

		Klinika Klinika = KlinikaService.findOneById(klinikaId);
		// convert Klinike to DTOs
		return new ResponseEntity<>(new KlinikaDTO(Klinika), HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/slobodnitermini/lekari/{datum}/{tipPregleda}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<List<PomocnaKlasa5>> LekariSlobodniTermini(@PathVariable String datum, @PathVariable String tipPregleda) {
		System.out.println("TRAZIM TERMINE SLOBODNE");
		TipPregleda tp = TipPregledaService.findByNaziv(tipPregleda);
		Date date1 = null;
		List<PomocnaKlasa5> retVal = new ArrayList<>();
		try {
			System.out.println(datum);
			date1=new SimpleDateFormat("yyyy-MM-dd").parse(datum);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		// za trazeni tip pregleda i datum moramo pronaci lekare koji u svom
		// radnom kalendaru imaju vremena taj dan da izvrse preglede
		// i prikazati kliniku u kojoj rade
		
		//izvuci lekare koji rade taj tip preglade
		List<Lekar> lekari = LekarService.findByTipPregledaId(tp.getId());
		
		// za svakog lekara proveriti da li odsustvuje taj dan
		ArrayList<Lekar> lekariKojiRade = new ArrayList<Lekar>();
		for (Lekar l: lekari)
		{
			Odsustvo odsustvuje = OdsustvoService.daLiOdsustvuje(l.getId(), date1);
			if(odsustvuje == null)
			{
				// ako je null onda taj lekar ne odsustvuje tj radi taj dan
				lekariKojiRade.add(l);
			}
		}
		
		/*
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date());
        */
		// za svakog lekara koji radi proveriti da li ima vremena da izvrsi pregled taj dan
		for (Lekar l: lekariKojiRade)
		{
			PomocnaKlasa5 lekarTermini = new PomocnaKlasa5();
			lekarTermini.lekar = new LekarDTO(l);
			
			boolean slobodan = false;
			Date rvPocetak = null;
			Date rvKraj = null;
			// vrati pregelede (vreme pregleda) koje izvrsava taj dan
			// iz baze vraca sortirano
			List<Pregled> pregledi = PregledService.nadjiPregledeLekaraZaDan(l.getId(), date1);
			
			// proveri da li ima slobodno u zavisnosti od njegovog radnog vremena
			SimpleDateFormat format = new SimpleDateFormat("HH:mm"); //if 24 hour format
			try {
				rvPocetak =(java.util.Date)format.parse(l.getRvPocetak());
				rvKraj =(java.util.Date)format.parse(l.getRvKraj());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(pregledi.isEmpty())
			{
				System.out.println("Slobodno vreme: od " + rvPocetak + " do " + rvKraj);
				SlobodnoVreme slobodanTermin = new SlobodnoVreme(rvPocetak, rvKraj);
				lekarTermini.slobodnoVreme.add(slobodanTermin);
			}
			
			for(Pregled p: pregledi)
			{
				// ispitati kad je slobodan
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				String time = localDateFormat.format(p.getDatumVreme());
				Date vremePregleda = null;
				try {
					vremePregleda = (Date)format.parse(time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(vremePregleda.after(rvPocetak))
				{
					slobodan = true;
					
					SlobodnoVreme slobodanTermin = new SlobodnoVreme(rvPocetak, vremePregleda);
					lekarTermini.slobodnoVreme.add(slobodanTermin);
					
					System.out.println("Slobodno vreme: od " + rvPocetak + " do " + vremePregleda);
					long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
					long vremeUMinutima = vremePregleda.getTime();
					
					// kad se zavrsi taj pregled on ima novi pocetak "radnog vremena"
					rvPocetak = new Date(vremeUMinutima + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
				}
				else
				{
					// pomeri se vreme pocetka za trajanje pregleda
					long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
					long vremeUMinutima = vremePregleda.getTime();
					rvPocetak = new Date(vremeUMinutima + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
				}
		      
			}
			// videti da li sad od rvPocetak do rvKraj ima dovoljno vremena za jos neki termin i to dodati
			long imaVremena = 0;
			if(lekarTermini.slobodnoVreme.size() > 1)
			{
				SlobodnoVreme slobodanTermin = new SlobodnoVreme(rvPocetak, rvKraj);
				lekarTermini.slobodnoVreme.add(slobodanTermin);
			}
			
			
			
			if(slobodan || pregledi.isEmpty())
			{
				System.out.println("SLOBODNA KLINIKA " + l.getKlinika().getNaziv());
				retVal.add(lekarTermini);
			}
		}
		return new ResponseEntity<>(retVal, HttpStatus.OK);
		//return new ResponseEntity<>(LekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/slobodnitermini/{datum}/{tipPregleda}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<List<KlinikaDTO>> klinikeSlobodniTermini(@PathVariable String datum, @PathVariable String tipPregleda) {
		System.out.println("TRAZIM TERMINE SLOBODNE");
		TipPregleda tp = TipPregledaService.findByNaziv(tipPregleda);
		Date date1 = null;
		List<KlinikaDTO> KlinikeDTO = new ArrayList<>();
		try {
			System.out.println(datum);
			date1=new SimpleDateFormat("yyyy-MM-dd").parse(datum);
			/*if(date1.before(new Date()))
			{
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}*/
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		// za trazeni tip pregleda i datum moramo pronaci lekare koji u svom
		// radnom kalendaru imaju vremena taj dan da izvrse preglede
		// i prikazati kliniku u kojoj rade
		
		//izvuci lekare koji rade taj tip preglade
		List<Lekar> lekari = LekarService.findByTipPregledaId(tp.getId());
		
		// za svakog lekara proveriti da li odsustvuje taj dan
		ArrayList<Lekar> lekariKojiRade = new ArrayList<Lekar>();
		for (Lekar l: lekari)
		{
			Odsustvo odsustvuje = OdsustvoService.daLiOdsustvuje(l.getId(), date1);
			if(odsustvuje == null)
			{
				// ako je null onda taj lekar ne odsustvuje tj radi taj dan
				lekariKojiRade.add(l);
			}
		}
		
		/*
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date());
        */
		// za svakog lekara koji radi proveriti da li ima vremena da izvrsi pregled taj dan
		for (Lekar l: lekariKojiRade)
		{
			boolean slobodan = false;
			Date rvPocetak = null;
			Date rvKraj = null;
			// vrati pregelede (vreme pregleda) koje izvrsava taj dan
			// iz baze vraca sortirano
			List<Pregled> pregledi = PregledService.nadjiPregledeLekaraZaDan(l.getId(), date1);
			
			// proveri da li ima slobodno u zavisnosti od njegovog radnog vremena
			SimpleDateFormat format = new SimpleDateFormat("HH:mm"); //if 24 hour format
			try {
				rvPocetak =(java.util.Date)format.parse(l.getRvPocetak());
				rvKraj =(java.util.Date)format.parse(l.getRvKraj());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(Pregled p: pregledi)
			{
				// ispitati kad je slobodan
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				String time = localDateFormat.format(p.getDatumVreme());
				Date vremePregleda = null;
				try {
					vremePregleda = (Date)format.parse(time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(vremePregleda.after(rvPocetak))
				{
					slobodan = true;
					System.out.println("Slobodno vreme: od " + rvPocetak + " do " + vremePregleda);
					long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
					long vremeUMinutima = vremePregleda.getTime();
					
					// kad se zavrsi taj pregled on ima novi pocetak "radnog vremena"
					rvPocetak = new Date(vremeUMinutima + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
				}
				else
				{
					// pomeri se vreme pocetka za trajanje pregleda
					long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
					long vremeUMinutima = vremePregleda.getTime();
					rvPocetak = new Date(vremeUMinutima + (p.getTrajanje() * ONE_MINUTE_IN_MILLIS));
				}
		      
			}
			
			System.out.println("Slobodno vreme: od " + rvPocetak + " do " + rvKraj);
			
			if(slobodan || pregledi.isEmpty())
			{
				System.out.println("SLOBODNA KLINIKA " + l.getKlinika().getNaziv());
				KlinikeDTO.add(new KlinikaDTO(l.getKlinika()));
			}
		}
		return new ResponseEntity<>(KlinikeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/proveriTermin/{vreme}")
	@PreAuthorize("hasRole('ROLE_PACIJENT')")
	public ResponseEntity<KlinikaDTO> proveriTermin(@PathVariable String vreme, @RequestBody PomocnaKlasa5 lekarTermini)
	{
		Date izabranoVremePocetak = null;
		Date izabranoVremeKraj = null;
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm"); //if 24 hour format
		try {
			izabranoVremePocetak =(java.util.Date)format.parse(vreme);
			
			long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
			long vremeUMinutima = izabranoVremePocetak.getTime();
			// za kraj dodaje se 30 min trajanja pregleda
			izabranoVremeKraj = new Date(vremeUMinutima + (30 * ONE_MINUTE_IN_MILLIS));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean uklapa = false;
		for(SlobodnoVreme s: lekarTermini.slobodnoVreme)
		{
			if(izabranoVremePocetak.equals(s.pocetak) || izabranoVremePocetak.after(s.pocetak))
			{
				if(izabranoVremeKraj.equals(s.kraj) || izabranoVremeKraj.before(s.kraj))
				{
					uklapa = true;
				}
			}
		}
		if(uklapa)
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<KlinikaDTO> saveKlinika(@RequestBody KlinikaDTO KlinikaDTO) {

		
		Klinika klinika = new Klinika();
		klinika.setNaziv(KlinikaDTO.getNaziv());
		klinika.setAdresa(KlinikaDTO.getAdresa());
		klinika.setOpis(KlinikaDTO.getOpis());
		klinika.setEmailKlinike(KlinikaDTO.getEmailKlinike());
		klinika.setKontaktKlinike(KlinikaDTO.getKontaktKlinike());
		// dodacu za ceovnik samo id
		//Cenovnik cenovnik = new Cenovnik();
		//cenovnik.setId(KlinikaDTO.getCenovnik().getId());
		//klinika.setCenovnik(cenovnik);
		klinika.setProsecnaOcena(0.0);
		klinika.setBrojOcena(0);
		
		try {
			List<Klinika> klinike = KlinikaService.findAll();
			for(Klinika k :klinike) {
				if(k.getNaziv().equalsIgnoreCase(klinika.getNaziv())) {
					return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.BAD_REQUEST);
				}
			}
			
			
			KlinickiCentar klinickiCentar = KlinickiCentarService.findOne(1);
			klinickiCentar.addKlinika(klinika);
			klinika = KlinikaService.save(klinika);
			klinickiCentar = KlinickiCentarService.save(klinickiCentar);
		} catch (Exception e) {
			return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json", value = "/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<KlinikaDTO> updateKlinika(@RequestBody KlinikaDTO KlinikaDTO, @PathVariable Integer id) {

		// a Klinika must exist
		Klinika Klinika = KlinikaService.findOne(id);

		if (Klinika == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Klinika.setNaziv(KlinikaDTO.getNaziv());
		Klinika.setOpis(KlinikaDTO.getOpis());
		Klinika.setAdresa(KlinikaDTO.getAdresa());
		Klinika.setEmailKlinike(KlinikaDTO.getEmailKlinike());
		Klinika.setKontaktKlinike(KlinikaDTO.getKontaktKlinike());
		
		try {
			Klinika = KlinikaService.save(Klinika);
		}catch(Exception e) {
			return new ResponseEntity<>(new KlinikaDTO(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new KlinikaDTO(Klinika), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", value = "/prihodi/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Double> prihodiKlinike(@RequestBody SearchIzvestaj searchIzvestaj, @PathVariable Integer id) {

		// a Klinika must exist
		Klinika Klinika = KlinikaService.findOne(id);

		if (Klinika == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		double prihodi = 0.0;
		
		// naci sve preglede, njihove stavke, cenu
		List<Pregled> pregledi = PregledService.izvestaj(searchIzvestaj.getPocetak(), searchIzvestaj.getKraj(), id);
		for (Pregled p : pregledi) {
			if(p.getPopust() != 0)
				prihodi += p.getTipPregleda().getStavka().getCena() * (100 - p.getPopust())/100;
			else
				prihodi += p.getTipPregleda().getStavka().getCena();
		}
		
		return new ResponseEntity<>(prihodi, HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping(value = "/grafik/{nivo}/{idKlinike}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Map<String,Integer>> grafik(@PathVariable String nivo, @PathVariable Integer idKlinike) {

		Map<String, Integer> podaci = new HashMap<String, Integer>(); // kljuc je dan, vrednost je broj pregleda za taj dan
		Date danas = new Date();
		Date danas2 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		GregorianCalendar gc = new GregorianCalendar();	
		gc.setTime(danas);
		
		
		if(nivo.equals("dnevni")) {
			for(int i=0; i <=20; i+=4) {
				danas.setHours(i);
				danas.setMinutes(0);
				danas2.setHours(i+4);
				danas2.setMinutes(0);
				Integer broj = PregledService.getPreglediZaSate(danas, danas2, idKlinike);
				podaci.put(sdf2.format(danas), broj);
			}
			
			
		}else if(nivo.equals("nedeljni")) {
			for(int i = -7; i<0; i++) {
				gc.add(Calendar.DAY_OF_MONTH, i);
				Integer broj = PregledService.getPreglediZaDatum(gc.getTime(), idKlinike);
				podaci.put(sdf.format(gc.getTime()), broj);
				gc.setTime(danas);
			}			
		}else if(nivo.equals("mesecni")) {
			for(int i = -30; i<0; i++) {
				gc.add(Calendar.DATE, i);
				Integer broj = PregledService.getPreglediZaDatum(gc.getTime(), idKlinike);
				podaci.put(sdf.format(gc.getTime()), broj);
				gc.setTime(danas);
			}
		}
		// sortira po datumu
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(podaci);		
		return new ResponseEntity<>(treeMap, HttpStatus.OK);

	}

}
