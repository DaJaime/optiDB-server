package optidb.server.controller;

import optidb.server.model.Platform;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlatformRestController {

    @RequestMapping("/platform")
    public Platform platformVersion(@RequestParam(value="name", defaultValue="Inconu") String name) {
        name = name.toLowerCase();

        switch (name) {
            case "hadoop":
                System.out.println("Hadoop");
                return new Platform(name);

            default:
                return new Platform(name);
        }
    }
}
