package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Gbook;

public interface ImagesService {
    void save(Book.Images images);
    void save(Gbook.ImageLinks imageLinks);
}
