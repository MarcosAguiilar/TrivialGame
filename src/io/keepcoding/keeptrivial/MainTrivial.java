package io.keepcoding.keeptrivial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainTrivial {
	public static void main(String[] args) {
        ArrayList<Topic> topics = getQuestions();
        List<Team> teams = getTeams();
        
        if (teams.isEmpty()) {
            System.out.println("No hay equipos disponibles.");
            return;
        }

        playGame(topics, teams);
    }
	public static void title(String text) {
		int length = text.length();
		printHashtagLine(length + 4); // Bordes

        System.out.println("# " + text + " #");

        printHashtagLine(length + 4);
	}
	
	public static void printHashtagLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("#");
        }
        System.out.println();
    }
	
	 public static boolean esTransformableAEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	private static int getRandomInt(int max) {
		return new Random().nextInt(max);
	}
	
	public static void addElement(List<String> lista, String elemento) {
        lista.add(elemento);
	}
	
	private static ArrayList<Topic> getQuestions() {
		ArrayList<Topic> list = new ArrayList<>();
		
		 File folder = new File("questions");
	        if (!folder.exists()) {
	            title("Error al cargar el fichero");
	        } else {
	        	File[] filesList = folder.listFiles();

	            for (File file : filesList) {
	                if (file.isFile() && file.getName().endsWith(".txt")) {
	                    String topicName = file.getName().substring(0, file.getName().length() - 4);
	                    // TODO create topic
	                    Topic topic = new Topic(topicName);
	                    
	                    // Read the question
	                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	                        String line;
	                        List<String> block = new ArrayList<>();

	                        while ((line = br.readLine()) != null) {
                        		block.add(line);

	                            if (block.size() == 6) { // número de lineas que componen una pregunta
	                                var questionAsk = block.get(0);
	                                var answer1 = block.get(1);
	                                var answer2 = block.get(2);
	                                var answer3 = block.get(3);
	                                var answer4 = block.get(4);
	                                var rightOption = Integer.parseInt(block.get(5));
	                                
	                                // TODO create question
	                                Question question = new Question(questionAsk,answer1,answer2,answer3,answer4,rightOption);
	                                topic.addQuestion(question);
	                                block.clear();
	                            }
	                        }
	                        // TODO Add to list
	                        list.add(topic);
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                  
	                }
	            }
	        }
	        
		return list;
	}
	
	private static List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        title("Introduce los nombres de los equipos (introduce [q] para finalizar)");

        while (true) {
            System.out.print("Nombre del equipo: ");
            String teamName = scanner.nextLine();
            if (teamName.equals("q")) {
                break;
            }
            teams.add(new Team(teamName));
        }

        return teams;
    }
	
	private static void playGame(ArrayList<Topic> topics, List<Team> teams) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int currentTeamIndex = 0;

        while (true) {
            Team currentTeam = teams.get(currentTeamIndex);
            // Selecciona un tema aleatorio
            Topic randomTopic = topics.get(random.nextInt(topics.size()));

            if (randomTopic.getQuestions().isEmpty()) {
                continue;
            }

            // Selecciona una pregunta aleatoria del tema seleccionado
            Question randomQuestion = randomTopic.getQuestions().get(random.nextInt(randomTopic.getQuestions().size()));

            // Muestra la pregunta con su tema y opciones
            title("Turno del equipo: " + currentTeam.getName());
            System.out.println();
            title(randomTopic.getTitle());
            System.out.println(randomQuestion.getQuestion());
            System.out.println("1. " + randomQuestion.getAnswer1());
            System.out.println("2. " + randomQuestion.getAnswer2());
            System.out.println("3. " + randomQuestion.getAnswer3());
            System.out.println("4. " + randomQuestion.getAnswer4());

            // Lee la respuesta del usuario
            System.out.print("Selecciona tu respuesta (1-4): ");
            int userAnswer = scanner.nextInt();

            // Verifica la respuesta
            if (userAnswer == randomQuestion.getRightOption()) {    	
            	System.out.println();
                title("¡Correcto!");
                currentTeam.addPoint(randomTopic.getTitle());
            } else {
            	System.out.println();
                title("Incorrecto. Respuesta correcta: " + randomQuestion.getRightOption());
            }
            
            printClassification(teams, topics);

            // Verificar si el equipo ha ganado
            if (currentTeam.hasAtLeastOnePointInEachTopic(topics)) {
                title("¡" + currentTeam.getName() + " ha ganado el juego!");
                
                break;
            }
            
            // Alternar equipos
            currentTeamIndex = (currentTeamIndex + 1) % teams.size();
        }
    }

    private static void printClassification(List<Team> teams, List<Topic> topics) {
    	System.out.println();
    	System.out.println();
        title("Clasificación");
        for (Team team : teams) {
            System.out.print(team.getName() + ": \n");
            System.out.print("|| ");
            for (Topic topic : topics) {
                int points = team.getPointsForTopic(topic.getTitle());
                
                System.out.print(topic.getTitle() + " - " + points + " || ");
            }
            System.out.println();
        }
        System.out.println();
    }
}