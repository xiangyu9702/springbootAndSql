package com.example.web.Institute;

import com.example.web.major.Major;

import java.util.ArrayList;


public class Institute {
    private Long instituteId;

    private String instituteName;

    private Long numberOfMajor;

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

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Long getNumberOfMajor() {
        return numberOfMajor;
    }

    public void setNumberOfMajor(Long numberOfMajor) {
        this.numberOfMajor = numberOfMajor;
    }
}
