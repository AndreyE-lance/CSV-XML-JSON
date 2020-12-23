package task3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import task1.Employee;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String json = readString("task1_result.json");
        try {
            List<Employee> employeeList = jsonToList(json);
            employeeList.forEach(emp-> System.out.println(emp.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static String readString(String fileName) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
            while (bReader.ready()) {
                result.append(bReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    private static List<Employee> jsonToList(String json) throws ParseException {
        List<Employee> employeeList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONArray array = (JSONArray) jsonParser.parse(json);
        //array.forEach(v-> System.out.println(v));
        array.forEach(v -> {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Employee employee = gson.fromJson(String.valueOf(v), Employee.class);
            employeeList.add(employee);
        });
        return employeeList;
    }
}
