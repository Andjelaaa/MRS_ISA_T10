package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Lek;
import main.mrs.model.OcenaLekar;



public interface OcenaLekarRepository  extends JpaRepository<OcenaLekar, Long>{
	Page<OcenaLekar> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(OcenaLekar arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends OcenaLekar> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<OcenaLekar> findById(Long arg0);

	 
	  <S extends OcenaLekar> S save(S arg0);

	 
	  <S extends OcenaLekar> long count(Example<S> arg0);

	 
	  <S extends OcenaLekar> boolean exists(Example<S> arg0);

	 
	  <S extends OcenaLekar> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends OcenaLekar> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<OcenaLekar> arg0);

	 
	  List<OcenaLekar> findAll();

	 
	  <S extends OcenaLekar> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends OcenaLekar> List<S> findAll(Example<S> arg0);

	 
	  List<OcenaLekar> findAll(Sort arg0);

	 
	  List<OcenaLekar> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  OcenaLekar getOne(Long arg0);

	 
	  <S extends OcenaLekar> List<S> saveAll(Iterable<S> arg0);

	@Query(value = "SELECT * FROM OCENA_LEKAR WHERE PACIJENT_ID = ?2 AND LEKAR_ID = ?1", nativeQuery = true)
	OcenaLekar findOcenu(int lekarId, int pacijentId);
}
