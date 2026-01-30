package com.example.consultationservice.client;

import com.example.consultationservice.dto.RendezVousDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rendezvous-service", url = "${rendezvous.service.url}")
public interface RendezVousFeignClient {
    @GetMapping("/internal/api/v1/rendezvous/{id}")
    RendezVousDto getRendezVousById(@PathVariable Long id);
}

