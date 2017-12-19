package cn.filesearch.controller;

import cn.filesearch.model.DocModel;
import cn.filesearch.services.LuceneService;
import cn.filesearch.util.IndicesUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Yao Pan on 17/12/19.
 */

@Controller
public class DocController {

    @Autowired
    LuceneService luceneService;

    @RequestMapping(path = "/searchfile", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchFile(Model model, @RequestParam("key") String key) {


        String[] searchFields = {"title", "content"};
        IndexSearcher indexSearcher = luceneService.getIndexSearcher();

        ArrayList<DocModel> docList = new ArrayList<>();


        MultiFieldQueryParser parser = new MultiFieldQueryParser(searchFields, luceneService.getAnalyzer());

        try {
            Query query = parser.parse(key);
            TopDocs hits = indexSearcher.search(query, 10);

            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style=\"color:red;\">", "</span>");
            QueryScorer scorerTitle = new QueryScorer(query, searchFields[0]);
            Highlighter hTitle = new Highlighter(formatter, scorerTitle);

            QueryScorer scorerContent = new QueryScorer(query, searchFields[1]);
            Highlighter hContent = new Highlighter(formatter, scorerContent);


            for (ScoreDoc sd : hits.scoreDocs) {
                Document doc = indexSearcher.doc(sd.doc);
                String title = doc.get("title");
                String content = doc.get("content");
                String doctype = doc.get("doctype");


                TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),
                        sd.doc, searchFields[0], luceneService.getAnalyzer());

                Fragmenter fragmenter = new SimpleSpanFragmenter(scorerTitle);
                hTitle.setTextFragmenter(fragmenter);
                String hl_title = hTitle.getBestFragment(tokenStream, title);

                tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),
                        sd.doc, searchFields[1], luceneService.getAnalyzer());
                fragmenter = new SimpleSpanFragmenter(scorerContent);
                hContent.setTextFragmenter(fragmenter);
                String hl_content = hContent.getBestFragment(tokenStream, content);

                DocModel docModel = new DocModel(hl_title != null ? hl_title : title,
                        hl_content != null ? hl_content : content, doctype);
                docList.add(docModel);

            }
            model.addAttribute("docList", docList);

            System.out.println(docList);


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }

        luceneService.afterSearch();

        return "result";
    }
}
