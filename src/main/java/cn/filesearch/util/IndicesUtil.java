package cn.filesearch.util;

import org.springframework.util.ClassUtils;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class IndicesUtil {

    public static String INDICES_PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "indices";
    public static String DOC_PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "docs/";


    public static String getFileTitle(String filename) {
        String[] strArr = filename.split("\\.");
        return strArr[0];
    }

    public static String getSuffix(String filename) {
        String[] strArr = filename.split("\\.");
        return strArr[strArr.length - 1];
    }

}
