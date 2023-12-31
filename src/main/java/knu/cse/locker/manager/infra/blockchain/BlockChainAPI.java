package knu.cse.locker.manager.infra.blockchain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * BlockChainAPI.java
 *
 * @note 블록체인 API를 사용하기 위한 클래스
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class BlockChainAPI {

    @Value("${spring.chain.api-token}")
    private String API_TOKEN;

    public String createWallet(String wallet_name) {
        final String URI = "https://testnet-api.blocksdk.com/v3/matic/address?api_token=" + API_TOKEN;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", wallet_name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                URI,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("오류 " + response);
        }

        Map payload = (Map) Objects.requireNonNull(response.getBody()).get("payload");

        return (String) payload.get("address");

    }

    public Map readWallet(String address) {
        final int LIMIT = 100;
        final String URI = "https://testnet-api.blocksdk.com/v3/matic/address?api_token="
                + API_TOKEN + "&offset=0&limit=" + LIMIT + "&order_direction=desc";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException();
        }

        Map payload = (Map) Objects.requireNonNull(response.getBody()).get("payload");
        List<Map> dataList = (List<Map>) payload.get("data");

        for (Map data : dataList) {
            String data_address = (String) data.get("address");
            if (data_address.equals(address)) {
                return data;
            }
        }

        return null;
    }
}
