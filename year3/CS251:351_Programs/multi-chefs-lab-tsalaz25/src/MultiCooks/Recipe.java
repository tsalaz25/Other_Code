package MultiCooks;

public record Recipe(String dish, String station, long cookTime) {
    public static Recipe fromCSVLine(String line){
        String[] recipeVals = line.split(",");
        long cookTime = Long.parseLong(recipeVals[2]);
        return new Recipe(recipeVals[0], recipeVals[1], cookTime);
    }
}
