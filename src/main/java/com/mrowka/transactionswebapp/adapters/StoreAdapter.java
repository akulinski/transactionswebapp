package com.mrowka.transactionswebapp.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;

import java.lang.reflect.Type;

public class StoreAdapter implements JsonSerializer<StoreEntity> {
    @Override
    public JsonElement serialize(StoreEntity storeEntity, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("storeName", storeEntity.getStoreName());
        jsonObject.addProperty("id", storeEntity.getId());
        return jsonObject;
    }
}
