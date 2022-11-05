package com.jeanbarcellos.demo.domain.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jeanbarcellos.core.domain.EntityBase;
import com.jeanbarcellos.core.util.CollectionUtils;
import com.jeanbarcellos.demo.domain.enums.UserStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "\"user\"", uniqueConstraints = { @UniqueConstraint(name = "user_email_uk", columnNames = { "email" }), })
public class User extends EntityBase implements UserDetails {

    // #region Properties

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.INACTIVE;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER) // fix: EAGER por causa do Hibernate
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", columnDefinition = "uuid"), foreignKey = @ForeignKey(name = "user_role_user_id_fk"), inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "uuid"), inverseForeignKey = @ForeignKey(name = "user_role_role_id_fk"))
    private Set<Role> roles = new HashSet<>();

    // #endregion

    // #region Contructors

    protected User() {
    }

    public User(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = UserStatus.INACTIVE;
    }

    public User(String name, String email, String password, UserStatus status) {
        this(name, email, password);
        this.status = status;
    }

    public User(UUID id, String name, String email, String password, UserStatus status) {
        this(name, email, password, status);
        this.id = id;
    }

    // #endregion

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // #region Setters

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    // #endregion

    // #region Handler Roles

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(this.roles);
    }

    public Set<Role> getReachableRoles() {
        var reachableRoles = new HashSet<Role>();

        for (Role role : this.roles) {
            reachableRoles.add(role);
            reachableRoles.addAll(role.getReachableRoles());
        }

        return reachableRoles;
    }

    public List<String> getRoleNames() {
        return CollectionUtils.mapToList(this.roles, Role::getName);
    }

    public List<String> getReachableRoleNames() {
        return CollectionUtils.mapToList(this.getReachableRoles(), Role::getName);
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    public User removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public User clearRoles() {
        this.roles.clear();
        return this;
    }

    public boolean isGranted(Role role) {
        return this.getReachableRoles().contains(role);
    }

    public boolean isGranted(String roleName) {
        return this.getReachableRoles().contains(new Role(roleName, ""));
    }

    // #endregion

    // #region Handler Status

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public User activate() {
        this.status = UserStatus.ACTIVE;
        return this;
    }

    public User inactivate() {
        this.status = UserStatus.INACTIVE;
        return this;
    }

    // #endregion

    // #region implements UserDetails

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CollectionUtils.mapToSet(this.getReachableRoleNames(),
                roleName -> new SimpleGrantedAuthority(Role.NAME_PREFIX + roleName));

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }

    // #endregion

    // #region Equals and ToString

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + name + "]";
    }

    // #endregion
}
