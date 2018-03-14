package com.tony.service.impl;

import com.jy.medusa.commons.Mapper;
import com.tony.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	protected Mapper<T> mapper;

	public int selectCount(Object... ps) {
		return mapper.selectCount(ps);
	}

	public List<T> selectAll(Object... ps) {
		return mapper.selectAll(ps);
	}

	public T selectOne(T entity, Object... ps) {
		return mapper.selectOne(entity, ps);
	}

	public T findById(Integer id, Object... ps) {
		return mapper.selectByPrimaryKey(id, ps);
	}

	public List<T> findListBy(T entity, Object... ps) {
		return mapper.select(entity, ps);
	}

	public int saveOrUpdate(T entity) {
		return 0;//TODO
	}

	public int save(T entity) {
		return mapper.insertSelective(entity);
	}

	public int saveBatch(List<T> obs) {
		return mapper.insertBatch(obs);
	}

	public int update(T entity,Object... ps) {
		return mapper.updateByPrimaryKey(entity,ps);
	}

	public int updateSelective(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	public int deleteBatch(List<Integer> ids) {
		return mapper.deleteBatch(ids);
	}

	public int deleteBy(T entity) {
		return mapper.delete(entity);
	}

	public List<T> selectByGaze(Object... ps) {
		return mapper.showMedusaGaze(ps);
	}
}