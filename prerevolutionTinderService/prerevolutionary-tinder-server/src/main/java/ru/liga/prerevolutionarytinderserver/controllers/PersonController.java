package ru.liga.prerevolutionarytinderserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.entity.Person;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity getPerson(@PathVariable("id") Long id) {
        log.info("Was calling getPerson. Input id: " + id);
        return personService.getPersonById(Long.valueOf(id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity getPersonImage(@PathVariable("id") Long id) {
        log.info("Was calling getPersonImage. Input id: " + id);
        return personService.getImagePersonById(Long.valueOf(id));
    }

    @PostMapping()
    public ResponseEntity createPerson(@RequestBody Person person) {
        log.info("Was calling createPerson. Input person: " + person);
        return personService.addNewPerson(person);
    }

}
