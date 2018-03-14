package com.tony.service.impl;

import com.tony.domain.Assets;
import javax.annotation.Resource;
import com.tony.dao.AssetsMapper;
import com.tony.service.AssetsService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liam on 2018-03-13 19:57:53
 */
@Service
public class AssetsServiceImpl extends BaseServiceImpl<Assets> implements AssetsService {

	@Resource
	private AssetsMapper assetsMapper;

	public List<Assets> queryAssetsList() {

		return assetsMapper.queryAssetsList();
	}
}