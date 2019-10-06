package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByName(String name);
}
