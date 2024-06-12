package io.quarkcloud.quarkstarter.service.admin.metric;

import io.quarkcloud.quarkadmin.template.metrics.Descriptions;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.util.Arrays;
import org.springframework.boot.SpringBootVersion;
import io.quarkcloud.quarkadmin.component.descriptions.Field;

public class SystemInfo extends Descriptions {

    // 构造方法
    public SystemInfo() {
        this.title = "系统信息";
        this.col = 12;
    }

    // 计算
    public Object calculate() {
        Field field = new Field();

        // SpringBoot版本
        String version = SpringBootVersion.getVersion();

        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        // Get the OperatingSystemMXBean
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // Get total and free memory in bytes
        long totalMemory = osBean.getTotalMemorySize();
        long freeMemory = osBean.getFreeMemorySize();
        long usedMemory = totalMemory - freeMemory;

        // Convert to MB
        long totalMemoryMB = totalMemory / (1024 * 1024);
        double usedPercent = (double) usedMemory / totalMemory * 100;

        // Get the CPU load between 0.0 and 1.0
        double cpuUsage = osBean.getCpuLoad();

        return this.result(Arrays.asList(
            field.text("应用名称").setValue("QuarkJar"),
            field.text("应用版本").setValue("1.0.0"),
            field.text("SpringBoot版本").setValue(version),
            field.text("服务器操作系统").setValue(os+" "+arch),
            field.text("内存信息").setValue(totalMemoryMB + "MB / " + String.format("%.0f", usedPercent) + "%"),
            field.text("CPU使用率").setValue(String.format("%.0f", cpuUsage * 100) + "%")
        ));
    }
}
