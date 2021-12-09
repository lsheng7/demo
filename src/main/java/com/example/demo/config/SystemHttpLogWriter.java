package com.example.demo.config;

import static org.apiguardian.api.API.Status.STABLE;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

@Slf4j
@API(status = STABLE)
public class SystemHttpLogWriter implements HttpLogWriter {

  @Override
  public boolean isActive() {
    return log.isInfoEnabled();
  }

  @Override
  public void write(Precorrelation precorrelation, String request) throws IOException {
    log.info(request);
  }

  @Override
  public void write(Correlation correlation, String response) throws IOException {
    log.info(response);
  }

}
