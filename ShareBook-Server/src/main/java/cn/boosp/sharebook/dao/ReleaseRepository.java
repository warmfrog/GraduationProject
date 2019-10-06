package cn.boosp.sharebook.dao;

import cn.boosp.sharebook.common.pojo.Release;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReleaseRepository extends PagingAndSortingRepository<Release, Integer> {
}
