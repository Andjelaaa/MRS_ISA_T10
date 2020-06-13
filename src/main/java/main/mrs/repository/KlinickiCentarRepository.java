package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.KlinickiCentar;

public interface KlinickiCentarRepository extends JpaRepository<KlinickiCentar, Long> {
	Page<KlinickiCentar> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(KlinickiCentar arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends KlinickiCentar> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<KlinickiCentar> findById(Integer id);

	 
	  <S extends KlinickiCentar> S save(S arg0);

	 
	  <S extends KlinickiCentar> long count(Example<S> arg0);

	 
	  <S extends KlinickiCentar> boolean exists(Example<S> arg0);

	 
	  <S extends KlinickiCentar> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends KlinickiCentar> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<KlinickiCentar> arg0);

	 
	  List<KlinickiCentar> findAll();

	 
	  <S extends KlinickiCentar> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends KlinickiCentar> List<S> findAll(Example<S> arg0);

	 
	  List<KlinickiCentar> findAll(Sort arg0);

	 
	  List<KlinickiCentar> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  KlinickiCentar getOne(Long arg0);

	 
	  <S extends KlinickiCentar> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends KlinickiCentar> S saveAndFlush(S arg0);

}
