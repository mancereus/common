package de.db12.common.db.mapper;

import java.util.List;

public interface CRUDMapper<T> {
	void add(T object);

	void update(T object);

	void delete(Integer id);

	T getById(Integer id);

	List<T> selectAll();

}
