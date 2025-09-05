package co.credit.app.api.config;

import co.credit.app.api.mapper.AuthRequestDTOMapper;
import co.credit.app.api.mapper.AuthResponseDTOMapper;
import co.credit.app.usecase.auth.AuthUseCase;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.credit.app.api.Handler;
import co.credit.app.api.RouterRest;
import co.credit.app.api.commons.ValidatorRequest;
import co.credit.app.api.mapper.UserDTOMapper;
import co.credit.app.usecase.user.UserUseCase;
import reactor.core.publisher.Flux;

@ContextConfiguration(classes = { RouterRest.class, Handler.class })
@WebFluxTest
@Import({ CorsConfig.class, SecurityHeadersConfig.class })
class ConfigTest {

        @Autowired
        private WebTestClient webTestClient;

        @MockBean
        private UserDTOMapper userDTOMapper;

        @MockBean
        private UserUseCase userUseCase;

        @MockBean
        private ValidatorRequest validatorErrorValidator;

        @MockBean
        private AuthUseCase authUseCase;

        @MockBean
        private AuthRequestDTOMapper authRequestDTOMapper;

        @MockBean
        private AuthResponseDTOMapper authResponseDTOMapper;

        @Test
        void corsConfigurationShouldAllowOrigins() {
                /*
                 * User user = User.builder()
                 * .name("David")
                 * .email("david.parra@mail.com")
                 * .document("1015435094")
                 * .phone("3143210987")
                 * .salary(3200000.0)
                 * .lastName("Parra")
                 * .roleId(1L)
                 * .build();
                 */

                when(userUseCase.getAllUsers()).thenReturn(Flux.empty());

                webTestClient.get()
                                .uri("/api/login")
                                .exchange()
                                .expectStatus().isUnauthorized()
                                /*.expectHeader().valueEquals("Content-Security-Policy",
                                                "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                                .expectHeader().valueEquals("Server", "")
                                .expectHeader().valueEquals("Cache-Control", "no-store")
                                .expectHeader().valueEquals("Pragma", "no-cache")
                                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin")
                                 */
                                ;

        }

}