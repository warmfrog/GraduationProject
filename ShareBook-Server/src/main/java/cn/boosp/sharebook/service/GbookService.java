package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.BookException;
import cn.boosp.sharebook.common.pojo.Gbook;

public interface GbookService {

    Gbook.VolumeInfo getGbook(String isbn);

    Gbook getBook(Integer id) throws BookException;
}
