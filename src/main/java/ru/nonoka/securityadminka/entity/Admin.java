package ru.nonoka.securityadminka.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "group_admins", name = "admins")
public class Admin {

    @Id
    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;
}
