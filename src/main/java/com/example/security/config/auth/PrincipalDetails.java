package com.example.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.security.model.Users;




//시큐리티 /login 주소요청을  낚아채서 로그인진행
//로그인을 진행이 완료되면 시큐리티 session을 만듬(security handler)
//오브젝트타입 => authentication 타입객체
//authentication 안에 user정보가 잇어야함
//user오브젝트타입 => userdetails 타입객체

//Security Session => Authentication => UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails {

    private Users users;

    public PrincipalDetails(Users users) {
        this.users = users;
    }


    //해당유저의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return users.getRole();
            }
            
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }
    
}
