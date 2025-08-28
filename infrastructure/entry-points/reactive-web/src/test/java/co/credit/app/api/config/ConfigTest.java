package co.credit.app.api.config;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.credit.app.api.Handler;
import co.credit.app.api.RouterRest;
import co.credit.app.api.mapper.UserDTOMapper;
import co.credit.app.usecase.user.UserUseCase;
import reactor.core.publisher.Flux;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserDTOMapper userDTOMapper;

    @MockBean
    private UserUseCase userUseCase;

    @Test
    void corsConfigurationShouldAllowOrigins() {
        /*User user = User.builder()
                .name("David")
                .email("david.parra@mail.com")
                .document("1015435094")
                .phone("3143210987")
                .salary(3200000.0)
                .lastName("Parra")
                .roleId(1L)
                .build();*/

        when(userUseCase.getAllUsers()).thenReturn(Flux.empty());
        
        webTestClient.get()
                .uri("/api/user")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}