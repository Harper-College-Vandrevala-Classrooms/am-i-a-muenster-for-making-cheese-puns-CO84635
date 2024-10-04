package com.csc;
import java.io.*;

public class CheeseAnalyzer {
  public static void main(String[] args) {
    int amountOfPasteurizedMilk = 0;
    int amountOfRawMilk = 0;
    int amountOfOrganicGreaterThanFourtyOnePercent = 0;
    String mostCommonTypeOfAnimal = "";
    int amountOfCows = 0;
    int amountOfGoats = 0;
    int amountOfEwe = 0;
    int amountOfBuffalo = 0;
    

    String csvFile = "src\\cheese_data.csv";
    BufferedReader reader = null;
    String line = "";
    String [] row;

    try {
      reader = new BufferedReader(new FileReader(csvFile));

      while((line = reader.readLine()) != null) {
        line = line.trim();

        if(line.isEmpty()) {
          continue;
        }

        row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        
        if ("Pasteurized".equals(row[9])) {
          amountOfPasteurizedMilk++;
        } else if ("Raw Milk".equals(row[9])) {
          amountOfRawMilk++;
        }

        // String moisturePercentage = row[3].trim();
        
        // if(!moisturePercentage.isEmpty()) {
        //   int moisturePercent = Integer.parseInt(moisturePercentage);
        //   if ("1".equals(row[6].trim()) && moisturePercent > 41.0) {
        //     amountOfOrganicGreaterThanFourtyOnePercent++;
        //   }
        // }
        
        switch (row[8]) {
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
    } finally {
        try{
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } 

    System.out.println(amountOfPasteurizedMilk);
    System.out.println(amountOfRawMilk);
    System.out.println(amountOfOrganicGreaterThanFourtyOnePercent);
    

    int[] amountOfAnimals = {amountOfCows, amountOfGoats,  amountOfEwe, amountOfBuffalo,};
    String[] animalTypes = {"Cow", "Goat", "Ewe", "Buffalo Cows"};
    int largestAnimalCount = amountOfAnimals[0];
    mostCommonTypeOfAnimal = animalTypes[0];

    for (int i = 1; i < amountOfAnimals.length; i++) {
      if (amountOfAnimals[i] > largestAnimalCount) {
        mostCommonTypeOfAnimal = animalTypes[i];
      }
    }

    System.out.println("Most Common Animal: " + mostCommonTypeOfAnimal);
  }
}