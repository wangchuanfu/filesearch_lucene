package cn.filesearch.controller;

import cn.filesearch.model.DocModel;
import cn.filesearch.services.LuceneService;
import cn.filesearch.util.IndicesUtil;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Yao Pan on 17/12/19.
 */

@Controller
public class DocController {

    @Autowired
    LuceneService luceneService;

    @RequestMapping(path = "/searchfile", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchFile(ModelMap map, @RequestParam("key") String key) {


        if (StringUtils.isBlank(key)) {
            return "redirect:/";
        }

        String[] searchFields = {"title", "content"};
        IndexSearcher indexSearcher = luceneService.getIndexSearcher();

        ArrayList<DocModel> docList = new ArrayList<>();

        MultiFieldQueryParser parser = new MultiFieldQueryParser(searchFields, luceneService.getAnalyzer());

        try {
            Query query = parser.parse(key);
            TopDocs hits = indexSearcher.search(query, 10);

            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style=\"color:red;\">", "</span>");
            QueryScorer scorerTitle = new QueryScorer(query, searchFields[0]);
            Highlighter hl_title = new Highlighter(formatter, scorerTitle);

            QueryScorer scorerContent = new QueryScorer(query, searchFields[1]);
            Highlighter hl_content = new Highlighter(formatter, scorerContent);


            for (ScoreDoc sd : hits.scoreDocs) {
                Document doc = indexSearcher.doc(sd.doc);
                String title = doc.get("title");
                String content = doc.get("content");
                String doctype = doc.get("doctype");


                TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),
                        sd.doc, searchFields[0], luceneService.getAnalyzer());

                Fragmenter fragmenter = new SimpleSpanFragmenter(scorerTitle);
                hl_title.setTextFragmenter(fragmenter);
                String hTitle = hl_title.getBestFragment(tokenStream, title);
                hTitle = StringUtils.isBlank(hTitle) ? title : hTitle;

                tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),
                        sd.doc, searchFields[1], luceneService.getAnalyzer());
                fragmenter = new SimpleSpanFragmenter(scorerContent);
                hl_content.setTextFragmenter(fragmenter);
                String hContent = hl_content.getBestFragment(tokenStream, content);
                hContent = StringUtils.isBlank(hContent) ? content : hContent;

                DocModel docModel = new DocModel(title, hTitle, content, hContent, doctype);
                docList.add(docModel);

            }
            map.addAttribute("docList", docList);
            map.addAttribute("keyback", key);

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


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void testDownload(HttpServletResponse res, String fileName) {
        System.out.println("filename: " + fileName);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        try {
            res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(IndicesUtil.DOC_PATH
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }


}
