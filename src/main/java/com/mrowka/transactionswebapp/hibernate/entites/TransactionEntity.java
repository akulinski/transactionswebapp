package com.mrowka.transactionswebapp.hibernate.entites;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Transactional
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_transaction")
    @Expose
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @Expose
    private UserEntity userEntity;

    @Column(name = "is_approved")
    @Expose
    private boolean isApproved;

    @Column(name = "date_of_creation")
    @Expose
    private Date dateOfCreation;

    @Column(name = "date_of_modification")
    @Expose
    private Date dateOfModification;

    @Column(name = "modifier_id", nullable = true)
    @Expose
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
