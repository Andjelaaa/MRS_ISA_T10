package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.OcenaKlinika;


public interface OcenaKlinikaRepository extends JpaRepository<OcenaKlinika, Long> {
	Page<OcenaKlinika> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(OcenaKlinika arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends OcenaKlinika> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<OcenaKlinika> findById(Long arg0);

	 
	  <S extends OcenaKlinika> S save(S arg0);

	 
	  <S extends OcenaKlinika> long count(Example<S> arg0);

	 
	  <S extends OcenaKlinika> boolean exists(Example<S> arg0);

	 
	  <S extends OcenaKlinika> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends OcenaKlinika> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<OcenaKlinika> arg0);

	 
	  List<OcenaKlinika> findAll();

	 
	  <S extends OcenaKlinika> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends OcenaKlinika> List<S> findAll(Example<S> arg0);

	 
	  List<OcenaKlinika> findAll(Sort arg0);

	 
	  List<OcenaKlinika> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  OcenaKlinika getOne(Long arg0);

	 
	  <S extends OcenaKlinika> List<S> saveAll(Iterable<S> arg0);

	@Query(value = "SELECT * FROM OCENA_KLINIKA WHERE PACIJENT_ID = ?2 AND KLINIKA_ID = ?1", nativeQuery = true)
	OcenaKlinika findOcenu(int klinikaId, int pacijentId);
}
