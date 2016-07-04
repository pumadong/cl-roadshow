package com.cl.roadshow.htw.ex03;

import com.cl.roadshow.htw.ex03.connector.http.HttpRequest;
import com.cl.roadshow.htw.ex03.connector.http.HttpResponse;
import java.io.IOException;

public class StaticResourceProcessor {

  public void process(HttpRequest request, HttpResponse response) {
    try {
      response.sendStaticResource();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
