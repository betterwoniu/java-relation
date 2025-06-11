package org.data.springbootgrpcserver.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class UserEntity {

    private final long id;

    private final String email;

    @JsonIgnore
    private final String password;

    @JsonCreator
    public UserEntity(@JsonProperty("id") long id, @JsonProperty("email") String email,
                             @JsonProperty("password") String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
