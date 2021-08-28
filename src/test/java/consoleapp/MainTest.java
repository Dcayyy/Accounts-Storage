package consoleapp;

import dbmodel.DataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    DataSource dataSource = null;


    @Before
    public void init() {
        dataSource = new DataSource();
    }

    @Test
    public void main() {
        if (!dataSource.open()) {
            System.out.println("Can't open database!");
            return;
        }

        dataSource.close();
    }
}
