package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.AdminKlinike;
import main.mrs.model.AdminKlinike;

public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Long>{
	Page<AdminKlinike> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(AdminKlinike arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends AdminKlinike> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<AdminKlinike> findById(Integer idAdmina);

	 
	  <S extends AdminKlinike> S save(S arg0);

	 
	  <S extends AdminKlinike> long count(Example<S> arg0);

	 
	  <S extends AdminKlinike> boolean exists(Example<S> arg0);

	 
	  <S extends AdminKlinike> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends AdminKlinike> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<AdminKlinike> arg0);

	 
	  List<AdminKlinike> findAll();

	 
	  <S extends AdminKlinike> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends AdminKlinike> List<S> findAll(Example<S> arg0);

	 
	  List<AdminKlinike> findAll(Sort arg0);

	 
	  List<AdminKlinike> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  AdminKlinike getOne(Long arg0);

	 
	  <S extends AdminKlinike> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends AdminKlinike> S saveAndFlush(S arg0);


	AdminKlinike findByEmail(String name);
}
