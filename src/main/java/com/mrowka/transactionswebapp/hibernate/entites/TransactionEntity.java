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

    @Column(name = "kart_payback")
    @Expose
    private float kartyPaybackField;

    @Column(name = "zwroty")
    @Expose
    private float zwrotyField;

    @Column(name = "niefiskalne")
    @Expose
    private float niefiskalneField;

    @Column(name = "fiskalne")
    @Expose
    private float fiskalneField;

    @Column(name = "fiskalnePlatnoscKarta")
    @Expose
    private float fiskalnePlatnoscKartaField;


    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    @Expose
    private CashRegisterEntity cashRegisterEntity;

    //additional fields

    public TransactionEntity(UserEntity userEntity, boolean isApproved, Date dateOfCreation, Date dateOfModification, int modifierName) {
        this.userEntity = userEntity;
        this.isApproved = isApproved;
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
        this.modifierId = modifierName;
    }

    public TransactionEntity(UserEntity userEntity, boolean isApproved, Date dateOfCreation, Date dateOfModification, int modifierId, float kartyPaybackField, float zwrotyField, float niefiskalneField, float fiskalneField, float fiskalnePlatnoscKartaField) {
        this.userEntity = userEntity;
        this.isApproved = isApproved;
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
        this.modifierId = modifierId;
        this.kartyPaybackField = kartyPaybackField;
        this.zwrotyField = zwrotyField;
        this.niefiskalneField = niefiskalneField;
        this.fiskalneField = fiskalneField;
        this.fiskalnePlatnoscKartaField = fiskalnePlatnoscKartaField;
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


    public float getKartyPaybackField() {
        return kartyPaybackField;
    }

    public void setKartyPaybackField(float kartyPaybackField) {
        this.kartyPaybackField = kartyPaybackField;
    }

    public float getZwrotyField() {
        return zwrotyField;
    }

    public void setZwrotyField(float zwrotyField) {
        this.zwrotyField = zwrotyField;
    }

    public float getNiefiskalneField() {
        return niefiskalneField;
    }

    public void setNiefiskalneField(float niefiskalneField) {
        this.niefiskalneField = niefiskalneField;
    }

    public float getFiskalneField() {
        return fiskalneField;
    }

    public void setFiskalneField(float fiskalneField) {
        this.fiskalneField = fiskalneField;
    }

    public float getFiskalnePlatnoscKartaField() {
        return fiskalnePlatnoscKartaField;
    }

    public void setFiskalnePlatnoscKartaField(float fiskalnePlatnoscKartaField) {
        this.fiskalnePlatnoscKartaField = fiskalnePlatnoscKartaField;
    }

    public CashRegisterEntity getCashRegisterEntity() {
        return cashRegisterEntity;
    }

    public void setCashRegisterEntity(CashRegisterEntity cashRegisterEntity) {
        this.cashRegisterEntity = cashRegisterEntity;
    }
}
