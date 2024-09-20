package guat.tsdrs.service.impl;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.mapper.UserMapper;
import guat.tsdrs.pojo.User;
import guat.tsdrs.pojo.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByName(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException(null);
        }
        return new LoginUser(user);
    }
}
