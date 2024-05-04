package io.quarkcloud.quarkadmin.commponent;

import java.util.Map;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Commponent {

    // 组件Key
    public String componentKey;

    // 组件
	public String component;

    // 组件样式
	public Map<String, ?> style;

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
