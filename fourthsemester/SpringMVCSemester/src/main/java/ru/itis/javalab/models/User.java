package ru.itis.javalab.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jl_user")
public class User implements Serializable {

    private static final long serialVersionUID = -1753311472021512433L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String hashPassword;
    private String confirmCode;

    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        CONFIRMED, NOT_CONFIRMED, BANNED
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    public boolean isActive() {
        return isConfirmed();
    }

    public boolean isConfirmed() {
        return this.state == State.CONFIRMED;
    }

    public boolean isAdmin() {
        return this.role == Role.ROLE_ADMIN;
    }
}
