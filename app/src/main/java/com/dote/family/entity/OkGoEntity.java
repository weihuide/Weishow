package com.dote.family.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class OkGoEntity {

    public boolean error;
    public List<Info> results;

    public class Info {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
    }

}

