/**
 * Created by GrIfOn on 29.04.2017.
 */
import Core.NeuralNetwork.Initialization.Initialization;
import Core.NeuralNetwork.Initialization.RandomInit;
import Core.NeuralNetwork.Initialization.ZerosInit;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

public class InitializationTest {

    @Test
    public void randomInitTest() {
        Initialization initialization = new RandomInit(-1., 1.);
        SimpleMatrix matrix = initialization.init(5, 2);

        matrix.print();
    }

    @Test
    public void zeroInitTest() {
        Initialization initialization = new ZerosInit();
        SimpleMatrix matrix = initialization.init(5, 2);

        matrix.print();
    }
}
