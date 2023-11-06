package com.self.test.bean;

import com.self.spring.annotation.Scope;
import com.self.spring.annotation.Service;

/**
 * @author YL
 * @date 2023/11/02
 **/
@Service("userService1")
@Scope("singleton")
public class UserService1 {
}
