package com.cl.roadshow.spring.perm;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ErrorService {

    @MonitorLog
    public void handleError(){
        System.out.print("errorService handle error");
    }
}
