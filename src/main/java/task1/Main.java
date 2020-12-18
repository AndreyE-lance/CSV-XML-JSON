package task1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        //list.forEach(e-> System.out.println(e.toString()));
        String json = listToJson(list);
        //System.out.println(json);
        writeString(json);

    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> list = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> cpmStrategy = new ColumnPositionMappingStrategy<>();
            cpmStrategy.setType(Employee.class);
            cpmStrategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csvBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(cpmStrategy)
                    .build();
            list= csvBean.parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private static String listToJson(List<Employee> list){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    private static boolean writeString(String string){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("task1_result.json"))){
            bufferedWriter.write(string);
            return true;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
