package cn.filesearch;

import cn.filesearch.util.IndicesUtil;
import cn.filesearch.util.TikaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FilesearchApplication.class)
@WebAppConfiguration
public class FilesearchApplicationTests {

    @Test
    public void contextLoads() {

        String path = Thread.currentThread().getContextClassLoader()
                .getResource("docs/").getPath();

        System.out.println("path:" + path);
        File f = new File(path + "如何使用JSON.doc");
        System.out.println(f.exists());

        System.out.println(f.getName());
        System.out.println(TikaUtil.fileParser(f));

    }

    @Test
    public void testName(){

        System.out.println(IndicesUtil.getSuffix("a.docx"));
    }

}
