package cps.fs.APImanagerSys.controller;

import cps.fs.APImanagerSys.common.util.MapCache;

/**
 * 基类控制器
 * @author fs
 * @date 2018年9月20日
 * @description
 */
public abstract class BaseController {

	protected MapCache cache = MapCache.single();
}
