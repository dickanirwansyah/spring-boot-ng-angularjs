package com.spring.app.springng.controller;

import com.spring.app.springng.entity.Jurusan;
import com.spring.app.springng.exception.NotFoundException;
import com.spring.app.springng.repository.JurusanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/jurusan/")
public class ControllerJurusan {

    @Autowired
    private JurusanRepository jurusanRepository;

    @GetMapping
    public ResponseEntity<List<Jurusan>> list(){
        return Optional.ofNullable(jurusanRepository.findAll())
                .map(callbackJSON -> new ResponseEntity<>(callbackJSON, HttpStatus.OK))
                .orElse(new ResponseEntity<List<Jurusan>>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Jurusan create(@RequestBody Jurusan jurusan){
        return jurusanRepository.save(jurusan);
    }

    @GetMapping(value = "/{idjurusan}")
    public Optional<Jurusan> getId(@PathVariable Long idjurusan){
        return jurusanRepository.findById(idjurusan);
    }

    @PutMapping(value = "/{idjurusan}")
    public Jurusan update(@PathVariable Long idjurusan,
                          @RequestBody Jurusan jurusan){

        return jurusanRepository.findById(idjurusan)
                .map(currentJurusan -> {
                    currentJurusan.setName(jurusan.getName());
                    currentJurusan.setActive(jurusan.isActive());
                    return jurusanRepository.save(currentJurusan);
                }).orElseThrow(() -> new NotFoundException("kode jurusan "+idjurusan+" tidak ada"));
    }

    @PutMapping(value = "/active/{idjurusan}")
    public Jurusan active(@PathVariable Long idjurusan){
        return jurusanRepository.findById(idjurusan)
                .map(currentJurusan -> {
                    currentJurusan.setActive(true);
                    return jurusanRepository.save(currentJurusan);
                }).orElseThrow(() -> new NotFoundException("kode jurusan "+idjurusan+" tidak ada"));
    }

    @PutMapping(value = "/deactive/{idjurusan}")
    public Jurusan deactive(@PathVariable Long idjurusan){
        return jurusanRepository.findById(idjurusan)
                .map(currentJurusan -> {
                    currentJurusan.setActive(false);
                    return jurusanRepository.save(currentJurusan);
                }).orElseThrow(() -> new NotFoundException("kode jurusan "+idjurusan+" tidak ada"));
    }
}
