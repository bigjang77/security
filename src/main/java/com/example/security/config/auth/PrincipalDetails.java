package com.example.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.security.model.Users;

import lombok.Data;




//시큐리티 /login 주소요청을  낚아채서 로그인진행
//로그인을 진행이 완료되면 시큐리티 session을 만듬(security handler)
//오브젝트타입 => authentication 타입객체
//authentication 안에 user정보가 잇어야함
//user오브젝트타입 => userdetails 타입객체

//Security Session => Authentication => UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Users users;
    private Map<String, Object> attributes;

    //일반로그인 생성자
    public PrincipalDetails(Users users) {
        this.users = users;
    }
    //Oauth 로그인 생성자
    public PrincipalDetails(Users users,Map<String, Object> attrbutes) {
        this.users = users;
        this.attributes = attrbutes;
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


    @Override
    public Map<String, Object> getAttributes() {
        
        return attributes;
    }


    @Override
    public String getName() {
        
        return null;
    }
    
}
