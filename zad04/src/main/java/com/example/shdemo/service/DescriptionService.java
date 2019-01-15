package com.example.shdemo.service;

import com.example.shdemo.domain.Description;

public interface DescriptionService {

    void addDescriptionToDog(Long descriptionId, Long dogId);
    Long addDescription(Long descriptionId);
    Description getDescriptionById(Long descriptionId);
    void updateDescription(Description description);
    void deleteDescription(Description description);


}
