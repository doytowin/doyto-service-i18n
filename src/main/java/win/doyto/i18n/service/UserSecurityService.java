//package win.doyto.i18n.service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import javax.annotation.Resource;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import win.doyto.client.database.api.RoleRepoClient;
//import win.doyto.client.database.api.UserRepoClient;
//import win.doyto.client.database.param.Perm;
//import win.doyto.client.database.param.Role;
//import win.doyto.client.database.param.User;
//
///**
// * UserService
// *
// * @author f0rb on 2017-11-03.
// */
//@Slf4j
//@Service
//public class UserSecurityService implements UserDetailsService {
//
//    @Resource
//    UserRepoClient userRepoClient;
//
//    @Resource
//    RoleRepoClient roleRepoClient;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepoClient.getByAccount(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        List<String > roleNameList = new ArrayList<>();
//        Collection<Role> roles = userRepoClient.getRoles(user.getId()).getContent();
//
//        for (Role role : roles) {
//            roleNameList.add(role.getName());
//            Collection<Perm> perms = roleRepoClient.getPerms(role.getId()).getContent();
//            for (Perm perm : perms) {
//                authorities.add(new SimpleGrantedAuthority(perm.getName()));
//            }
//        }
//        org.springframework.security.core.userdetails.User userDetail =
//                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//        //userDetail.setRoles(roleNameList);
//
//        return userDetail;
//    }
//
//}
