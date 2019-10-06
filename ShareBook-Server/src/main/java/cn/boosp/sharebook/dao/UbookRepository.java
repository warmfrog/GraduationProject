package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Ubook;
import cn.boosp.sharebook.common.pojo.Ubook.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UbookRepository extends JpaRepository<Ubook, Integer> {
    public List<Ubook> findAllByIsbn13In(List<String> isbns);

    public Integer deleteByIsbn13(String isbn13);

    public List<Ubook> findTop10ByIdAfterAndStatusEquals(Integer id, Status status);

    public List<Ubook> findTop10ByIdBeforeAndStatusEquals(Integer id, Status status);

    public List<Ubook> findByStatusEqualsAndIdBefore(Status status, Integer id);

    public List<Ubook> findByStatusEqualsAndIdAfter(Status status, Integer id);

    public List<Ubook> findTop10ByStatusEquals(Status status);

    public Page<Ubook> findAllByStatusEquals(Pageable pageable, Status status);
}
