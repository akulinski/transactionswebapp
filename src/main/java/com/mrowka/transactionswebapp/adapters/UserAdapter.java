package com.mrowka.transactionswebapp.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;

import java.lang.reflect.Type;

public class UserAdapter implements JsonSerializer<UserEntity> {
    @Override
    public JsonElement serialize(UserEntity userEntity, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", userEntity.getLogin());
        jsonObject.addProperty("id", userEntity.getId());
        jsonObject.addProperty("email", userEntity.getEmail());
        return jsonObject;
    }
}
