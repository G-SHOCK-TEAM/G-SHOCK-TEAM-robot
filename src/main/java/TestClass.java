import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Created by daniel on 24.08.16.
 */
@EnableAutoConfiguration
public class TestClass {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestClass.class, args);
    }

    public boolean returnTrue() {
        return true;
    }
    //Grzegorz
}
