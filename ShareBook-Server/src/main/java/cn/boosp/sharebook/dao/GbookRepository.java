package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Gbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GbookRepository extends JpaRepository<Gbook, Integer> {
}
