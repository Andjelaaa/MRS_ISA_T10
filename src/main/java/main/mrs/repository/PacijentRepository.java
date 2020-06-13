package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Pacijent;


public interface PacijentRepository extends JpaRepository<Pacijent, Long> {
	Page<Pacijent> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Pacijent arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Pacijent> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	 // Optional<Pacijent> findById(Long arg0);

	 
	  <S extends Pacijent> S save(S arg0);

	 
	  <S extends Pacijent> long count(Example<S> arg0);

	 
	  <S extends Pacijent> boolean exists(Example<S> arg0);

	 
	  <S extends Pacijent> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Pacijent> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Pacijent> arg0);

	 
	  List<Pacijent> findAll();

	 
	  <S extends Pacijent> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Pacijent> List<S> findAll(Example<S> arg0);

	 
	  List<Pacijent> findAll(Sort arg0);

	 
	  List<Pacijent> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Pacijent getOne(Long arg0);

	 
	  <S extends Pacijent> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Pacijent> S saveAndFlush(S arg0);
	  
	  @Query(value = "SELECT * FROM PACIJENT WHERE ID = ?1", nativeQuery = true)
	  Pacijent findById(int id);


	Pacijent findByEmail(String arg0);

	@Query(value = "SELECT * FROM PACIJENT WHERE upper(IME) like %?%1 and upper(prezime) like %?%2 and upper(lbo) like %?%3", nativeQuery = true)
	List<Pacijent> findByImeAndPrezimeAndLbo(String ime, String prezime, String lbo);


	Pacijent findByLbo(String lbo);
}
