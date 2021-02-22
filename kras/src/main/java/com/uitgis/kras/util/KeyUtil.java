package com.uitgis.kras.util;

import java.util.UUID;

public class KeyUtil {

	public static String getUUID () {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
