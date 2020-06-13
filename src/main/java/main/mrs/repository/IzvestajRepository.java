package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.Izvestaj;

public interface IzvestajRepository extends JpaRepository<Izvestaj, Long>{
	Page<Izvestaj> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Izvestaj arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Izvestaj> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Izvestaj> findById(Integer pregled_id);

	 
	  <S extends Izvestaj> S save(S arg0);

	 
	  <S extends Izvestaj> long count(Example<S> arg0);

	 
	  <S extends Izvestaj> boolean exists(Example<S> arg0);

	 
	  <S extends Izvestaj> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends Izvestaj> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Izvestaj> arg0);

	 
	  List<Izvestaj> findAll();

	 
	  <S extends Izvestaj> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Izvestaj> List<S> findAll(Example<S> arg0);

	 
	  List<Izvestaj> findAll(Sort arg0);

	 
	  List<Izvestaj> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Izvestaj getOne(Long arg0);

	 
	  <S extends Izvestaj> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Izvestaj> S saveAndFlush(S arg0);


}
