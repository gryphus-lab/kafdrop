package kafdrop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.TRACE;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class KafdropTest extends AbstractIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private Kafdrop kafdrop;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void contextLoads() {
    assertThat(kafdrop).isNotNull();
  }

  @Test
  void getReturnsExpectedGutHubStarText() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(java.util.List.of(MediaType.TEXT_HTML));
    ResponseEntity<String> responseEntity = restTemplate.exchange(
      "http://localhost:" + port + "/", HttpMethod.GET, new HttpEntity<>(headers), String.class);
    assertEquals(OK, responseEntity.getStatusCode());
    assertThat(responseEntity.getBody()).contains("Star Kafdrop on GitHub");
  }

  @Test
  void traceMethodExpectedDisallowedReturnCode() {
    ResponseEntity<String> response = restTemplate
      .exchange("http://localhost:" + port + "/", TRACE, null, String.class);
    assertEquals(METHOD_NOT_ALLOWED, response.getStatusCode());
  }
}
