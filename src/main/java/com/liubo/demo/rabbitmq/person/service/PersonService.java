package com.liubo.demo.rabbitmq.person.service;

import com.liubo.demo.rabbitmq.person.model.PersonDO;

import java.util.List;

/**
 *
 */
public interface PersonService {

    /**
     * 添加
     *
     * @param personDO
     * @return
     * @throws Exception
     */
    boolean addPerson(PersonDO personDO) throws Exception;


    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean removePerson(String id) throws Exception;

    /**
     * 修改
     *
     * @param person
     * @return
     * @throws Exception
     */
    boolean modifyPerson(PersonDO person) throws Exception;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    PersonDO getPerson(String id) throws Exception;

    /**
     * 查询列表
     *
     * @param person
     * @return
     * @throws Exception
     */
    List<PersonDO> queryPersonList(PersonDO person) throws Exception;
}
