package cn.boosp.sharebook.service;

import cn.boosp.sharebook.common.exception.BookException;
import cn.boosp.sharebook.common.pojo.Gbook;
import cn.boosp.sharebook.dao.Gbook$IndustryIdentifiersRepository;
import cn.boosp.sharebook.dao.GbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GbookServiceImpl implements GbookService {
    @Autowired
    GbookRepository gbookRepository;
    @Autowired
    Gbook$IndustryIdentifiersRepository gbook$IndustryIdentifiersRepository;

    public static final String GOOGLE_API = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional
    public Gbook.VolumeInfo getGbook(String isbn) {
        List<Gbook.IndustryIdentifier> list = gbook$IndustryIdentifiersRepository.findAllByIdentifier(isbn);
        if(list.size()< 1){
            String url = GOOGLE_API + isbn;
            Gbook gbook = restTemplate.getForObject(url, Gbook.class);
            if (gbook.getTotalItems()>0){
                gbookRepository.save(gbook);
                list = gbook$IndustryIdentifiersRepository.findAllByIdentifier(isbn);
            }
        }
        if(list.size() < 1){
            return null;
        }else {
            return list.get(0).getVolumeInfo();
        }
    }

    @Override
    public Gbook getBook(Integer id) throws BookException {
       return gbookRepository.findById(id).orElseThrow(
                () -> new BookException()
        );
    }
}
