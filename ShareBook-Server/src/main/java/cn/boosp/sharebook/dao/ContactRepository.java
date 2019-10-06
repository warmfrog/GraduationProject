package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
