package com.mrowka.transactionswebapp.hibernate.entites;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "dateOfJoining")
    private Date dataOfJoining;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private StoreEntity storeEntity;

    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
    private Set<PrivilegeEntity> privilegeEntities;

    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
    private Set<TransactionEntity> transactionEntities;

    public UserEntity() {
    }

    public UserEntity(String login, String password, String email, Date dataOfJoining, StoreEntity storeEntity) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.dataOfJoining = dataOfJoining;
        this.storeEntity = storeEntity;
    }

    public UserEntity(String login, String password, String email, Date dataOfJoining) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.dataOfJoining = dataOfJoining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataOfJoining() {
        return dataOfJoining;
    }

    public void setDataOfJoining(Date dataOfJoining) {
        this.dataOfJoining = dataOfJoining;
    }

    public StoreEntity getStoreEntity() {
        return storeEntity;
    }

    public void setStoreEntity(StoreEntity storeEntity) {
        this.storeEntity = storeEntity;
    }

    public Set<PrivilegeEntity> getPrivilegeEntity() {
        return privilegeEntities;
    }

    public void setPrivilegeEntity(Set<PrivilegeEntity> privilegeEntity) {
        this.privilegeEntities = privilegeEntity;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dataOfJoining=" + dataOfJoining +
                ", storeEntity=" + storeEntity +
                '}';
    }


}
