package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestClassTest {

    private TestClass test;

    @BeforeEach
    void init() {
        test = new TestClass("test");
    }

    @Test
    void getTest() {
        assertEquals("test", test.getTest());
    }

    @Test
    void setTest() {
        test.setTest("test2");
        assertEquals("test2", test.getTest());
    }
}
