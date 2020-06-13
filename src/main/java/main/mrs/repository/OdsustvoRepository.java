package main.mrs.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Lekar;
import main.mrs.model.Odsustvo;


public interface OdsustvoRepository  extends JpaRepository<Odsustvo, Long>{
	Page<Odsustvo> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Odsustvo arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Odsustvo> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Odsustvo findById(Integer id);

	 
	  <S extends Odsustvo> S save(S arg0);

	 
	  <S extends Odsustvo> long count(Example<S> arg0);

	 
	  <S extends Odsustvo> boolean exists(Example<S> arg0);

	 
	  <S extends Odsustvo> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Odsustvo> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Odsustvo> arg0);

	 
	  List<Odsustvo> findAll();

	 
	  <S extends Odsustvo> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Odsustvo> List<S> findAll(Example<S> arg0);

	 
	  List<Odsustvo> findAll(Sort arg0);

	 
	  List<Odsustvo> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Odsustvo getOne(Long arg0);

	 
	  <S extends Odsustvo> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Odsustvo> S saveAndFlush(S arg0);


	  // vraca id lekara koji odsustvuje
	@Query(value = "SELECT * FROM ODSUSTVO WHERE LEKAR_ID = ?1 AND ?2 >= POCETAK AND ?2 <= KRAJ", nativeQuery = true)
	Odsustvo daLiOdsustvuje(Integer id, Date date1);

	@Query(value = "SELECT * FROM ODSUSTVO WHERE STATUS=0", nativeQuery = true)
	List<Odsustvo> findAllZahtevi();



}
