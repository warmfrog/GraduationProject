package cn.booksp.sharebook.repository.dao;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.booksp.sharebook.repository.api.BookAPI;
import cn.booksp.sharebook.BasicApp;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by warmfrog on 2019/3/6.
 */

public class FileDao {
    static FileDao fileDao;
    static Context appContext;
    static File imgPath;
    static BookAPI bookAPI = BasicApp.getBookAPI();

    private FileDao(Context appContext) {
        this.appContext = appContext;
        imgPath = new File(appContext.getFilesDir(), "BookCover");
        if (!imgPath.exists()) {
            imgPath.mkdir();
        }
    }

    public static FileDao getInstance(Context appContext) {
        if (null == fileDao) {
            synchronized (FileDao.class){
                if(fileDao == null){
                    fileDao = new FileDao(appContext);
                }
            }
        }
        return fileDao;
    }

    public static Uri save(String isbn13, InputStream in) {
        String filename = isbn13 + ".jpg";
        File imgFile = null;
        try {
            imgFile = new File(imgPath, filename);
            OutputStream out = new FileOutputStream(imgFile);
            byte[] buf = new byte[2000];
            int len = in.read(buf);
            while (len > 0) {
                out.write(buf, 0, len);
                len = in.read(buf);
            }
            in.close();
            out.close();
            Log.d("输入输出流", "正常关闭");
        } catch (FileNotFoundException e) {
            Log.e("保存异常", "文件不存在");
        } catch (IOException e) {
            Log.e("IO异常", e.getMessage());
        }
        return Uri.fromFile(imgFile);
    }

    public static void clearBuffer() {
        delete(imgPath);
        Log.d("当前路径", imgPath.getAbsolutePath());
    }

    private static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            Log.d("删除文件", file.getAbsolutePath());
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                delete(f);
                Log.d("路径", file.getAbsolutePath());
            }
        }
    }

    public static Uri getImg(String isbn13) {
        File imgFile = new File(imgPath, isbn13 + ".jpg");
        if (imgFile.exists()) {
            Log.d("本地存在该图片", imgFile.getAbsolutePath());
            return Uri.fromFile(imgFile);
        } else {
            Log.d("本地不存在该文件", imgFile.getAbsolutePath());
            return null;
        }
    }

    public static boolean existsBookCover(String  isbn13) {
        File imgFile = new File(imgPath, isbn13 + ".jpg");
        return imgFile.exists();
    }

    public static Uri downloadImg(String isbn13) {
        /**
         * 同时下载封面图
         */
        Uri img = getImg(isbn13);
        Uri save = null;
        if (null == img) {
            Call<ResponseBody> imgCall = bookAPI.getImage(isbn13);
            try {
                Response<ResponseBody> imgResponse = imgCall.execute();
                ResponseBody body = imgResponse.body();
                if(body != null){
                    save = fileDao.save(isbn13, body.byteStream());
                    Log.d("下载封面成功", ".");
                }
            } catch (IOException e) {
                Log.e("网络错误", "从网络下载封面图失败");
            }
        }
        return save;
    }
}
