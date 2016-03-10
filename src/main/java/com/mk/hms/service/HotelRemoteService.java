package com.mk.hms.service;

import com.mk.hms.utils.ContentUtils;
import com.mk.hms.utils.HmsFileUtils;
import com.mk.hms.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thinkpad on 2015/10/17.
 */
@Service
public class HotelRemoteService {
    private static Logger logger = LoggerFactory.getLogger(HotelRemoteService.class);

    private final static String UPDATE_MIKE_PRICE_CACHE = "/hotel/updatemikepricecache";

    private final static String HOTEL_INIT = "/hotel/init";

    private final static String token = "1qaz2wsx";

    public void hotelInit(String cityId, String hotelId) throws IOException {
        Map<String, String> params=new HashMap<String, String>();
        params.put("token", token);
        params.put("cityid", cityId);
        params.put("hotelid", hotelId);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
        }
        String otsUrl = HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_UPDATE_ADDRESS);
        HttpClientUtils.post(otsUrl + HOTEL_INIT, params);
    }
    public void updatemikeprices(String hotelId) throws IOException {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
        }
        HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_UPDATE_ADDRESS);
        Map<String, String> params=new HashMap<String, String>();
        params.put("token", token);
        params.put("hotelid", hotelId);
        String otsUrl = HmsFileUtils.getSysContentItem(ContentUtils.CHECK_HOTEL_UPDATE_ADDRESS);
        HttpClientUtils.post(otsUrl + "/hotel/updatemikeprices", params);
    }

}
