package com.mrowka.transactionswebapp.hibernate.entites;

import javax.persistence.*;

@Entity
@Table(name = "privilege")
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_privilege")
    private int id;

    @Column(name = "type")
    private int type;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private UserEntity userEntity;

    public PrivilegeEntity(int type, UserEntity userEntity) {
        this.type = type;
        this.userEntity = userEntity;
    }

    public PrivilegeEntity(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
