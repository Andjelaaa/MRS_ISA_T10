package main.mrs.isa.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Pregled;
import main.mrs.model.TipPregleda;

public interface PregledRepository extends JpaRepository<Pregled, Long>{
	Page<Pregled> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Pregled arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Pregled> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  //Optional<Pregled> findById(Long arg0);

	 
	  <S extends Pregled> S save(S arg0);

	 
	  <S extends Pregled> long count(Example<S> arg0);

	 
	  <S extends Pregled> boolean exists(Example<S> arg0);

	 
	  <S extends Pregled> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Pregled> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Pregled> arg0);

	 
	  List<Pregled> findAll();

	 
	  <S extends Pregled> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Pregled> List<S> findAll(Example<S> arg0);

	 
	  List<Pregled> findAll(Sort arg0);

	 
	  List<Pregled> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Pregled getOne(Long arg0);

	 
	  <S extends Pregled> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Pregled> S saveAndFlush(S arg0);
	  
	  @Query(value = "SELECT * FROM PREGLED WHERE ID = ?1", nativeQuery = true)
	  Pregled findById(long id);

	  @Query(value = "SELECT * FROM PREGLED WHERE  PACIJENT_ID IS null AND PREGLED_ID = ?1", nativeQuery = true)
	  List<Pregled> findByTipPregleda(int tipPregledaId);

	  @Query(value = "SELECT * FROM PREGLED WHERE  PACIJENT_ID IS null AND DATUM_VREME >= ?1 ORDER BY DATUM_VREME ASC", nativeQuery = true)
	  List<Pregled> findAfterDate(Date datum);

	  @Query(value = "SELECT * FROM PREGLED WHERE PACIJENT_ID IS null AND KLINIKA_ID =?1", nativeQuery = true)
	  List<Pregled> getUnscheduled(int klinikaId);

	  @Query(value = "SELECT * FROM PREGLED WHERE PACIJENT_ID = ?1 AND IZVESTAJ_ID is null" , nativeQuery = true)
	  List<Pregled> getScheduled(int pacijentId);

	  @Query(value = "SELECT * FROM PREGLED WHERE LEKAR_ID = ?1 AND CAST(DATUM_VREME AS varchar(10)) = CAST(?2 AS varchar(10)) ORDER BY DATUM_VREME ASC", nativeQuery = true)
	  List<Pregled> nadjiPregledeLekaraZaDan(Integer id, Date date1);

	  @Query(value = "SELECT * FROM PREGLED WHERE PACIJENT_ID = ?1 AND IZVESTAJ_ID IS NOT null" , nativeQuery = true)
	  List<Pregled> dobaviIstoriju(int pacijentId);

	  // zahtevi pacijenta ili lekara
	  @Query(value = "SELECT * FROM PREGLED WHERE (STATUS = 0 OR STATUS = 3) AND KLINIKA_ID=?1" , nativeQuery = true)
	  List<Pregled> findAllZahteviKlinike(Integer integer);
	  
	  @Query(value = "SELECT * FROM PREGLED WHERE (STATUS = 0 OR STATUS = 3)" , nativeQuery = true)
	  List<Pregled> findAllZahtevi();
	  
	  @Query(value = "SELECT * FROM PREGLED WHERE LEKAR_ID = ?1", nativeQuery = true)
	  List<Pregled> findAllByLekarId(Integer id);

	  @Query(value = "SELECT * FROM PREGLED WHERE PACIJENT_ID=?1 AND LEKAR_ID = ?2 AND STATUS = 1 ", nativeQuery = true)
	  List<Pregled> getPreglediByPL(Integer pacijent_id, Integer lekar_id);

	  // dobavi sve zavrsene preglede odredjene klinike u odredjenom periodu
	  @Query(value = "SELECT * FROM PREGLED WHERE STATUS=4 AND DATUM_VREME >= ?1 AND DATUM_VREME <= ?2 AND KLINIKA_ID = ?3", nativeQuery = true)
	  List<Pregled> izvestaj(Date pocetak, Date kraj, Integer id);

	  @Query(value = "SELECT COUNT(*) FROM PREGLED WHERE CAST(DATUM_VREME AS varchar(10)) = CAST(?1 AS varchar(10)) AND KLINIKA_ID = ?2", nativeQuery = true)
	  Integer getPreglediZaDatum(Date danas, Integer idKlinike);

	  @Query(value = "SELECT COUNT(*) FROM PREGLED WHERE DATUM_VREME >= ?1 AND DATUM_VREME <= ?2 AND KLINIKA_ID = ?3", nativeQuery = true)
	  Integer getPreglediZaSate(Date danas, Date danas2, Integer idKlinike);

	  @Query(value = "SELECT * FROM PREGLED WHERE LEKAR_ID = ?1 AND PACIJENT_ID is not null AND IZVESTAJ_ID is null" , nativeQuery = true)
	  List<Pregled> getScheduledForDr(int lekarId);

	  @Query(value = "SELECT * FROM PREGLED WHERE STATUS = 4 AND KLINIKA_ID = ?1" , nativeQuery = true)
	  List<Pregled> getZavrsenePregledeZaRecept(Integer id);

	  @Query(value = "SELECT * FROM PREGLED WHERE LEKAR_ID = ?1 AND (STATUS != 4 AND STATUS != 2)", nativeQuery = true)
	  List<Pregled> findAllByLekarIdNotFinished(Integer id);

	  @Query(value = "SELECT * FROM PREGLED WHERE TIP_PREGLEDA_ID = ?1", nativeQuery = true)
	  List<Pregled> findAllByType(Integer id);
}














