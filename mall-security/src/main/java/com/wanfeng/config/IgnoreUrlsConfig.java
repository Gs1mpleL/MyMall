package com.wanfeng.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 写完这个配置文件，就可以在yaml文件中定义其中的参数了
 * @author wanfeng
 * @created 2022/3/28 16:46
 * @package com.wanfeng.config
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "security.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
