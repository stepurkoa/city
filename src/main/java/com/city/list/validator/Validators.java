package com.city.list.validator;

import com.city.list.exceptions.ServiceError;
import com.city.list.exceptions.ServiceException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class Validators {

    public void urlValidator(String url){
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url))
            throw ServiceException.builder(ServiceError.IT_IS_NOT_URL).build();
    }
}
