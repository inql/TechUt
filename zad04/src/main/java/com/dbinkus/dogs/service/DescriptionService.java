package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Description;

import java.util.List;

public interface DescriptionService {

    List getAllDescriptions();
    void addDescriptionToDog(Long descriptionId, Long dogId);
    Long addDescription(Description description);
    Description getDescriptionById(Long descriptionId);
    Description getDescriptionByContents(String content);
    void updateDescription(Description description);
    void deleteDescription(Description description);


}
