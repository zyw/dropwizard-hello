package cn.c5cn.dropwizard.dao;

import cn.c5cn.dropwizard.core.Contact;
import cn.c5cn.dropwizard.dao.mappers.ContactMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * Created by ZYW on 2014/6/17.
 */
public interface ContactDAO {

    @Mapper(ContactMapper.class)
    @SqlQuery("select * from contact where id=:id")
    Contact getContactById(@Bind("id") int id);

    @GetGeneratedKeys
    @SqlUpdate("insert into contact(id,firstName,lastName,phone)" +
            " values(NULL,:firstName,:lastName,:phone)")
    int createContact(@Bind("firstName")String firstName,
                      @Bind("lastName")String lastName,@Bind("phone")String phone);
}
