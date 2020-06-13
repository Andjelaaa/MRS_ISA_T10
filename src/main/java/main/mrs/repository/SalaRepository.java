package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long>{
	Page<Sala> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Sala arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Sala> arg0);

	 
	  void deleteById(Integer id);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Sala> findById(Integer id);

	 
	  <S extends Sala> S save(S arg0);

	 
	  <S extends Sala> long count(Example<S> arg0);

	 
	  <S extends Sala> boolean exists(Example<S> arg0);

	 
	  <S extends Sala> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Sala> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Sala> arg0);

	 
	  List<Sala> findAll();

	 
	  <S extends Sala> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Sala> List<S> findAll(Example<S> arg0);

	 
	  List<Sala> findAll(Sort arg0);

	 
	  List<Sala> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Sala getOne(Long arg0);

	 
	  <S extends Sala> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Sala> S saveAndFlush(S arg0);

	@Query(value = "SELECT * FROM SALA WHERE BROJ = ?1", nativeQuery = true)
	Sala findByBroj(int broj);

	@Query(value = "SELECT * FROM SALA WHERE NAZIV = ?1", nativeQuery = true)
	Sala findByNaziv(String naziv);

	 @Query(value = "SELECT * FROM SALA WHERE upper(NAZIV) like %?%1", nativeQuery = true)
	List<Sala> findSearchNaziv(String searchParam);

	 @Query(value = "SELECT * FROM SALA WHERE KLINIKA_ID = ?1", nativeQuery = true)
	List<Sala> findAllByIdKlinike(Integer id);
}
