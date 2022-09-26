package com.youwu.shopowner.ui.set_up;

/**
 * @author: Administrator
 * @date: 2022/9/26
 */
public class SettingBean {

    /**
     * id : 19
     * start : 00:01:00
     * end : 23:00:00
     * is_order : 2
     * delivery_method : 1,2,4
     * is_link : 1
     * feie_print : {"id":2,"user":"771882969@qq.com","ukey":"RNYEaFHfKJ2Aa9yA","sn":"523522489","key":"","store_id":19,"device_type":1}
     */

    private int id;
    private String start;
    private String end;
    private int is_order;
    private String delivery_method;
    private int is_link;
    private FeiePrintBean feie_print;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getIs_order() {
        return is_order;
    }

    public void setIs_order(int is_order) {
        this.is_order = is_order;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public int getIs_link() {
        return is_link;
    }

    public void setIs_link(int is_link) {
        this.is_link = is_link;
    }

    public FeiePrintBean getFeie_print() {
        return feie_print;
    }

    public void setFeie_print(FeiePrintBean feie_print) {
        this.feie_print = feie_print;
    }

    public static class FeiePrintBean {
        /**
         * id : 2
         * user : 771882969@qq.com
         * ukey : RNYEaFHfKJ2Aa9yA
         * sn : 523522489
         * key :
         * store_id : 19
         * device_type : 1
         */

        private int id;
        private String user;
        private String ukey;
        private String sn;
        private String key;
        private int store_id;
        private int device_type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getUkey() {
            return ukey;
        }

        public void setUkey(String ukey) {
            this.ukey = ukey;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getDevice_type() {
            return device_type;
        }

        public void setDevice_type(int device_type) {
            this.device_type = device_type;
        }
    }
}
