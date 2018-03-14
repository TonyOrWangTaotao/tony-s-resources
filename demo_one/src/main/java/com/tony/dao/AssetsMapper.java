package com.tony.dao;

import com.tony.domain.Assets;
import com.jy.medusa.commons.Mapper;

import java.util.List;

/**
 * Created by liam on 2018-03-13 19:57:53
 */
public interface AssetsMapper extends Mapper<Assets> {
    List<Assets> queryAssetsList();
}