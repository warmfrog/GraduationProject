package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Book;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public interface StoreService {
    void init() throws IOException;

    Map<String,String> saveImages(Book.Images imagesUrl) throws MalformedURLException;

    Resource loadAsResource(String url);
}
