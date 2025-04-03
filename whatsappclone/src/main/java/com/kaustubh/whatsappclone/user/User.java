package com.kaustubh.whatsappclone.user;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;


import com.kaustubh.whatsappclone.chat.Chat;
import com.kaustubh.whatsappclone.common.BaseAuditingEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

@NamedQuery(
    name = UserConstants.FIND_USER_BY_EMAIL,
    query = "SELECT u FROM User u WHERE u.email = :email"
)

@NamedQuery(
    name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF,
    query = "SELECT u FROM User u WHERE u.id != :publicId"
)


@NamedQuery(
    name = UserConstants.FIND_USER_BY_PUBLIC_ID,
    query = "SELECT u FROM User u WHERE u.id = :publicId"
)


public class User extends BaseAuditingEntity{

    private static final int LAST_ACTIVE_INTERVAL = 5;
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;
    

    @ManyToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;

    @ManyToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    @Transient
    public boolean isUserOnline(){
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }

}
