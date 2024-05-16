package io.quarkcloud.quarkadmin.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Admin {
    private Long id;
    private String username;
    private String nickname;
    private int sex;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private String lastLoginIp;
    private Date lastLoginTime;
    private int status;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
