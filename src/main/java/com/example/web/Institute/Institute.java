package com.example.web.Institute;


import com.example.web.major.Major;

import java.util.ArrayList;

/*
学院的实体类
 */
public class Institute {
    private Long instituteId;
    private String instituteName;
    private Integer numberOfMajor;

    private ArrayList<Major> majorArrayList;

    public ArrayList<Major> getMajorArrayList() {
        return majorArrayList;
    }

    public void setMajorArrayList(ArrayList<Major> majorArrayList) {
        this.majorArrayList = majorArrayList;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setNumberOfMajor(Integer numberOfMajor) {
        this.numberOfMajor = numberOfMajor;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Integer getNumberOfMajor() {
        return numberOfMajor;
    }


}
