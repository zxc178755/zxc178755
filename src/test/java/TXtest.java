import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TXtest
{
    TextCheck ts;
    String path1;
    String path2;
    String outputPath;
    String firstSentence;

    @Parameterized.Parameters
    public static Collection initData()
    {
        //参数为各个对比文章的绝对地址和文章的第一句话
        Object[][] objects = {{"testArticle\\orig.txt","活着前言"},
                {"testArticle\\orig_0.8_del.txt","活着言"},
                {"testArticle\\orig_0.8_dis_1.txt","活着言前"},
                {"testArticle\\orig_0.8_dis_10.txt","活着真正"},
                {"testArticle\\orig_0.8_dis_15.txt","的活着前言"}};
        return Arrays.asList(objects);
    }
    public TXtest(String path2, String firstSentence)
    {
        this.path1 = "testArticle\\orig.txt";//源文章地址
        this.path2 = path2;               //对比文章地址
        this.outputPath = "testArticle\\orig_output.txt";//输出文件地址
        this.firstSentence = firstSentence;//对比文章的第一句话
    }
    @Before
    public void createTestSimilarity()
    {
        ts = new TextCheck(path1, path2,outputPath);
    }
    @Test
    public void testArticleAFirstSentence()
    {
        assertNotEquals(0,ts.Article1.size());
        assertEquals("活着前言",ts.Article1.get(0));

    }
    @Test
    public void testArticleBFirstSentence()
    {
        assertNotEquals(0,ts.Article1.size());
        assertEquals(firstSentence,ts.Article2.get(0));
    }
    @Test
    public void testSimilarity()
    {
        double similarity = 0.333333333;
        assertEquals("0.33",ts.Similarity(similarity));
    }
    @Test
    public void testFilterSentence()
    {
        String str = "     我是一个句子      ";
        assertEquals("我是一个句子",ts.FilterSentence(str));
    }
    @Test
    public void testCreateOutputFile()
    {
        String similarity = "0.96";
        ts.OutputFile(similarity);
        File file = new File(this.outputPath);
        assertTrue(file.exists());
    }
    @Test
    public void testFilenotfound() throws Exception{
        try {
            TextCheck textCheck = new TextCheck("0", "1", "3");
        }catch (Exception e){
            throw new Exception("wrong");
        }

    }

}