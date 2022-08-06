package ru.liga.prerevolutionarytinderserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPerson(@PathVariable("id") Long id) {
        log.info("Was calling getPerson. Input id: " + id);
        return personService.getPersonById(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getPersonImage(@PathVariable("id") Long id) {
        log.info("Was calling getPersonImage. Input id: " + id);
        return personService.getImagePersonById(id);
    }

    @PostMapping()
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        log.info("Was calling createPerson. Input person: " + person);
        return personService.addNewPerson(person);
    }

    @GetMapping("/favorites/{id}/pages/{page}")
    public ResponseEntity<PersonDto> getPersonsLikedByMe(@PathVariable("id") Long id, @PathVariable("page") int page) {
        log.info("Was calling getPersons.");

        PageRequest pageRequest = PageRequest.of(page, 1);

        return ResponseEntity.ok(personService.getPersonsLikedByMe(id, pageRequest));
    }

    @GetMapping("/search/{id}/pages/{page}")
    public ResponseEntity<PersonDto> getCandidateFavorites(@PathVariable("id") Long id, @PathVariable("page") int page) {
        log.info("Was calling getPersons.");

        PageRequest pageRequest = PageRequest.of(page, 1);

        return ResponseEntity.ok(personService.getCandidateFavorites(id, pageRequest));
    }
}
