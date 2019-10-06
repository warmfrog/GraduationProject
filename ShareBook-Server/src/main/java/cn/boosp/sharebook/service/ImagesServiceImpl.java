package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Gbook;
import cn.boosp.sharebook.dao.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
public class ImagesServiceImpl implements ImagesService {
    @Autowired
    ImagesRepository imagesRepository;
    @Autowired
    StoreService storeService;

    @Override
    public void save(Book.Images images) {
        Book.Images imagesRef = images;
        try {
            Map<String, String> storeUrl = storeService.saveImages(imagesRef);
            imagesRef.setLocalLarge(storeUrl.get("largeP"));
            imagesRef.setLocalMedium(storeUrl.get("mediumP"));
            imagesRef.setLocalSmall(storeUrl.get("smallP"));

            imagesRepository.save(imagesRef);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Gbook.ImageLinks imageLinks) {

    }
}
