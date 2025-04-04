package ee.ivkhkdev.nptv23javafx.interfaces;

import ee.ivkhkdev.nptv23javafx.model.entity.AppUser;
import ee.ivkhkdev.nptv23javafx.model.entity.Session;

import java.util.Optional;

public interface AppUserService extends AppService<AppUser> {
    void initSuperUser();
    boolean authentication(String username, String password);
    Optional<Session> getSession();
}
