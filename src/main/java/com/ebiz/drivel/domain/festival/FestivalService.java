package com.ebiz.drivel.domain.festival;

import com.ebiz.drivel.domain.festival.FestivalApiResponse.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FestivalService {

    @Value("${festival.key}")
    private String key;
    private static final String FESTIVAL_API_URL =
            "http://apis.data.go.kr/B551011/KorService1/searchFestival1?eventStartDate=%s&eventEndDate=%s&areaCode=&sigunguCode=&ServiceKey=%s&listYN=Y&MobileOS=ETC&MobileApp=drivel&arrange=A&numOfRows=3000&pageNo=1&_type=json";

    private static final String UPDATE_FESTIVAL_DATA_SUCCESS_MESSAGE = "페스티벌 데이터 %d개 업데이트 완료";
    private static final String UPDATE_FESTIVAL_DATA_FAIL_MESSAGE = "페스티벌 데이터 업데이트 실패";

    private final FestivalRepository festivalRepository;

    @Transactional
    @Scheduled(cron = "0 0 4 * * *") // 매일 새벽 4시에 작동
    public void updateFestivalData() throws IOException {
        initializeFestivalData();
        FestivalApiResponse festivalApiResponse = getData(getFetchUrl());
        saveFestivalData(festivalApiResponse);
        log.info(String.format(UPDATE_FESTIVAL_DATA_SUCCESS_MESSAGE,
                festivalApiResponse.getResponse().getBody().getNumOfRows()));
    }

    private FestivalApiResponse getData(String fetchUrl) throws IOException {
        try {
            URL url = new URL(fetchUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String jsonResponse = response.toString();

                ObjectMapper objectMapper = new ObjectMapper();
                FestivalApiResponse responseObject = objectMapper.readValue(jsonResponse, FestivalApiResponse.class);
                return responseObject;
            }
        } catch (JsonProcessingException e) {
            throw new FestivalApiException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

        throw new FestivalApiException(UPDATE_FESTIVAL_DATA_FAIL_MESSAGE);
    }

    private void saveFestivalData(FestivalApiResponse festivalApiResponse) {
        List<Item> items = festivalApiResponse.getResponse().getBody().getItems().getItem();
        List<Festival> festivals = items.stream().map(Festival::from).collect(Collectors.toList());
        festivalRepository.saveAll(festivals);
    }

    private void initializeFestivalData() {
        festivalRepository.deleteAll();
    }

    private String getFetchUrl() {
        String stringDate = getStringDate();
        return String.format(FESTIVAL_API_URL, stringDate, stringDate, key);
    }

    private String getStringDate() {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(date);
    }

}
