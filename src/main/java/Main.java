import cz.cvut.fit.tjv.art_commissions.app.business.ArtistService;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.business.CustomerService;
import cz.cvut.fit.tjv.art_commissions.app.domain.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var customer1 = new Customer(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(), "Bob");
        var customer2 = new Customer(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + 1, "Alice");
        var artist1 = new Artist(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + 2,
                "John", 200, ArtType.SKETCHING, List.of());
        var artist2 = new Artist(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + 3,
                "Jane", 200, ArtType.DIGITAL_ART, List.of());
        var commission1 = new Commission(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + 4,
                ArtType.DIGITAL_ART, "Pixel art panda", Difficulty.EASY, LocalDate.now(), customer1, List.of(artist2));
        var commission2 = new Commission(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + 5,
                ArtType.SKETCHING, "Sketch of a panda", Difficulty.MEDIUM, LocalDate.now(), customer2, List.of(artist1));


        ApplicationContext ctx = new AnnotationConfigApplicationContext("cz.cvut.fit.tjv.art_commissions.app");
        var cusSvc = ctx.getBean(CustomerService.class);
        var artSvc = ctx.getBean(ArtistService.class);
        var comSvc = ctx.getBean(CommissionService.class);


        System.out.println("\u001B[1;33m" + "Insert data" + "\u001B[0m");
        System.out.println("Inserting customer " + customer1.getId());
        cusSvc.create(customer1);
        System.out.println("Inserting customer " + customer2.getId());
        cusSvc.create(customer2);
        System.out.println("Inserting artist " + artist1.getId());
        artSvc.create(artist1);
        System.out.println("Inserting artist " + artist2.getId());
        artSvc.create(artist2);
        System.out.println("Inserting commission " + commission1.getId());
        comSvc.create(commission1);
        System.out.println("Inserting commission " + commission2.getId());
        comSvc.create(commission2);

        System.out.println("\n\u001B[1;33m" + "Data after insertion:" + "\u001B[0m");
        System.out.println("Customers: " + cusSvc.readAll());
        System.out.println("Artists: " + artSvc.readAll());
        System.out.println("Commissions: " + comSvc.readAll());

        System.out.println("\n\u001B[1;33m" + "Rename customer " + customer1.getId() + "\u001B[0m");
        System.out.println("Database before: " + cusSvc.readAll());
        customer1.setName("Jaroslava");
        cusSvc.update(customer1); // Not necessary, just for the ✨effect✨
        System.out.println("Database after:  " + cusSvc.readAll());

        Artist artist3 = new Artist(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() + 6, "Harry", 300, ArtType.DIGITAL_ART, List.of());
        artist2.setCoworkers(List.of(artist3));
        System.out.println("\n\u001B[1;33m" + "Add the commissioner " + artist3.getId() + "\u001B[0m");
        System.out.println("Database before: " + comSvc.readAll());
        artSvc.create(artist3);
        commission1.addCommissioner(artist3);
        System.out.println("Database after:  " + comSvc.readAll());

        System.out.println("\n\u001B[1;33m" + "Delete the commission " + commission2.getId() + "\u001B[0m");
        System.out.println("Database before: " + comSvc.readAll());
        comSvc.deleteById(commission2.getId());
        comSvc.deleteById(commission2.getId());
        System.out.println("Database after:  " + comSvc.readAll());

        System.out.println("\n\u001B[1;33m" + "Find the deleted commission " + commission2.getId() + "\u001B[0m");
        if (comSvc.readById(commission2.getId()).isEmpty())
            System.out.println("Commission was not found");
        else
            throw new RuntimeException("The commission was unexpectedly found");

        System.out.println("\n\n\u001B[1;32m" + "------- EXITED SUCCESSFULLY -------" + "\u001B[0m");
    }
}