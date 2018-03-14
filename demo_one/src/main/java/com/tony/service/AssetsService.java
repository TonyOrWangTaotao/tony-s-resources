package com.tony.service;

import com.tony.domain.Assets;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liam on 2018-03-13 19:57:53
 */
public interface AssetsService extends BaseService<Assets> {
    List<Assets> queryAssetsList();
}