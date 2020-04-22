package com.presentation.util.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.presentation.entities.User;

import java.io.IOException;

public class UserDeserialize extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);
        User user = new User();
        user.setFirstName(jsonNode.get("firstName").asText());
        user.setLastName(jsonNode.get("lastName").asText());
        user.setEmail(jsonNode.get("email").asText());
        user.setStudy(jsonNode.get("study").asText());
        user.setYear(jsonNode.get("year").asText());
        user.setPassword(jsonNode.get("password").asText());
        user.setRole("user");
        return user;
    }
}

