package io.quarkcloud.quarkadmin.entity;

import org.casbin.jcasbin.main.Enforcer;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("casbin_rules")
public class CasbinRule extends Model<CasbinRule> {
    private Long id;
    private String ptype;
    private String v0;
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String v5;

    public CasbinRule enforcer() {
        org.casbin.jcasbin.model.Model model = new org.casbin.jcasbin.model.Model();
        model.loadModelFromText(
            "[request_definition]\r\n" +
            "r = sub, obj, act\r\n" +
            "[policy_definition]\r\n" +
            "p = sub, obj, act\r\n" +
            "[role_definition]\r\n" +
            "g = _, _\r\n" +
            "[policy_effect]\r\n" +
            "e = some(where (p.eft == allow))\r\n" +
            "[matchers]\r\n" +
            "m = g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act\r\n"
        );

        Enforcer e = new Enforcer(model);

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/tbl";
        String username = "YourUsername";
        String password = "YourPassword";
        
        return this;
    }
}
