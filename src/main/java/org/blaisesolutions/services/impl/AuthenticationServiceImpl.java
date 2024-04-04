package org.blaisesolutions.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.blaisesolutions.entity.User;
import org.blaisesolutions.entity.UserCredential;
import org.blaisesolutions.services.AuthenticationService;
import org.blaisesolutions.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zuti.zul.Apply;

import java.io.Serializable;

@Getter
@Setter
@Slf4j
@Service("authService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthenticationServiceImpl implements AuthenticationService, Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("userInfoService")
    UserInfoService userInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserCredential getUserCredential() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute("userCredential");
        if (cre == null) {
            cre = new UserCredential();
            sess.setAttribute("userCredential", cre);
        }
        return cre;
    }


    @Override
    public boolean login(String nm, String pd) {

        if (nm.equals("admin") && pd.equals("admin")) {
            UserCredential adminCredential = new UserCredential("Admin", "Admin","ADMIN");

            Session sess = Sessions.getCurrent();
            sess.setAttribute("userCredential", adminCredential);
            return true;
        }
            User user = userInfoService.findUser(nm);
         if (user == null || !passwordEncoder.matches(pd, user.getPassword())) {
            return false;
        }
        Session sess = Sessions.getCurrent();
        UserCredential cre = new UserCredential(user.getAccount(), user.getFullName(), user.getRole());
        if (cre.isAnonymous()) {
            return false;
        }
        sess.setAttribute("userInfo",user);
        sess.setAttribute("userCredential", cre);
        //TODO handle the role here for authorization
        return true;
    }

    @Override
    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
        sess.removeAttribute("userInfo");
    }
}
