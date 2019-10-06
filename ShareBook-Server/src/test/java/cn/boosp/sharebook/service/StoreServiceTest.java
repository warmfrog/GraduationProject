package cn.boosp.sharebook.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StoreServiceTest {
    @Test
    public void testCreateFile(){
        ClassPathResource resource = new ClassPathResource("Hello.txt");
        System.out.println("根路径：" + resource.getPath());
        File file = new File("Hello.txt");
        System.out.println(file.getAbsolutePath());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write("hello,world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveImage(){
        String urlAddress = "https://img1.doubanio.com/view/subject/m/public/s1959967.jpg";
        try {
            URL url = new URL(urlAddress);
            System.out.println("urltostring " + url.toString());
            System.out.println("文件名：" + url.getFile());
            System.out.println("路径：" + url.getPath());
            System.out.println("Authority:" + url.getAuthority());
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Ref:" + url.getRef());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
