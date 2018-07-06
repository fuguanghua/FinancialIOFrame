/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuguanghua.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.fuguanghua.mysql.bean.Person;

public class MyBatisTest {
    SqlSession session;

    @Before
    public void beforeLoadXML() {
        //加载 mybatis 配置文件
        InputStream inputStream = MyBatisTest.class.getClassLoader().getResourceAsStream(
            "com\\fuguanghua\\mysql\\mybatis\\mybatis-configuration.xml");
        //构建sqlSession的工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //根据 sqlSessionFactory 产生 session
        session = sqlSessionFactory.openSession();
    }

    //根据 pid 查询 person 表中的数据
    @Test
    public void testSelectById() {
        //这个字符串有 personMapper.xml 文件中 两个部分构成
        //<mapper namespace="com.ys.bean.personMapper"> 的 namespace 的值
        //<select id="selectPersonById" > id 值
        String statement = "com.fuguanghua.mysql.mybatis.personMapper" + ".selectPersonById";
        Person p = session.selectOne(statement, 1);
        System.out.println(p);
        session.close();
    }

    //查询person 表所有数据
    @Test
    public void testGetAllPerson() {
        String statement = "com.fuguanghua.mysql.mybatis.personMapper.getAllPerson";
        List<Person> listPerson = session.selectList(statement);
        System.out.println(listPerson);
        session.close();
    }

    //根据id更新数据
    @Test
    public void updateById() {
        String statement = "com.fuguanghua.mysql.mybatis.personMapper.updatePersonById";
        Person p = new Person();
        p.setPid(1);
        p.setPname("aaa");
        p.setPage(11);
        session.update(statement, p);
        session.commit();
        session.close();
    }

    //向 person 表插入一条数据
    @Test
    public void addPerson() {
        String statement = "com.fuguanghua.mysql.mybatis.personMapper.addPerson";
        Person p = new Person();
        //由于我们设置了主键的自增长机制，故这里不需要手动设置 pid 的值
        //p.setPid(1);
        p.setPname("add");
        p.setPage(11);
        session.insert(statement, p);
        session.commit();
        session.close();
    }

    //根据 pid 删除person 表中的数据
    @Test
    public void deletePersonById() {
        String statement = "com.fuguanghua.mysql.mybatis.personMapper.deletePersonById";
        session.delete(statement, 1);
        session.commit();
        session.close();

    }

}
