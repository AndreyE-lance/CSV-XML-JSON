package task2;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import task1.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> list = parseXML("data.xml");
        task1.Main.writeString(task1.Main.listToJson(list), "task2_result.json");
    }

    private static List<Employee> parseXML(String fileName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        List<Employee> list = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            Node rootNode = doc.getDocumentElement();
            list = read(rootNode);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static List<Employee> read(Node node) {
        List<Employee> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                Element element = (Element) node_;
                Long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                Employee employee = new Employee(id, firstName, lastName, country, age);
                list.add(employee);
            }
        }
        return list;
    }

}
