package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Klinika;

public interface KlinikaRepository extends JpaRepository<Klinika, Long> {
	Page<Klinika> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Klinika arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Klinika> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Klinika> findById(Integer id);

	 
	  <S extends Klinika> S save(S arg0);

	 
	  <S extends Klinika> long count(Example<S> arg0);

	 
	  <S extends Klinika> boolean exists(Example<S> arg0);

	 
	  <S extends Klinika> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Klinika> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Klinika> arg0);

	 
	  List<Klinika> findAll();

	 
	  <S extends Klinika> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Klinika> List<S> findAll(Example<S> arg0);

	 
	  List<Klinika> findAll(Sort arg0);

	 
	  List<Klinika> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Klinika getOne(Long arg0);

	 
	  <S extends Klinika> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Klinika> S saveAndFlush(S arg0);

	  @Query(value = "SELECT * FROM KLINIKA WHERE  ID =?1", nativeQuery = true)
	  Klinika findOneById(int klinikaId);
}
