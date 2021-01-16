package com.bvcm.qlcongviec;

public class CongViec {
    private int IdCV;
    private String TenCV;
    private String TTCV;

    public CongViec(int idCV, String tenCV, String TTCV) {
        IdCV = idCV;
        TenCV = tenCV;
        this.TTCV = TTCV;
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String tenCV) {
        TenCV = tenCV;
    }

    public String getTTCV() {
        return TTCV;
    }

    public void setTTCV(String TTCV) {
        this.TTCV = TTCV;
    }
}

