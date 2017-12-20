package cn.filesearch.services;

import cn.filesearch.util.IndicesUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Yao Pan on 17/12/19.
 */

@Service
public class LuceneService {

    private Directory directory;
    private IndexWriter indexWriter;

    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    public Analyzer getAnalyzer() {
        return new JcsegAnalyzer(JcsegTaskConfig.COMPLEX_MODE);
    }

    public IndexWriter getIndexWriter() {

        Analyzer analyzer = getAnalyzer();

        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);


//        Path indicesPath = Paths.get(Thread.currentThread().getContextClassLoader()
//                .getResource(IndicesUtil.INDICES_PATH).getPath());


        Path indicesPath = Paths.get(IndicesUtil.INDICES_PATH);
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


}
