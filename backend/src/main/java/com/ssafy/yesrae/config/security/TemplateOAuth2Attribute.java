package com.ssafy.yesrae.config.security;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * TODO: 예시 코드이므로 삭제 예정
 */

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TemplateOAuth2Attribute {

    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String picture;

    static TemplateOAuth2Attribute of(String provider, String attributeKey,
        Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> ofGoogle(attributeKey, attributes);
            case "kakao" -> ofKakao("email", attributes);
            case "naver" -> ofNaver("id", attributes);
            default -> throw new RuntimeException();
        };
    }

    private static TemplateOAuth2Attribute ofGoogle(String attributeKey,
        Map<String, Object> attributes) {
        return TemplateOAuth2Attribute.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .attributeKey(attributeKey)
            .build();
    }

    private static TemplateOAuth2Attribute ofKakao(String attributeKey,
        Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return TemplateOAuth2Attribute.builder()
            .name((String) kakaoProfile.get("nickname"))
            .email((String) kakaoAccount.get("email"))
            .picture((String) kakaoProfile.get("profile_image_url"))
            .attributes(kakaoAccount)
            .attributeKey(attributeKey)
            .build();
    }

    private static TemplateOAuth2Attribute ofNaver(String attributeKey,
        Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return TemplateOAuth2Attribute.builder()
            .name((String) response.get("name"))
            .email((String) response.get("email"))
            .picture((String) response.get("profile_image"))
            .attributes(response)
            .attributeKey(attributeKey)
            .build();
    }

    Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("email", email);
        map.put("picture", picture);

        return map;
    }
}
