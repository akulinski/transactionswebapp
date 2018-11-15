package com.mrowka.transactionswebapp.hibernate.entites;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_transaction")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private UserEntity userEntity;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @Column(name = "date_of_modification")
    private Date dateOfModification;

    @Column(name = "modifier_id", nullable = true)
    private int modifierId;

    //additional fields

    public TransactionEntity(UserEntity userEntity, boolean isApproved, Date dateOfCreation, Date dateOfModification, int modifierName) {
        this.userEntity = userEntity;
        this.isApproved = isApproved;
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
        this.modifierId = modifierName;
    }

    public TransactionEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(Date dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public int getModifierId() {
        return modifierId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }
}
