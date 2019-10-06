package cn.boosp.sharebook.config;

import org.hibernate.dialect.MySQL5Dialect;

public class MysqlConfig extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
