package com.tongchuang.model;

import lombok.Data;
import java.util.ArrayList;

@Data
public class ProvinceImgUrlsModel {
    private int provin_id;
    private int slideImgNum;
    private ArrayList<String> slideImgUrls;
    private int normalImgNum;
    private ArrayList<String> normalImgUrls;
}
