package com.timain.web.sys.common;

import com.timain.web.sys.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 活动用户
 * @author yyf
 * @version 1.0
 * @date 2020/1/14 20:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {

    private User user;

    private List<String> roles;

    private List<String> permissions;
}
