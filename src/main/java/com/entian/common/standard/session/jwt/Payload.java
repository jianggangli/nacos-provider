package com.entian.common.standard.session.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

/**
 * 负载对象
 *
 * @author jianggangli
 */
@Getter
@Setter
@ToString
public class Payload implements Serializable {

    /**
     * 过期时间
     */
    private Instant expireTime;

    /**
     * 负载内容
     */
    private String content;

}
