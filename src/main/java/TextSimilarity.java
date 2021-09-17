
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class TextCheck {
    List<String> Article1;//ԭ����
    List<String> Article2;//�Ա�����
    private String path1; //ԭ����Ŀ¼
    private String path2;//�Ա�����Ŀ¼
    private String outputPath;//����ļ�Ŀ¼
    private static final Logger logger = Logger.getLogger("TextCheck");

    public TextCheck(String path1,String path2,String outputPath){
        Article1=new ArrayList<>();
        Article2=new ArrayList<>();
        this.path1= path1;
        this.path2= path2;
        this.outputPath= outputPath;

        /**
         * �����´�ŵ�ArrayList��
         */
        AddList(path1,Article1);
        AddList(path2,Article2);

        CosSimilarity cos=new CosSimilarity();
        cos.textSegment(Article1,0);
        cos.textSegment(Article2,1);


        /**
         * ����ļ�
         */
        OutputFile(Similarity(cos.calculateCos()));

    }

    private void AddList(String path,List<String> list){
        try(FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);){

            String line;
            /*
             * ��ȡ�����ı�
             * */
            while ((line=br.readLine())!=null){
                line = FilterSentence(line);
                if(!line.isEmpty()){
                    list.add(line);
                }
            }

        }catch (IOException e){
            e.printStackTrace();//�׳��쳣
        }
    }

    /**
     * �����ַ���
     * @param sentence ��Ҫ�����ַ���
     * @return ���˺�
     */
    protected String FilterSentence(String sentence){
        sentence =sentence.trim();//����ǰ��ո�
        return sentence;
    }

    /**
     * �õ����ƶȱ�ȡС�������λ
     * @return
     */
    public String Similarity(Double cos){
        return "0"+new DecimalFormat("#.000").format(cos).substring(0,3);
//        return null;
    }

    protected void OutputFile(String similarity){
        File outPutFile = new File(outputPath);
        try(FileOutputStream fos = new FileOutputStream(outPutFile);
            PrintWriter pw = new PrintWriter(fos);)
        {
            pw.write("Դ�ļ���"+path1+"\r\n");
            pw.write("�Ա��ļ���"+path2+"\r\n");
            pw.write("�ı����ƶȣ�"+similarity);
            pw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextCheck("testArticle/orig.txt", "testArticle/orig_0.8_add.txt","testArticle/orig_output2.txt");
    }
}
