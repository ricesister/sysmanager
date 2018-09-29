package cps.fs.APImanagerSys.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Created by LK on 2017/6/26.
 */
public class DjGson {

	public static Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

}