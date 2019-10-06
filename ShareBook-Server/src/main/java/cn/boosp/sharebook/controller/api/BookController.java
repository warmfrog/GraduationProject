package cn.boosp.sharebook.controller.api;

import cn.boosp.sharebook.common.ApiResponse;
import cn.boosp.sharebook.common.ApiResponse.Status;
import cn.boosp.sharebook.common.dto.BookDTO;
import cn.boosp.sharebook.common.exception.BookException;
import cn.boosp.sharebook.common.exception.BookNotFoundException;
import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.common.pojo.Gbook;
import cn.boosp.sharebook.service.BookService;
import cn.boosp.sharebook.service.GbookService;
import cn.boosp.sharebook.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    StoreService storeService;
    @Autowired
    GbookService gbookService;

    @ResponseBody
    @GetMapping("")
    public ApiResponse list(Integer index, Integer size) {
        Page<Book> books = bookService.listBooks(index, size);
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book : books) {
            bookDTOList.add(new BookDTO(book));
        }
        return new ApiResponse(Status.ok, bookDTOList);
    }

    @GetMapping("{isbn13}/image/{size}")
    public void getImage(@PathVariable String isbn13, String size, HttpServletResponse response) {
        String url;
        Book book = bookService.get(isbn13);
        if (book != null) {
            Book.Images images = book.getImages();
            if ("s" == size) {
                url = images.getLocalSmall();
            } else if ("l" == size) {
                url = images.getLocalLarge();
            } else {
                url = images.getLocalMedium();
            }
        } else {
            return;
        }
        try {
            Path path = Paths.get(url);
            if (path.toFile().exists()) {
                response.setContentType("image/jpg");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "");
                try {
                    OutputStream outputStream = response.getOutputStream();
                    Files.copy(path, outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (InvalidPathException e){
                response.setStatus(404);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ApiResponse getBook(@PathVariable(name = "id") Integer id) {
        Book book = null;
        try {
            book = bookService.getBookById(id);
            return new ApiResponse(Status.ok, new BookDTO(book));
        } catch (BookNotFoundException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(400, e.getMessage()));
        }

    }

    @ResponseBody
    @PostMapping("/isbn/{isbn}")
    public ApiResponse addBook(@PathVariable(name = "isbn") String isbn) {
        try {
            Book book = bookService.addBook(isbn);
            if (null != book) {
                return new ApiResponse(Status.ok, new BookDTO(book));
            } else {
                return new ApiResponse(Status.error, null, new ApiResponse.ApiError(400, "未查询到该书信息"));
            }
        } catch (RestClientException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(500, "服务器错误，豆瓣api挂了，请用googleapi查询"));
        }
    }

    @ResponseBody
    @DeleteMapping("")
    public ApiResponse deleteBook(Integer id) {
        Boolean success = null;
        try {
            success = bookService.delete(id);
            return new ApiResponse(Status.ok, null);
        } catch (BookException e) {
            return new ApiResponse(Status.error, null, new ApiResponse.ApiError(400, e.getMessage()));
        }
    }
}
