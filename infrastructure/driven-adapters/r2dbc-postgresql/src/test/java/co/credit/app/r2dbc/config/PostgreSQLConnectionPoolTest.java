package co.credit.app.r2dbc.config;

import org.mockito.InjectMocks;
import org.mockito.Mock;

class PostgreSQLConnectionPoolTest {

    @InjectMocks
    private MySQLConnectionPool connectionPool;

    @Mock
    private MysqlConnectionProperties properties;

    /* 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(properties.host()).thenReturn("localhost");
        when(properties.port()).thenReturn(5432);
        when(properties.database()).thenReturn("dbName");
        when(properties.username()).thenReturn("username");
        when(properties.password()).thenReturn("password");
    }

    @Test
    void getConnectionConfigSuccess() {
        assertNotNull(connectionPool.getConnectionConfig(properties));
    }
    */
}
