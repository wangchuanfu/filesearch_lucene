package cn.filesearch.services;

import cn.filesearch.util.IndicesUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Yao Pan on 17/12/19.
 */

@Service
public class LuceneService {

    private Directory directory;  //索引文件目录
    private IndexWriter indexWriter;  //IndexWriter对象用于写索引
    private IndexReader indexReader;//IndexReader对象用于读索引


    /**
     * 返回Jcseg分词器
     *
     * @return
     */
    public Analyzer getAnalyzer() {
        return new JcsegAnalyzer(JcsegTaskConfig.COMPLEX_MODE);
    }

    /**
     * 返回IndexWriter
     *
     * @return
     */
    public IndexWriter getIndexWriter() {
        Analyzer analyzer = getAnalyzer();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        Path indicesPath = Paths.get(IndicesUtil.INDICES_PATH);


        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        try {
            directory = FSDirectory.open(indicesPath);
            indexWriter = new IndexWriter(directory, icw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }


    public void afterIndex() {
        try {
            indexWriter.commit();
            indexWriter.close();
            directory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IndexSearcher getIndexSearcher() {
        Path indicesPath = Paths.get(IndicesUtil.INDICES_PATH);
        try {
            directory = FSDirectory.open(indicesPath);
            indexReader = DirectoryReader.open(directory);

            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            return indexSearcher;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void afterSearch() {

        try {
            directory.close();
            indexReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean deleteIndex(String field, String key) {
        IndexWriter indexWriter = getIndexWriter();
        TermQuery termQuery = new TermQuery(new Term(field, key));
        try {
            long seqNo = indexWriter.deleteDocuments(termQuery);

            return seqNo > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
