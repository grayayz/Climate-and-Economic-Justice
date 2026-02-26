
package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel(String inputLine) {
        // Split the input line to extract information
        String[] data = inputLine.split(",");
        // Extract the state's name
        String stateName = data[2];
    
        // Check if the firstState is null (list is empty)
        if (firstState == null) {
            firstState = new StateNode(stateName, null, null);
            return; // New state added, exit the method
        }
    
        // Initialize a variable to keep track of the current node in the traversal
        StateNode current = firstState;
        StateNode previous = null; // To keep track of the last node in case we need to add a new state
    
        // Traverse the list to find if the state already exists
        while (current != null) {
            if (current.getName().equals(stateName)) {
                return; // State already exists, do nothing
            }
            previous = current; // Update the previous node
            current = current.getNext(); // Move to the next node
        }
    
        // If the state does not exist, add it to the end of the list
        previous.setNext(new StateNode(stateName, null, null));
    }
    
    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel(String inputLine) {
        String[] data = inputLine.split(",");
        String countyName = data[1];
        String stateName = data[2];
    
        // First, find the matching state node
        StateNode currentState = firstState;
        while (currentState != null && !currentState.getName().equals(stateName)) {
            currentState = currentState.getNext();
        }
    
        if (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
            CountyNode lastCounty = null;
            while (currentCounty != null) {
                if (currentCounty.getName().equals(countyName)) {
                    return; // County already exists, do nothing
                }
                lastCounty = currentCounty;
                currentCounty = currentCounty.getNext();
            }
    
            // If the county doesn't exist, add it to the end of the list
            CountyNode newCounty = new CountyNode(countyName, null, null);
            if (lastCounty == null) {
                // This means there are no counties yet, so add as the first county
                currentState.setDown(newCounty);
            } else {
                // Add the new county to the end of the list
                lastCounty.setNext(newCounty);
            }
        }
    }
    

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel(String inputLine) {
        String[] data = inputLine.split(",");
        String communityName = data[0];
        String countyName = data[1];
        String stateName = data[2];
        // for data method
        double percentAfricanAmerican = Double.parseDouble(data[3]);
        double percentNative = Double.parseDouble(data[4]);
        double percentAsian = Double.parseDouble(data[5]);
        double percentWhite = Double.parseDouble(data[8]);
        double percentHispanic = Double.parseDouble(data[9]);
        double pmLevel = Double.parseDouble(data[49]);
        double chanceOfFlood = Double.parseDouble(data[37]);
        double povertyLine = Double.parseDouble(data[121]);
        String disadvantaged = data[19];
    
        // matching state node
        StateNode currentState = firstState;
        while (currentState != null && !currentState.getName().equals(stateName)) {
            currentState = currentState.getNext();
        }
        if (currentState != null) {
            //  matching county node within the state
            CountyNode currentCounty = currentState.getDown();
            // condition for states that don't have counties
            if (currentCounty == null){
                return;
            }
            while (currentCounty != null && !currentCounty.getName().equals(countyName)) {
                currentCounty = currentCounty.getNext();
            }
            if (currentCounty != null) {
                // check if the community already exists in this county's list
                CommunityNode currentCommunity = currentCounty.getDown();
                CommunityNode lastCommunity = null;
                while (currentCommunity != null) {
                    if (currentCommunity.getName().equals(communityName)) {
                        return; // alr exists, do nothing
                    }
                    lastCommunity = currentCommunity;
                    currentCommunity = currentCommunity.getNext();
                }
                // new data object
                Data communityData = new Data(percentAfricanAmerican, percentNative, percentAsian, percentWhite, percentHispanic, disadvantaged, pmLevel, chanceOfFlood, povertyLine);
                    
                // if the community doesn't exist, add it to the end of the list
                CommunityNode newCommunity = new CommunityNode(communityName, null, communityData);
                if (lastCommunity == null) {
                    // this means there are no communities yet, so add as the first community
                    currentCounty.setDown(newCommunity);
                } else {
                    // add new community to the end of the list
                    lastCommunity.setNext(newCommunity);
                }
            }
        }
    }
    

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities(double userPrcntage, String race) {
        int count = 0;
        StateNode currentState = firstState;
    
        double adjustedPrcntage = userPrcntage / 100;
    
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
            while (currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null) {
                    Data communityData = currentCommunity.getInfo();
                    double racePercentage = 0;
                    if (race.equals("African American")){
                        racePercentage = communityData.getPrcntAfricanAmerican();
                    } else if (race.equals("Native American")){
                        racePercentage = communityData.getPrcntNative();
                    } else if (race.equals("Asian American")){
                        racePercentage = communityData.getPrcntAsian();
                    } else if (race.equals("White American")) {
                        racePercentage = communityData.getPrcntWhite();
                    } else if (race.equals("Hispanic American")){
                        racePercentage = communityData.getPrcntHispanic();
                    }
                    boolean isDisadvantaged = communityData.getAdvantageStatus().equalsIgnoreCase("True");
                    if (racePercentage >= adjustedPrcntage && isDisadvantaged) {
                        count++;
                    }
    
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
        return count;
    }
    

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        //WRITE YOUR CODE HERE
        int count = 0;
        StateNode currentState = firstState;
    
        double adjustedPrcntage = userPrcntage / 100;
    
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
            while (currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null) {
                    Data communityData = currentCommunity.getInfo();
                    double racePercentage = 0;
                    if (race.equals("African American")){
                        racePercentage = communityData.getPrcntAfricanAmerican();
                    } else if (race.equals("Native American")){
                        racePercentage = communityData.getPrcntNative();
                    } else if (race.equals("Asian American")){
                        racePercentage = communityData.getPrcntAsian();
                    } else if (race.equals("White American")) {
                        racePercentage = communityData.getPrcntWhite();
                    } else if (race.equals("Hispanic American")){
                        racePercentage = communityData.getPrcntHispanic();
                    }
                    boolean isDisadvantaged = communityData.getAdvantageStatus().equalsIgnoreCase("True");
                    if (racePercentage >= adjustedPrcntage && !isDisadvantaged) {
                        count++;
                    }
    
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
        return count;
    }
    
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {
        // WRITE YOUR METHOD HERE
        StateNode currentState = firstState; 
        ArrayList<StateNode> count= new ArrayList<>();       
        while (currentState != null) {
            // d
            boolean stateLevel = false;
            CountyNode currentCounty = currentState.getDown();
            while (currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null) {
                    double communityPM = currentCommunity.getInfo().getPMlevel();
                    if (communityPM >= PMlevel){
                        stateLevel = true;
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            if (stateLevel){
                count.add(currentState); 
            }
            currentState = currentState.getNext();
        }
        
        return count;
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        // WRITE YOUR METHOD HERE
        int count = 0;
        StateNode currentState = firstState;
    
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
            while (currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null) {
                    Data communityData = currentCommunity.getInfo();
                    double chanceOfFlood = communityData.getChanceOfFlood();
                    if (chanceOfFlood >= userPercntage){
                        count++;
                    }       
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
        return count;
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        //WRITE YOUR METHOD HERE
        StateNode currentState = firstState;
        ArrayList<CommunityNode> lowIncome = new ArrayList<>();
        while (currentState != null) {
            if (!currentState.name.equals(stateName)){
                currentState = currentState.getNext();
                continue;
            }
            
            CountyNode currentCounty = currentState.getDown();
            while (currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null) {
                    if (lowIncome.size() < 10){
                        lowIncome.add(currentCommunity);
                        currentCommunity = currentCommunity.getNext();
                        continue;
                    }
                    CommunityNode lowestCommunityNode = lowIncome.get(0);
                    for (int i = 0; i < lowIncome.size(); i++){
                        if (lowestCommunityNode.getInfo().getPercentPovertyLine() > lowIncome.get(i).getInfo().getPercentPovertyLine()){
                            lowestCommunityNode = lowIncome.get(i);
                        }
                    }
                    if (currentCommunity.getInfo().getPercentPovertyLine() > lowestCommunityNode.getInfo().getPercentPovertyLine()){
                        lowIncome.set(lowIncome.indexOf(lowestCommunityNode), currentCommunity);
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
            break;
        }
        return lowIncome;
    }
  }
    
