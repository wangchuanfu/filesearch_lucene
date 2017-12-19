package cn.filesearch.util;


import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class LuceneUtil {


    public static FieldType getFieldType1() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setStoreTermVectorOffsets(true);
        fieldType.setStoreTermVectorPositions(true);
        return fieldType;
    }

    public static FieldType getFieldType2() {
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS);
        fieldType.setStored(true);

        return  fieldType;
    }
}
