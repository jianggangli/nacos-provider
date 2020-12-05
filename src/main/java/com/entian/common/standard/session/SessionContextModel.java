package com.entian.common.standard.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author jianggangli
 * @version 1.0 2020/12/4 13:57
 * 功能:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SessionContextModel implements SessionContext, Serializable {
    private static final long serialVersionUID = -295625748330640502L;

    private String token;
    private String userId;
    private String userName;
    private String roleId;
    private String roleName;


}
