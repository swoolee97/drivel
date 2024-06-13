package com.ebiz.drivel.domain.festival.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FestivalDetailApiResponse {
    private Response response;


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Header header;
        private Body body;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String contentid;
        private String contenttypeid;
        private String title;
        private String createdtime;
        private String modifiedtime;
        private String tel;
        private String telname;
        private String homepage;
        private String booktour;
        private String firstimage;
        private String firstimage2;
        private String cpyrhtDivCd;
        private String areacode;
        private String sigungucode;
        private String cat1;
        private String cat2;
        private String cat3;
        private String addr1;
        private String addr2;
        private String zipcode;
        private String mapx;
        private String mapy;
        private String mlevel;
        private String overview;
    }

}
