package ee.ivkhkdev.nptv23javafx.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Session {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private AppUser currentUser;
    private LocalDateTime startSession;
    private int duration=20; //минут
    private boolean expired;
    private enum ROLES {USER, MANAGER, ADMINISTRATOR}


    public Session() {
    }
    public Session(Long id) {
        this.id = id;
        this.startSession = null;
        this.duration = 20;
        this.expired = true;
    }
    public String getRoleString(String role) {
        String roleString;
        switch (role) {
            case "USER": roleString = ROLES.USER.toString();
            case "MANAGER": roleString = ROLES.MANAGER.toString();
            case "ADMINISTRATOR": roleString = ROLES.ADMINISTRATOR.toString();
            default: roleString = "";
        }
        return roleString;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartSession() {
        return startSession;
    }

    public void setStartSession(LocalDateTime startSession) {
        this.startSession = startSession;
    }

    public boolean isExpired() {
        if(startSession == null || startSession.plusMinutes(duration).isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return duration == session.duration && Objects.equals(id, session.id) && Objects.equals(currentUser, session.currentUser) && Objects.equals(startSession, session.startSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentUser, startSession, duration);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", currentUser=" + currentUser +
                ", startSession=" + startSession +
                ", duration=" + duration +
                '}';
    }
}
