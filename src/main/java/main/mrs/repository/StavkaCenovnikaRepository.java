package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.StavkaCenovnika;

public interface StavkaCenovnikaRepository extends JpaRepository<StavkaCenovnika, Long>{
	Page<StavkaCenovnika> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(StavkaCenovnika arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends StavkaCenovnika> arg0);

	 
	  void deleteById(Integer id);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<StavkaCenovnika> findById(Integer id);

	 
	  <S extends StavkaCenovnika> S save(S arg0);

	 
	  <S extends StavkaCenovnika> long count(Example<S> arg0);

	 
	  <S extends StavkaCenovnika> boolean exists(Example<S> arg0);

	 
	  <S extends StavkaCenovnika> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends StavkaCenovnika> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<StavkaCenovnika> arg0);

	 
	  List<StavkaCenovnika> findAll();

	 
	  <S extends StavkaCenovnika> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends StavkaCenovnika> List<S> findAll(Example<S> arg0);

	 
	  List<StavkaCenovnika> findAll(Sort arg0);

	 
	  List<StavkaCenovnika> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  StavkaCenovnika getOne(Long arg0);

	 
	  <S extends StavkaCenovnika> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends StavkaCenovnika> S saveAndFlush(S arg0);

}

