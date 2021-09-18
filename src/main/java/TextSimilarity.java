import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class TextCheck {
    private static final Logger logger = Logger.getLogger("TextCheck");
    List<String> Article1;//原文章
    List<String> Article2;//对比文章
    private String path1; //原文章目录
    private String path2;//对比文章目录
    private String outputPath;//输出文件目录

    public TextCheck(String path1, String path2, String outputPath) {
        Article1 = new ArrayList<>();
        Article2 = new ArrayList<>();
        this.path1 = path1;
        this.path2 = path2;
        this.outputPath = outputPath;

        /**
         * 把文章存放到ArrayList中
         */
        AddList(path1, Article1);
        AddList(path2, Article2);

        CosSimilarity cos = new CosSimilarity();
        cos.textSegment(Article1, 0);
        cos.textSegment(Article2, 1);


        /**
         * 输出文件
         */
        OutputFile(Similarity(cos.calculateCos()));

    }



    private void AddList(String path, List<String> list) {
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);) {

            String line;
            /*
             * 读取单行文本
             * */
            while ((line = br.readLine()) != null) {
                line = FilterSentence(line);
                if (!line.isEmpty()) {
                    list.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();//抛出异常
        }
    }

    /**
     * 过滤字符串
     *
     * @param sentence 需要过滤字符串
     * @return 过滤后
     */
    protected String FilterSentence(String sentence) {
        sentence = sentence.trim();//过滤前后空格
        return sentence;
    }

    /**
     * 得到相似度取小数点后两位
     *
     * @return
     */
    public String Similarity(Double cos) {
        return "0" + new DecimalFormat("#.000").format(cos).substring(0, 3);
//        return null;
    }

    protected void OutputFile(String similarity) {
        File outPutFile = new File(outputPath);
        try (FileOutputStream fos = new FileOutputStream(outPutFile);
             PrintWriter pw = new PrintWriter(fos);) {
            pw.write("源文件：" + path1 + "\r\n");
            pw.write("对比文件：" + path2 + "\r\n");
            pw.write("文本相似度：" + similarity);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextCheck("testArticle/orig.txt", "testArticle/orig_0.8_dis_15.txt", "testArticle/orig_output.txt");
    }

}
