package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.BookException;
import cn.boosp.sharebook.common.exception.BookIdIsNullException;
import cn.boosp.sharebook.common.exception.BookNotFoundException;
import cn.boosp.sharebook.common.pojo.*;
import cn.boosp.sharebook.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    public final String apiDouban = "https://api.douban.com/v2/book/isbn/";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    BookRepository bookRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    SeriesRepository seriesRepository;
    @Autowired
    ImagesService imagesService;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    StoreService storeService;

    @Override
    public Book addBook(String isbn13) throws RestClientException {
        Book book = null;
        if (bookRepository.existsBookByIsbn13(isbn13)) {
            book = bookRepository.findBookByIsbn13(isbn13);
            return book;
        } else {
            String url = apiDouban + isbn13;
            book = restTemplate.getForObject(url, Book.class);
            Book.Rating rating = book.getRating();
            ratingRepository.save(rating);
            Book.Series series = book.getSeries();
            Book.Images images = book.getImages();
            List<Book.Tag> tags = book.getTags();

            tagRepository.saveAll(tags);
            imagesService.save(images);
            if (series != null && !seriesRepository.existsById(series.getId())) {
                seriesRepository.save(series);
            }

            Book save = bookRepository.save(book);
            return save;
        }
    }

    @Override
    public Boolean delete(Integer id)throws BookException {
        if (id == null) {
            throw new BookIdIsNullException();
        } else if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        } else {
            bookRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book get(String isbn13) {
        return bookRepository.findBookByIsbn13(isbn13);
    }

    @Override
    public Book getBookById(Integer id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException());
    }

    @Override
    public Page<Book> listBooks(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return bookRepository.findAll(pageable);
    }
}
