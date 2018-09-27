package cps.fs.APImanagerSys.common.util;

import java.util.UUID;

/**
 * 主键生成，比如userid
 * @author fs
 * @date 2018年9月11日
 * @description
 */
public class UUIDUtil {
	public static String getID() {
		return UUID.randomUUID().toString().toLowerCase();
	}

}
