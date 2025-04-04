package ee.ivkhkdev.nptv23javafx.service;

import ee.ivkhkdev.nptv23javafx.Nptv23JavaFxApplication;
import ee.ivkhkdev.nptv23javafx.interfaces.AppUserService;
import ee.ivkhkdev.nptv23javafx.model.entity.AppUser;
import ee.ivkhkdev.nptv23javafx.model.entity.Session;
import ee.ivkhkdev.nptv23javafx.model.repository.AppUserRepository;
import ee.ivkhkdev.nptv23javafx.model.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final SessionRepository sessionRepository;
    private AppUserRepository repository;
    public AppUserServiceImpl(AppUserRepository repository, SessionRepository sessionRepository) {
        this.repository = repository;
        initSuperUser();
        this.sessionRepository = sessionRepository;
    }
    @Override
    public List<AppUser> getList() {
        return repository.findAll();
    }
    @Override
    public void initSuperUser(){
        if(repository.count()>0){
            return;
        }
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setFirstname("Admin");
        admin.setLastname("SuperAdmin");
        admin.getRoles().add("ADMINISTRATOR");
        admin.getRoles().add("MANAGER");
        admin.getRoles().add("USER");
        repository.save(admin);
    }
    @Override
    public Optional<AppUser> add(AppUser user) {
       return Optional.of(repository.save(user));
    }

    /**
     * Получает пользователя из базы по username
     * Сравнивает пароль пользователя с введенным
     * Получает пользователя из Session
     *
     * @param username
     * @param password
     * @return boolean
     */
    @Override
    public boolean authentication(String username, String password) {
        Optional<AppUser> optionalAppUser = repository.findByUsername(username);
        if(optionalAppUser.isEmpty()) {
            return false;
        }
        AppUser loginUser = optionalAppUser.get();
        if(loginUser.getPassword().equals(password)) {
            Optional<Session> optionalSession = sessionRepository.findById(1L);
            if(optionalSession.isEmpty()) {
                Session newSession = new Session();
                newSession.setStartSession(LocalDateTime.now());
                newSession.setCurrentUser(loginUser);
                sessionRepository.save(newSession);
                return true;
            }else{
                Session currentSession = optionalSession.get();
                currentSession.setStartSession(LocalDateTime.now());
                currentSession.setCurrentUser(loginUser);
                sessionRepository.save(currentSession);
                return true;
            }
        }
        return false;
    }
    public Optional<Session> getSession(){
        return sessionRepository.findById(1L);
    }
}
