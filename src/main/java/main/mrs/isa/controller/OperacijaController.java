package main.mrs.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.mrs.dto.LekarDTO;
import main.mrs.dto.OperacijaDTO;
import main.mrs.dto.PregledDTO;
import main.mrs.model.AdminKlinike;
import main.mrs.model.Lekar;
import main.mrs.model.Operacija;
import main.mrs.model.Pacijent;
import main.mrs.model.PomocnaKlasa7;
import main.mrs.model.Sala;
import main.mrs.model.Status;
import main.mrs.service.AdminKlinikeService;
import main.mrs.service.EmailService;
import main.mrs.service.LekarService;
import main.mrs.service.OperacijaService;
import main.mrs.service.PacijentService;
import main.mrs.service.SalaService;

@RestController
@RequestMapping(value="api/operacije")
public class OperacijaController {

	private SimpleDateFormat sdf;
	@Autowired
	private OperacijaService OperacijaService;
	@Autowired
	private LekarService LekarService;
	@Autowired
	private PacijentService PacijentService;
	
	@Autowired
	private EmailService EmailService;

	@Autowired 
	private SalaService SalaService;
	
	@Autowired 
	private AdminKlinikeService AdminKlinikeService;
	
	@Transactional // obavezno ova anotacija, inace puca
	@GetMapping(value="/lekarop/{id}")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<List<OperacijaDTO>> dobaviLekaroveOperacije(@PathVariable Integer id){
		//lekar_id
		
		List<Integer> idOperacija = OperacijaService.findByLekarId(id);

		List<Operacija> operacije = OperacijaService.findAllById(idOperacija);
		
		
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija s : operacije) {
			if(s.getStatus() == Status.odobreno)
				operacijeDTO.add(new OperacijaDTO(s));
		}
		
		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
		
	
	}
	
	@PostMapping(consumes = "application/json;charset=UTF-8", value="/lekarzahtev")
	@PreAuthorize("hasAnyRole('ADMIN_KLINIKE', 'LEKAR')")
	public ResponseEntity<OperacijaDTO> saveZahtevLekar(@RequestBody OperacijaDTO OperacijaDTO) {
		Operacija Operacija = new Operacija();
		// ne sme zakazivati za proslost
		if(OperacijaDTO.getDatumVreme().before(new Date()))
			return new ResponseEntity<>(new OperacijaDTO(), HttpStatus.BAD_REQUEST);
		
		Operacija.setDatumVreme(OperacijaDTO.getDatumVreme());
		Operacija.setTrajanje(OperacijaDTO.getTrajanje());
		Operacija.setStatus(Status.zahtev_lekar);	
		Operacija.setSala(null);
		Lekar l = LekarService.findByEmail((new ArrayList<LekarDTO>(OperacijaDTO.getLekar())).get(0).getEmail());
		// provera da li je u radnom vremenu
		SimpleDateFormat sdform = new SimpleDateFormat("HH:mm");
		String dd = sdform.format(OperacijaDTO.getDatumVreme());
		try {
			if(sdform.parse(dd).after(sdform.parse(l.getRvPocetak()))  && sdform.parse(dd).before(sdform.parse(l.getRvKraj())))
				System.out.println("Radno vreme!!!");
			else {
				return new ResponseEntity<>(new OperacijaDTO(), HttpStatus.BAD_REQUEST);	
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Set<Lekar> lekari = new HashSet<Lekar>();
		lekari.add(l);
		Operacija.setLekar(lekari);
		
		Pacijent p = PacijentService.findByEmail(OperacijaDTO.getPacijent().getEmail());
		Operacija.setPacijent(p);
		
		// OVO NE BRISATI
		System.out.println(l.getKlinika().getAdminKlinike().isEmpty());
		
		try {
			Operacija = OperacijaService.save(Operacija);
			EmailService.mailAdminuZakazanaOperacija(Operacija, l);
		} catch (Exception e) {
			return new ResponseEntity<>(new OperacijaDTO(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new OperacijaDTO(), HttpStatus.CREATED);
	}
	@GetMapping(value = "/zahtevi/{idAdmina}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<OperacijaDTO>> getZahtevi(@PathVariable Integer idAdmina) {
		AdminKlinike ak = AdminKlinikeService.findOne(idAdmina);
		List<Operacija> operacije = OperacijaService.findAllZahteviKlinike(ak.getKlinika().getId());

		List<OperacijaDTO> OperacijeDTO = new ArrayList<>();
		for (Operacija s : operacije) {
			OperacijaDTO operacija = new OperacijaDTO(s);
			OperacijeDTO.add(operacija);
			
		}
		return new ResponseEntity<>(OperacijeDTO, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/rezervisi/{operacijaId}/{salaId}/{prviSlobodan}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINIKE', 'LEKAR')")
	public ResponseEntity<OperacijaDTO> rezervisiSaluZaOperaciju(@PathVariable Integer operacijaId, @PathVariable Integer salaId, 
			@PathVariable String prviSlobodan, @RequestBody PomocnaKlasa7 pomkl7){
		
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
		Date datum = null;
		try {
			datum = sdf.parse(prviSlobodan);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Operacija operacija = OperacijaService.findOne(operacijaId);
		Sala s = SalaService.findOne(salaId);
			
		s.setIzmena(s.getIzmena()+1);
		
		operacija.setSala(s);
		operacija.setStatus(Status.odobreno);

		datum.setHours(datum.getHours()+2);
		operacija.setDatumVreme(datum);
		Set<Lekar> lekari = new HashSet<>();
		
		//Lekar glavni = (Lekar) operacija.getLekar();
		Iterator iter = operacija.getLekar().iterator();

		Lekar glavni = (Lekar) iter.next();

		lekari.add(glavni);
		
		for(LekarDTO ld : pomkl7.lekariDTO) {
			Lekar lekar = LekarService.findByEmail(ld.getEmail());
			lekar.setIzmenaRezervisanja(lekar.getIzmenaRezervisanja()+1);
			lekar = LekarService.save(lekar);
			lekari.add(lekar);
		}
		operacija.setLekar(lekari);
		
		try {
			s = SalaService.save(s);
			operacija = OperacijaService.save(operacija);
			EmailService.posaljiPacijentuOdobrenaOperacija(operacija);
			EmailService.posaljiLekaruOdobrenaOperacija(operacija);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(new OperacijaDTO(operacija), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(new OperacijaDTO(operacija), HttpStatus.OK);
	}

}
