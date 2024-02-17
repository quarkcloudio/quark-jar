package io.quarkcloud.quarkadmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("`users`")
public class User extends Model<User> {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private boolean status;
}
