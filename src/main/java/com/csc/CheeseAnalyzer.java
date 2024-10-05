package com.csc;
import java.io.*;

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

    String csvFile = "src\\cheese_data.csv";

    BufferedReader reader = null;
       
    try {
      reader = new BufferedReader(new FileReader(csvFile));
      String line = "";
      int lineNumber = 0;

      while((line = reader.readLine()) != null) {
        line = line.trim();

        if(line.isEmpty()) {
          continue;
        }

        lineNumber++;

        if(lineNumber == 1) {
          continue;
        }

        String[] column = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        
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

    } catch (Exception e) {
      e.printStackTrace();
      return;
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

    try (FileWriter writer = new FileWriter("output.txt")) {
      writer.write("Amount of Pasteurized Milk: " + amountOfPasteurizedMilk + '\n');
      writer.write("Amount of Raw Milk: " + amountOfRawMilk + '\n');
      writer.write("Amount of Organic Cheese greater than 41%: " + amountOfOrganicGreaterThanFortyOnePercent + '\n');
      writer.write("Most Common Animal: " + mostCommonTypeOfAnimal);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}