package io.quarkcloud.quarkstarter;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.quarkcloud.quarkadmin.entity.AdminEntity;
import io.quarkcloud.quarkadmin.mapper.AdminMapper;

@SpringBootTest
public class AdminTest {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testSelect() {
        List<AdminEntity> userList = adminMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
