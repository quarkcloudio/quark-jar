package io.quarkcloud.quarkadmin.component;

import java.util.Map;
import java.util.UUID;

import lombok.experimental.Accessors;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Accessors(chain = true)
public class Component {

    // 组件Key
    public String componentKey;

    // 组件Key
    public String componentkey;

    // 组件
	public String component;

    // 组件样式
	public Map<String, Object> style;

    // 组件Key
    public void setComponentKey() {
        setComponentKey("",true);
    }

    // 组件Key
    public void setComponentKey(String defaultKey) {
        setComponentKey(defaultKey,true);
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
                byte[] digest = md.digest(defaultKey.getBytes());
                defaultKey = bytesToHex(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        this.componentKey = this.componentkey = defaultKey;
    }

    public String getComponent() {
        return this.component;
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
