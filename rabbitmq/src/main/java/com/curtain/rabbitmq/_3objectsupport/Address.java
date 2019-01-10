package com.curtain.rabbitmq._3objectsupport;

import java.io.Serializable;

public class Address implements Serializable {
        private String city;
        private String province;
        private String country;

        public Address(String city, String province, String country) {
            this.city = city;
            this.province = province;
            this.country = country;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    ", province='" + province + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }