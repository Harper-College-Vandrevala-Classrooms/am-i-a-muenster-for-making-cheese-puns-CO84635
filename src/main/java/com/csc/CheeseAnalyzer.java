package com.csc;
import java.io.*;
import java.util.ArrayList;


public class CheeseAnalyzer {
  @SuppressWarnings("resource")
  public static void main(String[] args) {
    int amountOfPasteurizedMilk = 0;
    int amountOfRawMilk = 0;
    int amountOfOrganicGreaterThanFortyOnePercent = 0;

    String mostCommonTypeOfAnimal = "";
    int amountOfCows = 0;
    int amountOfGoats = 0;
    int amountOfEwe = 0;
    int amountOfBuffalo = 0;

    double averageMoisturePercentage = 0.0;
    double moisturePercentageSum = 0.0;
    int validMoistureCount = 0;



    ArrayList<Integer> currentIds = new ArrayList<Integer>();

    String csvFileInput = "src\\cheese_data.csv";
    File csvFileOutputWithoutHeaders = new File("cheese_without_headers.csv");
    File csvFileOutputWithoutIds = new File("cheese_without_ids.csv");
    File txtFileMissingIds = new File("missing_ids.txt");
    BufferedReader reader = null;
       
    try {
      reader = new BufferedReader(new FileReader(csvFileInput));
      BufferedWriter outputWithOutHeaders = new BufferedWriter(new FileWriter(csvFileOutputWithoutHeaders));
      BufferedWriter outputWithOutIds = new BufferedWriter(new FileWriter(csvFileOutputWithoutIds));
      String line = "";
      int lineNumber = 0;

      while((line = reader.readLine()) != null) {
        line = line.trim();

        if(line.isEmpty()) {
          continue;
        }

        lineNumber++;

        String[] column = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        if (lineNumber == 1) {
          for (int i = 1; i < column.length; i++) {
            outputWithOutIds.write(column[i]);
            outputWithOutIds.write(((i < column.length - 1) ? "," : "\n"));
          }
          continue;
        }

        try {
          int id = Integer.parseInt(column[0]);
          currentIds.add(id);
        } catch (NumberFormatException e) {
          System.err.println("Invalid ID format on line " + lineNumber);
          continue;
        }

        try {
          if(!column[3].isEmpty()) {
          double moisturePercentage = Double.parseDouble(column[3]);
          moisturePercentageSum+= moisturePercentage;
          validMoistureCount++;
          }
        } catch (NumberFormatException e) {
          System.err.println("Invalid Moisture format on line " + lineNumber);
          continue;
        }
        
        outputWithOutHeaders.write(line);
        outputWithOutHeaders.newLine();

        for (int i = 1; i < column.length; i++) {
          outputWithOutIds.write(column[i]);
          outputWithOutIds.write(((i < column.length - 1) ? "," : "\n"));
        }
        

        if ("Pasteurized".equals(column[9])) {
          amountOfPasteurizedMilk++;
        } else if ("Raw Milk".equals(column[9])) {
          amountOfRawMilk++;
        }

        boolean isOrganic = column[6].equals("1");
        if(isOrganic) {
          try{
            Double moisturePercentage = Double.parseDouble(column[3].trim());
            if(moisturePercentage >= 41){
              amountOfOrganicGreaterThanFortyOnePercent++;
            }
        } catch (NumberFormatException e) {
        }
      }
      
        switch (column[8]) {
          case "Cow":
            amountOfCows++;
            break;
          case "Goat":
            amountOfGoats++;
            break;
          case "Ewe":
            amountOfEwe++;
            break;  
          case "Buffalo Cow":
            amountOfBuffalo++;
            break;
          default:
            break;      
        }
      }

      outputWithOutHeaders.close();
      outputWithOutIds.close();

    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    if (validMoistureCount > 0) {
      averageMoisturePercentage = moisturePercentageSum / (validMoistureCount);
      averageMoisturePercentage = Math.round(averageMoisturePercentage * 100) / 100;
    } else {
      averageMoisturePercentage = 0.0;
    }
    
       
    int[] amountOfAnimals = {amountOfCows, amountOfGoats,  amountOfEwe, amountOfBuffalo,};
    String[] animalTypes = {"Cow", "Goat", "Ewe", "Buffalo Cows"};
    int largestAnimalCount = amountOfAnimals[0];
    mostCommonTypeOfAnimal = animalTypes[0];

    for (int i = 1; i < amountOfAnimals.length; i++) {
      if (amountOfAnimals[i] > largestAnimalCount) {
        largestAnimalCount = amountOfAnimals[i];
        mostCommonTypeOfAnimal = animalTypes[i];
      }
    }

    try(FileWriter missingIdWriter = new FileWriter(txtFileMissingIds)) {
    
      int maxId = currentIds.get(currentIds.size() - 1);
      boolean[] knownIds = new boolean[maxId + 1];

      for (int id : currentIds) {
          if(id > 0 && id < maxId) {
            knownIds[id] = true;
          }
      }

      for (int i = 1; i < maxId; i++) {
          if (!knownIds[i]) {
            missingIdWriter.write(i + "\n");
          }
      }
      
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    try (FileWriter writer = new FileWriter("output.txt")) {
      writer.write("Amount of Pasteurized Milk: " + amountOfPasteurizedMilk + '\n');
      writer.write("Amount of Raw Milk: " + amountOfRawMilk + '\n');
      writer.write("Amount of Organic Cheese greater than 41%: " + amountOfOrganicGreaterThanFortyOnePercent + '\n');
      writer.write("Most Common Animal: " + mostCommonTypeOfAnimal + '\n');
      writer.write("Average moisture percent: "  + averageMoisturePercentage + "%");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}