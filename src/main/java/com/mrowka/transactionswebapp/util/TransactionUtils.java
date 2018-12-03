package com.mrowka.transactionswebapp.util;

import com.mrowka.transactionswebapp.hibernate.entites.CashRegisterEntity;

public class TransactionUtils {


    public static void calcTransaction(CashRegisterEntity cashRegisterEntity, Float fiskalnePlatnoscKartaKarta, Float kartyPayback, Float fiskalne, Float zwroty, Float niefiskalne){

        cashRegisterEntity.addMoney(fiskalnePlatnoscKartaKarta+kartyPayback+fiskalne+niefiskalne);

        if(zwroty>0){
            cashRegisterEntity.withdrawMoney(zwroty);
        }
    }
}
