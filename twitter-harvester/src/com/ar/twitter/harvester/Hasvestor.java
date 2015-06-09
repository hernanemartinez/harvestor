package com.ar.twitter.harvester;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import twitter4j.TwitterException;
import twitter4j.User;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;


/**
 * <font color="#000000">Main class for the harvester of followers in twitter.
 * </font>
 * 
 * @author hmartinez
 * @version 1.0
 * @updated 02-may-2013 10:49:20 p.m.
 */
public class Hasvestor {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FollowersHandler fh = new FollowersHandler();
		System.out.println("This is a console app for harvesting twitter followers...");
		
		//TODO: falta refactory de mongoDB
		//TODO: falta refactory de rate limits en todos los procesos.
		
		//TODO: ver como hacemos para que conecte contra un mongodb, lo ponemos bundleado?
		if ((args.length== 2) && (args[0].trim().equals("updateFromTwitter")) && (args[1]!=null)){
			
			fh.updateUserFollowersFromTwitter(args[1].trim()); // Obtains a refresh over the users data.
			
		} else if ((args.length== 2) && (args[0].trim().equals("updateFollowersInfo")) && (args[1]!=null)){
		
			fh.updateUserFollowerInformationInDatabase(args[1]); //<--//TODO: aun resta probar que funcione el update en la base de datos; lo dejamos para el final porque lo importante es tener los IDs y no la info de los usuarios en si mismos.
			
		} else if ((args.length>= 3) && (args[0].trim().equals("followUsersOf")) && (args[1]!=null) && (args[2]!=null)){
			
			if (args[3].equals("updateFollowersFirst")) {
				fh.updateUsersThatAlreadyFollowsBack(args[1].trim()); //updates the followers of the main user
			}
		
			Long amountOfIterations = Long.parseLong(args[2]);
			
			//TODO: averiguar de que es ese error de "not enough parameters passed to query y arreglarlo".
			fh.automaticFollowingOfMainUser(args[1], amountOfIterations); // follow the users that follows the parametrized usuarioCopiar, follows the amount of users passed as parameter.
			
		} else if ((args.length>= 3) && (args[0].trim().equals("unfollowNonFollowersOf")) && (args[1]!=null) && (args[2]!=null)){
			
			if (args[3].equals("updateFollowersFirst")) {
				fh.updateUsersThatAlreadyFollowsBack(args[1].trim()); //updates the followers of the main user
			}
			
			Long amountOfIterations = Long.parseLong(args[2]);
			
			//Systematically unfollow the users that were followed but that didn't followed back; WATCH IT: this has to be done at least with a day of delay from the following for being effective.
			fh.systematicallyUnfollowNonFollowersThatWereFollowed(args[1], amountOfIterations);
			
		} else {
			System.out.println("...command NOT RECOGNIZED.");	
		}
		
		System.out.println("...process finished.");
		
		System.exit(0);
	
		//following from the commandline
		//unfollowing from the commandline
		
		//TODO: Armar interfaz web
		//TODO: transformar en un web service
		//TODO: Generar los codigos de unfollow inteligente (con ratios y evaliacion de seguidores seguidos por cada uno)
		//TODO: generar seguimiento distribuido por las proximas 24hs
		//TODO: generar segumiento distribuido por las proximas 24hs.
		
		

	}
	
	

}
