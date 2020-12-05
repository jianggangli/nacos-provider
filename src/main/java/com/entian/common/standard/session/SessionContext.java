package com.entian.common.standard.session;

/**
 * @author jianggangli
 * @version 1.0 2020/12/4 13:57
 * 功能:
 */

 public interface SessionContext {
     String getToken();

     void setToken(String token) ;

     String getUserId() ;

     void setUserId(String userId) ;

     String getUserName() ;

     void setUserName(String userName) ;

     String getRoleId() ;

     void setRoleId(String roleId) ;

     String getRoleName() ;

     void setRoleName(String roleName) ;
}
