package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.Cenovnik;

public interface CenovnikRepository extends JpaRepository<Cenovnik, Long>{
	Page<Cenovnik> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Cenovnik arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Cenovnik> arg0);

	 
	  void deleteById(Integer id);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Cenovnik> findById(Integer id);

	 
	  <S extends Cenovnik> S save(S arg0);

	 
	  <S extends Cenovnik> long count(Example<S> arg0);

	 
	  <S extends Cenovnik> boolean exists(Example<S> arg0);

	 
	  <S extends Cenovnik> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Cenovnik> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Cenovnik> arg0);

	 
	  List<Cenovnik> findAll();

	 
	  <S extends Cenovnik> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Cenovnik> List<S> findAll(Example<S> arg0);

	 
	  List<Cenovnik> findAll(Sort arg0);

	 
	  List<Cenovnik> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Cenovnik getOne(Long arg0);

	 
	  <S extends Cenovnik> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Cenovnik> S saveAndFlush(S arg0);

}

