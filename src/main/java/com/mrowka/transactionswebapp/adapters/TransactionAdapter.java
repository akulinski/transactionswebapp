package com.mrowka.transactionswebapp.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;

import java.lang.reflect.Type;

public class TransactionAdapter implements JsonSerializer<TransactionEntity> {
    @Override
    public JsonElement serialize(TransactionEntity transactionEntity, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("isApproved", transactionEntity.isApproved());
        jsonObject.addProperty("id", transactionEntity.getId());
        jsonObject.addProperty("dateOfCreation", transactionEntity.getDateOfCreation().toString());
        jsonObject.addProperty("dateOfModification", transactionEntity.getDateOfModification().toString());
        jsonObject.addProperty("modifierId", transactionEntity.getModifierId());
        jsonObject.addProperty("userId", transactionEntity.getUserEntity().getId());
        return jsonObject;
    }
}
