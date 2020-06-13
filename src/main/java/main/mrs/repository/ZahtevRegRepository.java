package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import main.mrs.model.ZahtevReg;

public interface ZahtevRegRepository extends JpaRepository<ZahtevReg, Long>{
	Page<ZahtevReg> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(ZahtevReg arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends ZahtevReg> arg0);

	 
	  void deleteById(Integer id);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<ZahtevReg> findById(Long arg0);

	 
	  <S extends ZahtevReg> S save(S arg0);

	 
	  <S extends ZahtevReg> long count(Example<S> arg0);

	 
	  <S extends ZahtevReg> boolean exists(Example<S> arg0);

	 
	  <S extends ZahtevReg> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends ZahtevReg> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<ZahtevReg> arg0);

	 
	  List<ZahtevReg> findAll();

	 
	  <S extends ZahtevReg> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends ZahtevReg> List<S> findAll(Example<S> arg0);

	 
	  List<ZahtevReg> findAll(Sort arg0);

	 
	  List<ZahtevReg> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  ZahtevReg getOne(Long arg0);

	 
	  <S extends ZahtevReg> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends ZahtevReg> S saveAndFlush(S arg0);


	ZahtevReg findByEmail(String email);
}
