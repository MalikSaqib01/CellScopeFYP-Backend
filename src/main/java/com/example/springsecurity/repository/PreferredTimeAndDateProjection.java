package com.example.springsecurity.repository;

import org.springframework.beans.factory.annotation.Value;

public interface PreferredTimeAndDateProjection {
        @Value("#{target.preferredTime}")
        String getPreferredTime();

        @Value("#{target.preferredDate}")
        String getPreferredDate();


}
