package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.AdminKC;

public interface AdminKCRepository extends JpaRepository<AdminKC, Long>{

	Page<AdminKC> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(AdminKC arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends AdminKC> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<AdminKC> findById(Integer id);

	 
	  <S extends AdminKC> S save(S arg0);

	 
	  <S extends AdminKC> long count(Example<S> arg0);

	 
	  <S extends AdminKC> boolean exists(Example<S> arg0);

	 
	  <S extends AdminKC> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends AdminKC> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<AdminKC> arg0);

	 
	  List<AdminKC> findAll();

	 
	  <S extends AdminKC> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends AdminKC> List<S> findAll(Example<S> arg0);

	 
	  List<AdminKC> findAll(Sort arg0);

	 
	  List<AdminKC> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  AdminKC getOne(Long arg0);

	 
	  <S extends AdminKC> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends AdminKC> S saveAndFlush(S arg0);


	AdminKC findByEmail(String name);

}
