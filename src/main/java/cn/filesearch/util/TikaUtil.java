package cn.filesearch.util;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class TikaUtil {

    public static String fileParser(File file) {

        String fileContent = "";
        BodyContentHandler handler = new BodyContentHandler();
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try {

            InputStream stream = TikaInputStream.get(file);
            ParseContext context = new ParseContext();
            parser.parse(stream, handler, metadata, context);

            fileContent = handler.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }
}
