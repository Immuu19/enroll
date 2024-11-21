package com.educationportal.enroll.service.impl;

import com.educationportal.enroll.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.web.servlet.function.ServerRequest;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private RestTemplate restTemplate;

    // URLs for the other services
    private static final String USER_SERVICE_URL = "http://localhost:3000/user";
    private static final String COURSES_SERVICE_URL = "http://localhost:8080/api/courses";

    public boolean isUserValid(String username) {
        try {
            String url = USER_SERVICE_URL + "/" + username;

            HttpHeaders headers = new HttpHeaders();
            String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwicm9sZSI6InN0dWRlbnQiLCJpYXQiOjE3MzIxNTcxNTIsImV4cCI6MTczMjE2MDc1Mn0.M1xlNmuB1mS_vZkvE8wy8e233c-XMSgCdHRanoRjBvg";
            headers.set("Authorization", "Bearer " + jwtToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            System.out.println(url);
            //ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
            //ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Headers: " + response.getHeaders());
            System.out.println("Response Body: " + response.getBody());


            System.out.println(response);
            System.out.println(response.getBody());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("e");

            // Handle failure, e.g., logging
            return false;
        }
    }

    public boolean isCourseValid(String courseId) {
        try {
            String url = COURSES_SERVICE_URL ;
            System.out.println("BODYY" + url);
            //ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Headers: " + response.getHeaders());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("\"id\":"+courseId);
            System.out.println(response.getBody().contains("id:"+courseId));
            return response.getBody().contains("\"id\":"+courseId);
        } catch (Exception e) {
            // Handle failure, e.g., logging
            System.out.println(e);
            return false;
        }
    }
}
