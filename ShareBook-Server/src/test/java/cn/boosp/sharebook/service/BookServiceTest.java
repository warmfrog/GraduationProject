package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Book;
import cn.boosp.sharebook.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    public void testAddBook(){

        String[]books ={"9787115351708",
                "9787111467045" ,
                "9787111365709" ,
                "9787111421900",
                "9787111321880",
                "9787121282089",
                "9787115433145",
                "9787115209429"};
        for(int i=0;i< books.length;i++){
            String isbn = books[i];
            bookService.addBook(isbn);
            assertNotNull(bookService.get(isbn));
        }
    }

    @Test
    public void findBookByISBN(){
        Book book = bookService.get("9787111365709");
        assertTrue(!book.getAuthor().isEmpty());
    }
}
