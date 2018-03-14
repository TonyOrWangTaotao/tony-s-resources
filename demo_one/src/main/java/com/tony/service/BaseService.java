package com.tony.service;

import java.util.List;

public interface BaseService<T> {

	int selectCount(Object... ps);

	List<T> selectAll(Object... ps);

	T selectOne(T entity, Object... ps);

	T findById(Integer id, Object... ps);

	List<T> findListBy(T entity, Object... ps);

	int saveOrUpdate(T entity);

	int save(T entity);

	int saveBatch(List<T> obs);

	int update(T entity, Object... ps);

	int updateSelective(T entity);

	int deleteById(Integer id);

	int deleteBatch(List<Integer> ids);

	int deleteBy(T entity);

	List<T> selectByGaze(Object... ps);
}