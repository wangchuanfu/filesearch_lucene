package cn.filesearch.services;

import cn.filesearch.util.IndicesUtil;
import cn.filesearch.util.LuceneUtil;
import cn.filesearch.util.TikaUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Yao Pan on 17/12/19.
 */

@Service
public class DocService implements InitializingBean {

    @Autowired
    LuceneService luceneService;

    @Override
    public void afterPropertiesSet() throws Exception {

        String docDirPath = Thread.currentThread().getContextClassLoader()
                .getResource("docs").getPath();

        File docDir = new File(docDirPath);
        File[] docList = docDir.listFiles();


        IndexWriter indexWriter = luceneService.getIndexWriter();
        for (File doc : docList) {
            Document document = new Document();
            document.add(new Field("title", IndicesUtil.getFileTitle(doc.getName()), LuceneUtil.getFieldType1()));
            document.add(new Field("content", TikaUtil.fileParser(doc), LuceneUtil.getFieldType1()));
            document.add(new Field("doctype", IndicesUtil.getSuffix(doc.getName()), LuceneUtil.getFieldType2()));
            indexWriter.addDocument(document);
        }


        luceneService.afterIndex();


    }
}
