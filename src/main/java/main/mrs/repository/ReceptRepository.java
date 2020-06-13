package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.Recept;


public interface ReceptRepository  extends JpaRepository<Recept, Long>{
	Page<Recept> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Recept arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Recept> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Recept> findById(Integer integer);

	 
	  <S extends Recept> S save(S arg0);

	 
	  <S extends Recept> long count(Example<S> arg0);

	 
	  <S extends Recept> boolean exists(Example<S> arg0);

	 
	  <S extends Recept> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Recept> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Recept> arg0);

	 
	  List<Recept> findAll();

	 
	  <S extends Recept> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Recept> List<S> findAll(Example<S> arg0);

	 
	  List<Recept> findAll(Sort arg0);

	 
	  List<Recept> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Recept getOne(Long arg0);

	 
	  <S extends Recept> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Recept> S saveAndFlush(S arg0);

}
