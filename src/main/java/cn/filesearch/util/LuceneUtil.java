package cn.filesearch.util;


import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class LuceneUtil {


    /**
     * 设置字段不索引
     *
     * @return
     */
    public static FieldType getFieldType1() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.NONE);
        return fieldType;
    }

    /**
     * 设置字段只存储文档原始值
     *
     * @return
     */
    public static FieldType getFieldType2() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS);
        fieldType.setStored(true);
        return fieldType;
    }


    /**
     * 设置字段存储文档原始值、词频
     *
     * @return
     */
    public static FieldType getFieldType3() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        fieldType.setStored(true);
        return fieldType;
    }


    /**
     * 设置字段存储文档原始值、词频、词项位置
     *
     * @return
     */
    public static FieldType getFieldType4() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        fieldType.setStored(true);
        return fieldType;
    }


    /**
     * 设置字段存储文档原始值、词频、词项位置、偏移信息
     *
     * @return
     */
    public static FieldType getFieldType5() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setStoreTermVectorOffsets(true);
        fieldType.setStoreTermVectorPositions(true);
        return fieldType;
    }
}
