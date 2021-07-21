import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testEqualChars(){
        CharacterComparator offByN = new OffByN(2);
        assertTrue(offByN.equalChars('a','c'));
        assertTrue(offByN.equalChars('#','%'));
        assertFalse(offByN.equalChars('a','b'));
    }
}
