package me.gt86.controller;

import me.gt86.model.User;
import me.gt86.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,16}$";

    @PostMapping("/check")
    public ResponseEntity<User> getPlayerInfo(@RequestBody Map<String, Object> json) {
        String username = (String) json.get("username");
        if (username == null || username.isEmpty() || !username.matches(USERNAME_REGEX)) {
            return ResponseEntity.badRequest().body(User.builder().error("Missing player username!").build());
        }
        String url = "https://api.mojang.com/users/profiles/minecraft/" + username;
        try {
            ResponseEntity<Map> mojangResponse = restTemplate.getForEntity(url, Map.class);
            if (mojangResponse.getStatusCodeValue() != 200) {
                return ResponseEntity.ok(User.builder().error("Couldn't find any profile with name " + username).build());
            }
            Map<String, Object> mojangData = mojangResponse.getBody();
            if (mojangData == null || mojangData.containsKey("error") || mojangData.containsKey("errorMessage")) {
                return ResponseEntity.ok(User.builder().error("Couldn't find any profile with name " + username).build());
            }
            String name = (String) mojangData.get("name");
            String rawId = (String) mojangData.get("id");
            String uuid = Utils.stringToUuid(rawId);

            User userResponse = User.builder()
                .username(name)
                .rawid(rawId)
                .uuid(uuid)
                .build();
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.ok(User.builder().error("Couldn't find any profile with name " + username).build());
        }
    }

}
