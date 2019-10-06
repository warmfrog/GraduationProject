package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Book;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class StoreServiceImpl implements StoreService {
    static final String BASE_PATH = "D://subjects";
    static final String SMALL_PATH = BASE_PATH + "/s";
    static final String MEDIUM_PATH = BASE_PATH + "/m";
    static final String LARGE_PATH = BASE_PATH + "/l";

    String basePath = null;
    String largeImgPath = null;
    String mediumImgPath = null;
    String smallImgPath = null;

    Properties properties = new Properties();
    File configFile = new File("sharebook-config.properties");

    public void init() throws IOException {
        /**
         * 如果配置文件不存在，则创建默认配置文件
         */
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                /**
                 * 创建存储目录
                 */
                if (!(new File(BASE_PATH)).exists()) {
                    Files.createDirectory(Paths.get(BASE_PATH));
                }if (!(new File(LARGE_PATH)).exists()) {
                    Files.createDirectory(Paths.get(LARGE_PATH));
                }
                if (!(new File(SMALL_PATH)).exists()) {
                    Files.createDirectory(Paths.get(SMALL_PATH));
                }
                if (!(new File(MEDIUM_PATH)).exists()) {
                    Files.createDirectory(Paths.get(MEDIUM_PATH));
                }

                /**
                 * 保存属性到文件
                 */
                properties.setProperty("base_path", BASE_PATH);
                basePath = BASE_PATH;
                properties.setProperty("small_img_path", SMALL_PATH);
                smallImgPath = SMALL_PATH;
                properties.setProperty("medium_img_path", MEDIUM_PATH);
                mediumImgPath = MEDIUM_PATH;
                properties.setProperty("large_img_path", LARGE_PATH);
                largeImgPath = LARGE_PATH;
                FileOutputStream out = new FileOutputStream(configFile);
                properties.store(out, "set default store path");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            if (null == basePath ||
                    null == smallImgPath ||
                    null == mediumImgPath ||
                    null == largeImgPath) {
                /**
                 * 从文件载入配置
                 */
                FileInputStream inputStream = new FileInputStream(configFile);
                properties.load(inputStream);
                inputStream.close();

                if (null == basePath) {
                    basePath = properties.getProperty("base_path");
                    if (null == basePath) {
                        properties.setProperty("base_path", BASE_PATH);
                        basePath = BASE_PATH;
                    }
                }
                if (null == smallImgPath) {
                    smallImgPath = properties.getProperty("small_img_path");
                    if (null == smallImgPath) {
                        properties.setProperty("small_img_path", SMALL_PATH);
                        smallImgPath = SMALL_PATH;
                    }
                }
                if (null == mediumImgPath) {
                    mediumImgPath = properties.getProperty("medium_img_path");
                    if (null == mediumImgPath) {
                        properties.setProperty("medium_img_path", MEDIUM_PATH);
                        mediumImgPath = MEDIUM_PATH;
                    }
                }
                if (null == largeImgPath) {
                    largeImgPath = properties.getProperty("large_img_path");
                    if (null == largeImgPath) {
                        properties.setProperty("large_img_path", LARGE_PATH);
                        largeImgPath = LARGE_PATH;
                    }
                }

                FileOutputStream out = new FileOutputStream(configFile);
                properties.store(out, "set default config");
                out.close();
            }
        }
    }

    @Override
    public Map<String,String> saveImages(Book.Images imagesUrl) throws MalformedURLException {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String smallP = saveBookCover(smallImgPath, new URL(imagesUrl.getSmall()));
        String mediumP = saveBookCover(mediumImgPath, new URL(imagesUrl.getMedium()));
        String largeP = saveBookCover(largeImgPath, new URL(imagesUrl.getLarge()));
        Map<String,String> storeUrl = new HashMap<>();
        storeUrl.put("smallP", smallP);
        storeUrl.put("mediumP",mediumP);
        storeUrl.put("largeP",largeP);
        return storeUrl;
    }

    @Override
    public Resource loadAsResource(String url) {
        FileSystemResource fileSystemResource = new FileSystemResource(url);
        return fileSystemResource;
    }

    private String saveBookCover(String path, URL url) {
        String imgPath = null;
        try {
            String a = url.toString();
            String imgName = a.substring(a.lastIndexOf("/"));
            InputStream in = url.openStream();
            imgPath = path + imgName;
            File imgFile = new File(imgPath);
            FileOutputStream out = new FileOutputStream(imgFile);
            byte[] temp = new byte[2000];
            int len = in.read(temp);
            while (len > 0) {
                out.write(temp, 0, len);
                len = in.read(temp);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgPath;
    }
}
