package main.mrs.repository;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;


@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable> extends Repository<T, ID> {

	Iterable<T> findAll(Pageable sort);

	<S extends T> S save(S entity);
}