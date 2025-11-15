package com.kuza.kuzasokoni.common.authentication.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final WebClient webClient;

    @Value("${app.sms.url}")
    private String smsUrl;

    @Value("${app.sms.clientId}")
    private String clientId;

    @Value("${app.sms.clientSecret}")
    private String clientSecret;

    @Value("${app.sms.source}")
    private String source;

    @Value("${app.sms.enabled:true}")
    private boolean smsEnabled;

    public Mono<String> sendSms(String phone, String message) {
        if (!smsEnabled) {
            log.warn("SMS disabled. Would send to {}: {}", phone, message);
            return Mono.just("SMS disabled (dev mode)");
        }

        String normalizedPhone = normalizePhone(phone);
        String reference = "OTP-" + System.currentTimeMillis();

        var payload = Map.of(
                "auth", Map.of(
                        "clientId", clientId,
                        "clientSecret", clientSecret
                ),
                "messages", List.of(
                        Map.of(
                                "text", message,
                                "msisdn", normalizedPhone,
                                "source", source,
                                "reference", reference
                        )
                )
        );

        log.debug("Payload being sent: {}", payload);

        return webClient.post()
                .uri(smsUrl) // https://bulksms.fasthub.co.tz/api/sms/send
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .doOnSuccess(resp -> log.info("SMS sent successfully to {} | Ref: {}", normalizedPhone, reference))
                .doOnError(err -> log.error("SMS failed to {}: {}", normalizedPhone, err.getMessage()))
                .thenReturn("SMS sent");
    }

    private String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) return phone;
        phone = phone.trim();
        if (phone.startsWith("0")) return "255" + phone.substring(1);
        if (phone.startsWith("+")) return phone.substring(1);
        return phone;
    }

}
