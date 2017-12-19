package cn.filesearch;

import cn.filesearch.util.IndicesUtil;
import cn.filesearch.util.TikaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
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
