package com.ebiz.drivel.domain.festival.service;

import com.ebiz.drivel.domain.festival.dto.FestivalDetailResponse;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoApiResponse;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoApiResponse.Item;
import com.ebiz.drivel.domain.festival.entity.Festival;
import com.ebiz.drivel.domain.festival.exception.FestivalApiException;
import com.ebiz.drivel.domain.festival.repository.FestivalRepository;
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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FestivalApiService {

    @Value("${festival.key}")
    private String key;
    private static final String FESTIVAL_INFO_API_URL =
            "http://apis.data.go.kr/B551011/KorService1/searchFestival1?eventStartDate=%s&eventEndDate=%s&areaCode=&sigunguCode=&ServiceKey=%s&listYN=Y&MobileOS=ETC&MobileApp=drivel&arrange=A&numOfRows=3000&pageNo=1&_type=json";
    private static final String FESTIVAL_DETAIL_API_URL = "http://apis.data.go.kr/B551011/KorService1/detailCommon1?ServiceKey=%s&contentTypeId=15&contentId=%s&MobileOS=ETC&MobileApp=drivel&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&_type=json";
    private static final String UPDATE_FESTIVAL_DATA_SUCCESS_MESSAGE = "페스티벌 데이터 %d개 업데이트 완료";
    private static final String UPDATE_FESTIVAL_DATA_FAIL_MESSAGE = "페스티벌 데이터 업데이트 실패";

    private final FestivalRepository festivalRepository;

    @Transactional
    @Scheduled(cron = "0 0 4 * * *") // 매일 새벽 4시에 작동
    public void updateFestivalData() throws IOException {
        initializeFestivalData();
        FestivalInfoApiResponse festivalInfoApiResponse = fetchFestivalData(getFestivalInfoFetchUrl(),
                FestivalInfoApiResponse.class);
        List<Item> items = festivalInfoApiResponse.getResponse().getBody().getItems().getItem();
        List<Festival> festivals = fetchFestivalDetails(items);
        festivalRepository.saveAll(festivals);
        log.info(String.format(UPDATE_FESTIVAL_DATA_SUCCESS_MESSAGE,
                festivalInfoApiResponse.getResponse().getBody().getNumOfRows()));
    }

    private List<Festival> fetchFestivalDetails(List<Item> items) {
        List<Festival> festivals = new ArrayList<>();
        items.forEach(item -> {
            try {
                FestivalDetailResponse response = fetchFestivalData(
                        getFestivalDetailFetchUrl(item), FestivalDetailResponse.class);
                festivals.add(Festival.from(item,
                        response.getResponse().getBody().getItems().getItem().get(0).getOverview()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return festivals;
    }

    private <T> T fetchFestivalData(String fetchUrl, Class<T> responseType)
            throws IOException {
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
                T responseObject = objectMapper.readValue(jsonResponse, responseType);
                return responseObject;
            }
        } catch (JsonProcessingException e) {
            throw new FestivalApiException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

        throw new FestivalApiException(UPDATE_FESTIVAL_DATA_FAIL_MESSAGE);
    }

    private void initializeFestivalData() {
        festivalRepository.deleteAll();
    }

    private String getFestivalInfoFetchUrl() {
        String stringDate = getStringDate();
        return String.format(FESTIVAL_INFO_API_URL, stringDate, stringDate, key);
    }

    private String getFestivalDetailFetchUrl(Item item) {
        return String.format(FESTIVAL_DETAIL_API_URL, key, item.getContentid());
    }

    private String getStringDate() {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(date);
    }

}
