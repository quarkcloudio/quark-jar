package io.quarkcloud.quarkadmin.commponent.form.fields;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class When {

    // 组件标识
	String componentKey;

    // 组件名称
	String component;

    // When组件中需要解析的元素
	WhenItem[] items;

    public When() {
        this.component = "whenField";
        this.setComponentKey("",true);
    }

    // 组件Key
    public void setComponentKey(String defaultKey, Boolean crypt) {
        if (defaultKey == "" || defaultKey == null) {
            defaultKey = UUID.randomUUID().toString();
        }

        if (crypt) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                
                // 计算消息的摘要
                byte[] digest = md.digest(defaultKey.getBytes());
                
                // 将摘要转换为十六进制字符串
                defaultKey = bytesToHex(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        this.componentKey = defaultKey;
    }

    // 将字节数组转换为十六进制字符串
    public String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
