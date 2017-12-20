package cn.filesearch.util;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class IndicesUtil {

    public static String INDICES_PATH = "/Users/bee/Documents/bookspace/filesearch/src/main/resources/indices";
    public static String DOC_PATH = "/Users/bee/Documents/bookspace/filesearch/src/main/resources/docs/";

    public static String DOCTYPE_TXT = "txt";
    public static String DOCTYPE_DOC = "doc";
    public static String DOCTYPE_DOCX = "docx";
    public static String DOCTYPE_PDF = "pdf";
    public static String DOCTYPE_EXCEL = "excel";
    public static String DOCTYPE_PPT = "ppt";
    public static String DOCTYPE_PPTX = "pptx";


    public static String getFileTitle(String filename) {

        String[] strArr = filename.split("\\.");

        return strArr[0];
    }

    public static String getSuffix(String filename) {

        String[] strArr = filename.split("\\.");

        return strArr[strArr.length - 1];
    }

}
