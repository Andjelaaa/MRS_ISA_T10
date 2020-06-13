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
import main.mrs.model.TipPregleda;



public interface LekRepository  extends JpaRepository<Lek, Long>{
	Page<Lek> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Lek arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Lek> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Lek> findById(Long arg0);

	 
	  <S extends Lek> S save(S arg0);

	 
	  <S extends Lek> long count(Example<S> arg0);

	 
	  <S extends Lek> boolean exists(Example<S> arg0);

	 
	  <S extends Lek> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Lek> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Lek> arg0);

	 
	  List<Lek> findAll();

	 
	  <S extends Lek> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Lek> List<S> findAll(Example<S> arg0);

	 
	  List<Lek> findAll(Sort arg0);

	 
	  List<Lek> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Lek getOne(Long arg0);

	 
	  <S extends Lek> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Lek> S saveAndFlush(S arg0);
	  @Query(value = "SELECT * FROM LEK WHERE NAZIV = ?1", nativeQuery = true)
	  Lek findByNaziv(String naziv);
}
