package com.example.shdemo.domain;

public enum Sex {

    MALE("M"),
    FEMALE("F");

    private String code;

    Sex(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Sex fromCode(String code){
        for(Sex sex : Sex.values()){
            if(sex.getCode().equals(code))
                return sex;
        }
        throw new UnsupportedOperationException();
    }
}
