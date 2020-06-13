package main.mrs.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import main.mrs.model.Lekar;

public interface LekarRepository extends JpaRepository<Lekar, Long>{
	Page<Lekar> findAll(Pageable arg0);

	 
	  long count();

	 
	  void delete(Lekar arg0);

	 
	  void deleteAll();

	 
	  void deleteAll(Iterable<? extends Lekar> arg0);

	 
	  void deleteById(Integer id);

	 
	  boolean existsById(Long arg0);

	 
	  Optional<Lekar> findById(Integer id);

	 
	  <S extends Lekar> S save(S arg0);

	 
	  <S extends Lekar> long count(Example<S> arg0);

	 
	  <S extends Lekar> boolean exists(Example<S> arg0);

	 
	  <S extends Lekar> Page<S> findAll(Example<S> arg0, Pageable arg1);

	  @Lock(LockModeType.PESSIMISTIC_WRITE)
	  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	  <S extends Lekar> Optional<S> findOne(Example<S> arg0);

	 
	  void deleteAllInBatch();

	 
	  void deleteInBatch(Iterable<Lekar> arg0);

	 
	  List<Lekar> findAll();

	 
	  <S extends Lekar> List<S> findAll(Example<S> arg0, Sort arg1);

	 
	  <S extends Lekar> List<S> findAll(Example<S> arg0);

	 
	  List<Lekar> findAll(Sort arg0);

	 
	  List<Lekar> findAllById(Iterable<Long> arg0);

	 
	  void flush();

	 
	  Lekar getOne(Long arg0);

	 
	  <S extends Lekar> List<S> saveAll(Iterable<S> arg0);

	 
	  <S extends Lekar> S saveAndFlush(S arg0);
	  
	
	  @Lock(LockModeType.PESSIMISTIC_WRITE)
	  @Query(value = "SELECT l FROM Lekar AS l WHERE l.email = ?1")
	Lekar findByEmail(String email);

	@Query(value = "SELECT * FROM LEKAR WHERE (upper(IME) like %?%1 and upper(prezime) like %?%2)", nativeQuery = true)
	List<Lekar> findByImeAndPrezime(String ime, String prezime);

	@Query(value = "SELECT * FROM LEKAR WHERE TIP_PREGLEDA_ID = ?1", nativeQuery = true)
	List<Lekar> findByTipPregledaId(Integer id);

	@Query(value = "SELECT LEKAR_ID FROM OPERACIJA_LEKAR WHERE OPERACIJA_ID = ?1", nativeQuery = true)
	Integer findByIdOp(Integer id_operacije);

	@Query(value = "SELECT * FROM LEKAR WHERE KLINIKA_ID = ?1", nativeQuery = true)
	List<Lekar> findAllByIdKlinike(Integer id);
	
	@Query(value = "SELECT * FROM LEKAR WHERE ID = ?1", nativeQuery = true)
	Lekar findById(int id);

	@Query(value = "SELECT * FROM LEKAR WHERE upper(IME) like ?1 and upper(prezime) like ?2 and klinika_id = ?3", nativeQuery = true)
	List<Lekar> findByImeAndPrezimeAndKlinika(String upperCase, String upperCase2, Integer idKlinike);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value = "SELECT l FROM Lekar AS l WHERE l.email = ?1")
	Lekar findByEmailLock(String email, LockModeType pessimisticWrite);
}
