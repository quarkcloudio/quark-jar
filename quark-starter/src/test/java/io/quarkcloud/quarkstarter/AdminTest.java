package io.quarkcloud.quarkstarter;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.mapper.UserMapper;

@SpringBootTest
public class AdminTest {

    @Autowired
    private UserMapper adminMapper;

    @Test
    public void testSelect() {
        List<UserEntity> userList = adminMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
