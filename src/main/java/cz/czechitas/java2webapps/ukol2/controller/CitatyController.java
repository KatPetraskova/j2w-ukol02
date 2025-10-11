package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class CitatyController {
    private final Random random = new Random();

    @GetMapping("/")
    public ModelAndView citat() {
        ModelAndView modelAndView = new ModelAndView("citat");

        List<String> citaty = readAllLines("citaty.txt");
        String nahodnyCitat = citaty.get(random.nextInt(citaty.size()));

        /*
        List<String> seznamTextu=List.of(
                "Debugging /de·bugh·ing/ (verb): The Classic Mystery Game where you are the detective, the victim, and the murderer.",
                "A user interface is like a joke. If you have to explain it, it's not that good.",
                "To replace programmers with robots, clients will have to accurately describe what they want. We're safe.",
                "I have a joke on programming but it only works on my computer.",
                "99 little bugs in the code, 99 bugs in the code. Take one down, patch it around. 127 little bugs in the code…",
                "When I wrote this code, only God & I understood what it did. Now… Only God knows.",
                "Programmer (noun.): A machine that turns coffee into code.",
                "Real programmers count from 0.");
        int index = random.nextInt(seznamTextu.size());
        String nahodnyCitat = seznamTextu.get(index);
        */

        int nahodnyObrazek = obrazek();

        modelAndView.addObject("citat", nahodnyCitat);
        modelAndView.addObject("obrazek", nahodnyObrazek);

        return modelAndView;

    }

    private int obrazek() {
        return random.nextInt(6) + 1;
    }

    private static List<String> readAllLines(String resource) {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(
                InputStream inputStream = classLoader.getResourceAsStream(resource);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader)
        ){
            return reader
                    .lines()                        // Metoda lines() vrací stream řádků ze souboru.
                    .collect(Collectors.toList());  // Pomocí kolektoru převedeme Stream<String> na List<String>.
        } catch (IOException e) {
            throw new RuntimeException("Nepodařilo se načíst soubor " + resource, e);
        }
    }


}
