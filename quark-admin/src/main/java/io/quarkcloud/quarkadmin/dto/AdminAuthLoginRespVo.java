package io.quarkcloud.quarkadmin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminAuthLoginRespVo {

    /**
     * 登录凭证
     */
    private String token;
}
