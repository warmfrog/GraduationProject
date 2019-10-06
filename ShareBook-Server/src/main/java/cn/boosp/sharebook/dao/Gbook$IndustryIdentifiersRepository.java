package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Gbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface Gbook$IndustryIdentifiersRepository extends JpaRepository<Gbook.IndustryIdentifier, Integer> {
     List<Gbook.IndustryIdentifier> findAllByIdentifier(String isbn);
}
