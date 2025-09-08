package co.credit.app.redis.template;

import co.credit.app.redis.template.config.ConfigProperties;
import co.credit.app.redis.template.entity.AttemptsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class ReactiveRedisTemplateAdapterOperationsTest {

    @Mock
    private ReactiveRedisConnectionFactory connectionFactory;

    @Mock
    private ObjectMapper objectMapper;

    private ReactiveRedisTemplateAdapter adapter;

    @Mock
    private ConfigProperties configProperties;

    @Mock
    private ReactiveRedisTemplate<String, AttemptsEntity> reactiveRedisTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        var entity = new AttemptsEntity(1);
        when(objectMapper.map("value", AttemptsEntity.class)).thenReturn(entity);

        adapter = new ReactiveRedisTemplateAdapter(connectionFactory, objectMapper, configProperties, reactiveRedisTemplate);
    }

    /*@Test
    void testSave() {
        StepVerifier.create(adapter.save("key", "value"))
                .expectNext("value")
                .verifyComplete();
    }

    @Test
    void testSaveWithExpiration() {

        StepVerifier.create(adapter.save("key", "value", 2))
                .expectNext("value")
                .verifyComplete();
    }*/

    @Test
    void testFindById() {

        StepVerifier.create(adapter.findById("key"))
                .verifyComplete();
    }

}