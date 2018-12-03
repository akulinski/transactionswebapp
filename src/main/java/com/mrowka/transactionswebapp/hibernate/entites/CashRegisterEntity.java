package com.mrowka.transactionswebapp.hibernate.entites;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Set;

@Entity
@Table(name = "cash_register")
@Transactional
public class CashRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cash_register")
    @Expose
    private int id;

    @Column(name = "balance")
    @Expose
    private float balance;

    @Column(name = "cashNumber")
    @Expose
    private int cashNumber;

    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cashRegisterEntity")
    private Set<TransactionEntity> transactionEntities;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    @Expose
    private StoreEntity storeEntity;


    public void addMoney(float money){
        if(money < 0){
            throw new IllegalStateException("Negative value not allowed");
        }else{
            this.balance += money;
        }
    }

    public void withdrawMoney(float money){

        if(money < 0){
            throw new IllegalStateException("Negative value not allowed");
        }

        else{
            this.balance -= money;
        }

    }

    public CashRegisterEntity(float balance, int cashNumber, Set<TransactionEntity> transactionEntities, StoreEntity storeEntity) {
        this.balance = balance;
        this.cashNumber = cashNumber;
        this.transactionEntities = transactionEntities;
        this.storeEntity = storeEntity;
    }

    public CashRegisterEntity(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getCashNumber() {
        return cashNumber;
    }

    public void setCashNumber(int cashNumber) {
        this.cashNumber = cashNumber;
    }

    public Set<TransactionEntity> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(Set<TransactionEntity> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }

    public StoreEntity getStoreEntity() {
        return storeEntity;
    }

    public void setStoreEntity(StoreEntity storeEntity) {
        this.storeEntity = storeEntity;
    }
}
