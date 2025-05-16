package com.jlumart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jlumart.map")
@Data
public class BaiduMapProperties {
    private String ak;
    private String address;
    private String longitude;
    private String latitude;
    private String output;
}
