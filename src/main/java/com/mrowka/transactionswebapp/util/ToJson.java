package com.mrowka.transactionswebapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mrowka.transactionswebapp.adapters.StoreAdapter;
import com.mrowka.transactionswebapp.adapters.TransactionAdapter;
import com.mrowka.transactionswebapp.adapters.UserAdapter;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;

import java.util.ArrayList;

public class ToJson {
    public String storeToJson(ArrayList<StoreEntity> storeEntities) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(StoreEntity.class, new StoreAdapter()).create();
        return gson.toJson(storeEntities);
    }
    public String userToJson(ArrayList<UserEntity> userEntities){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(UserEntity.class, new UserAdapter()).create();
        return  gson.toJson(userEntities);
    }

    public String transactionToJson(ArrayList<TransactionEntity> transactionEntities){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(TransactionEntity.class, new TransactionAdapter()).create();
        return  gson.toJson(transactionEntities);
    }
}
