package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.pojo.Gbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GbookServiceTest {
    @Autowired
    GbookService gbookService;

    @Test
    public void testGetGbookVolumnInfo() {
        String isbn = "0716604892";
        Gbook.VolumeInfo gbook = gbookService.getGbook(isbn);
    }
}
