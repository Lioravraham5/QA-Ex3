package pom_tests_cases;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
     SignInTest.class,
     AddToCartTest.class,
     PaymentTest.class,
     CheckoutTest.class
})
public class SanityTest extends BaseTest{

}

