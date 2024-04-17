package com.api.marvel.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public enum RoleEnum {
        CUSTOMER, AUDITOR;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<GrantedPermission> permissions;

    @Override
    public String getAuthority() {
        if (Objects.isNull(this.name)) {
            return null;
        }

        return "ROLE_" + this.name.name();
    }
}
