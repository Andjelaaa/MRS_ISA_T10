package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long>{
	Page<TipPregleda> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(TipPregleda arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends TipPregleda> arg0);

	 
	  void deleteById(Integer id);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<TipPregleda> findById(Integer id);

	 
	  <S extends TipPregleda> S save(S arg0);

	 
	  <S extends TipPregleda> long count(Example<S> arg0);

	 
	  <S extends TipPregleda> boolean exists(Example<S> arg0);

	 
	  <S extends TipPregleda> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends TipPregleda> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<TipPregleda> arg0);

	 
	  List<TipPregleda> findAll();

	 
	  <S extends TipPregleda> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends TipPregleda> List<S> findAll(Example<S> arg0);

	 
	  List<TipPregleda> findAll(Sort arg0);

	 
	  List<TipPregleda> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  TipPregleda getOne(Long arg0);

	 
	  <S extends TipPregleda> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends TipPregleda> S saveAndFlush(S arg0);
	  
	  
	  @Query(value = "SELECT * FROM TIP_PREGLEDA WHERE NAZIV = ?1", nativeQuery = true)
	  TipPregleda findByNaziv(String naziv);

	  @Query(value = "SELECT * FROM TIP_PREGLEDA WHERE upper(NAZIV) like %?%1", nativeQuery = true)
	  List<TipPregleda> findSearchNaziv(String searchParam);
}
