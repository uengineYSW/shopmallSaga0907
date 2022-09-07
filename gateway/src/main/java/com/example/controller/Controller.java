// package com.example.controller;

// import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
// import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.security.Principal;

// @RestController
// public class Controller {

//     @GetMapping("/")
//     public String index(Principal principal) {
//         StringBuffer sb = new StringBuffer("<html>");
//         sb.append("<center>");
//         sb.append("<p style='font-size: 20px; font-weight: bold;'>");
//         sb.append("Welcome to our 12st ShopMall");
//         sb.append("</p>");
//         sb.append("<img src=\'https://user-images.githubusercontent.com/35618409/155874313-8b57affe-82e5-49f6-9858-4400bf09e508.png\'/><br/>");
//         sb.append("<ins>[Add to Cart] [Buy now]</ins>");
//         sb.append("</center>");

//         return sb.toString();
//     }
// }
