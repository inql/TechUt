package com.example.shdemo.service;

import com.example.shdemo.domain.Description;

import java.util.List;

public interface DescriptionService {

    List getAllDescriptions();
    void addDescriptionToDog(Long descriptionId, Long dogId);
    Long addDescription(Description description);
    Description getDescriptionById(Long descriptionId);
    void updateDescription(Description description);
    void deleteDescription(Description description);


}
