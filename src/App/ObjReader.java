package App;

import App.J3D.Mesh;
import App.Math.Triangle;
import App.Math.Vertex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class ObjReader {
    private Mesh object = new Mesh();
    private List<Vertex> vertices = new ArrayList<>();


    public ObjReader(String file) {
        try {
            FileInputStream fileStream = new FileInputStream(file);
            String line = "";
            int currentChar = 0;

            while (currentChar != -1) {
                currentChar = fileStream.read();

                if ((char) currentChar == '\n') {
                    parseLine(line);
                    line = "";

                } else {
                    line += (char) currentChar;
                }
            }

            fileStream.close();

        } catch (FileNotFoundException fnfEx) {
            System.err.println("Unable to open the file");

        } catch (Exception defaultException) {
            System.err.println("An error was encountered while trying to build the object");
        }
    }


    private void parseLine(String line) {
        String[] elements = line.split(" ");

        try {
            if (elements[0].equals("v")) {
                vertices.add(new Vertex(
                        Double.parseDouble(elements[1]),
                        Double.parseDouble(elements[2]),
                        Double.parseDouble(elements[3])
                ));

            } else if (elements[0].equals("f")) {
                object.add(new Triangle(
                        vertices.get((int) Double.parseDouble(elements[1]) - 1),
                        vertices.get((int) Double.parseDouble(elements[2]) - 1),
                        vertices.get((int) Double.parseDouble(elements[3]) - 1)
                ));
            }

        } catch (NumberFormatException numberFormatException) {
            System.err.println("Error encountered while parsing number in file, Line : " + line);

        } catch (Exception exception) {
            System.err.println("An error occurred");
        }
    }


    public Mesh getObject() {
        return object;
    }
}
