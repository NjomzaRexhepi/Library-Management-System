import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XMLProcessor<T> {
    private final Class<T> type;

    public XMLProcessor(Class<T> type) {
        this.type = type;
    }

    //serialize
    public String serialize(T model, String path) {
        try {
            BufferedOutputStream buffered = new BufferedOutputStream(
                    new FileOutputStream(path)
            );
            XMLEncoder encoder = new XMLEncoder(buffered);
            encoder.writeObject(model);
            encoder.close();
            return encoder.toString();
        }catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    //deserialize
    public T deserialize(String path) {
        try {
            BufferedInputStream buffered = new BufferedInputStream(
                    new FileInputStream(path));

            XMLDecoder decoder = new XMLDecoder(buffered);

            T object = (T) decoder.readObject();
            return object;
        }catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}