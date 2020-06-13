package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.mrs.model.Pacijent;
import main.mrs.model.Sala;
import main.mrs.model.VerificationToken;


public interface VerificationTokenRepository  extends JpaRepository<VerificationToken, Long>{
	Page<VerificationToken> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(VerificationToken arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends VerificationToken> arg0);

	 
	  void deleteById(Long arg0);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<VerificationToken> findById(Long arg0);

	 
	  <S extends VerificationToken> S save(S arg0);

	 
	  <S extends VerificationToken> long count(Example<S> arg0);

	 
	  <S extends VerificationToken> boolean exists(Example<S> arg0);

	 
	  <S extends VerificationToken> Page<S> findAll(Example<S> arg0, Pageable arg1);

	 
	  <S extends VerificationToken> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<VerificationToken> arg0);

	 
	  List<VerificationToken> findAll();

	 
	  <S extends VerificationToken> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends VerificationToken> List<S> findAll(Example<S> arg0);

	 
	  List<VerificationToken> findAll(Sort arg0);

	 
	  List<VerificationToken> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  VerificationToken getOne(Long arg0);

	 
	  <S extends VerificationToken> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends VerificationToken> S saveAndFlush(S arg0);
	  
	  @Query(value = "SELECT * FROM verification_token WHERE token = ?1", nativeQuery = true)
	  VerificationToken findByToken(String token);
	  
	  @Query(value = "SELECT * FROM verification_token WHERE lbo = ?1", nativeQuery = true)
	  VerificationToken findByPacijent(Pacijent pacijent);


}
