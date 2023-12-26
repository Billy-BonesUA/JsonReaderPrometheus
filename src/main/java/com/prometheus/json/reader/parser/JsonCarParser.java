package com.prometheus.json.reader.parser;

import com.prometheus.json.reader.model.Car;

public class JsonCarParser {

   private static String JSON_FILE_PREFIX = "[\n  {\n    ";
   private static String JSON_FILE_POSTFIX = "\n  }\n]\n";

   private static String EMPTY_STRING = "";

   private static String CARS_DELIMITER_REGEX = "\\n\\s*},\\n\\s*\\{\\n\\s*";
   private static String CARS_FIELD_DELIMITER_REGEX = ",\\n\\s*";

   private static String CAR_FIELD_SPEED = "speed";
   private static String CAR_FIELD_YEAR = "year";

   private static String NAME_FIELD_PREFIX = "\"name\": \"";

   public Car[] parseCars(String fileContent) {
       fileContent = fileContent
               .replace(JSON_FILE_PREFIX, EMPTY_STRING)
               .replace(JSON_FILE_POSTFIX, EMPTY_STRING);

       String[] carObjects = fileContent.split(CARS_DELIMITER_REGEX);

       Car[] cars = new Car[carObjects.length];
       for (int i = 0; i < carObjects.length; i++) {
           Car car = parseCar(carObjects[i]);
           cars[i] = car;
       }
       return cars;
   }

   private Car parseCar(String carJsonObject) {
       String[] carFields = carJsonObject.split(CARS_FIELD_DELIMITER_REGEX);

       int speed = parseIntField(carFields, CAR_FIELD_SPEED);
       int year = parseIntField(carFields, CAR_FIELD_YEAR);
       String name = parseNameField(carFields);

       Car car = new Car();
       car.setSpeed(speed);
       car.setYear(year);
       car.setName(name);
       return car;
   }

   private int parseIntField(String[] carInfo, String fieldName) {
       for (String field : carInfo) {
           if (field.startsWith("\"" + fieldName + "\": ")) {
               return Integer.parseInt(field.substring(fieldName.length() + 4));
           }
       }
       return -1;
   }

   private String parseNameField(String[] carInfo) {
       for (String field : carInfo) {
           if (field.startsWith(NAME_FIELD_PREFIX)) {
               return field.substring(NAME_FIELD_PREFIX.length(), field.length() - 1);
           }
       }
       return null;
   }
}
