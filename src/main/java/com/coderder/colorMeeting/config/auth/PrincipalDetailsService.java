package com.coderder.colorMeeting.config.auth;

import com.coderder.colorMeeting.model.UserTest;
import com.coderder.colorMeeting.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserTestRepository userTestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        UserTest userTest = userTestRepository.findByUsername(username);

        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(userTest);
    }
}
