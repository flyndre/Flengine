package de.flyndre.flengine.converter;

import java.util.Arrays;
import java.util.Scanner;

public class RequestHandler {
    private String engineName = "Flengine";
    private String engineAuthor = "TeamFlyndre";
    private String position = "";
    private String[] moves = {""};

    public void startUp(){
        Scanner systemInScanner = new Scanner(System.in);
        String input = "";

        while(true){
            input = systemInScanner.nextLine();

            String[] splittedInput = input.split(" ");

            while(true){
                switch(splittedInput[0].toLowerCase()){
                    case "uci":
                        //engine name and author
                        System.out.println("id name " + engineName);
                        System.out.println("id author " + engineAuthor);
                        //options if there were any

                        //uciok
                        System.out.println("uciok");
                        break;
                    case "setoption":
                        //ignore for the moment
                        break;
                    case "isready":
                        //no initialization needed here at the moment so indicate engine is ready
                        System.out.println("readyok");
                        break;
                    case "ucinewgame":
                        //ignore
                        break;
                    case "position":
                        //get position
                        if(splittedInput.length > 1){
                            this.position = splittedInput[1];

                            //get moves
                            if(splittedInput.length > 3){
                                if(splittedInput[2].equals("moves")){
                                    this.moves = Arrays.copyOfRange(splittedInput, 3, splittedInput.length);
                                }
                            }
                        }
                        //computing is started with the go command
                        break;
                    case "go":
                        //ignore params for the moment, start computing async by creating organizer with given values

                        break;
                    case "stop":
                        //return bestmove to gui
                        //System.out.println("bestmove ");

                        break;
                    default:
                        if(splittedInput.length > 1){
                            splittedInput = Arrays.copyOfRange(splittedInput, 1, splittedInput.length);
                            continue;
                        }
                }
                //leave the loop if input is recognized
                break;
            }

        }
    }

}
