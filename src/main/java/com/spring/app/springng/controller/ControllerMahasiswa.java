package com.spring.app.springng.controller;

import com.spring.app.springng.entity.Mahasiswa;
import com.spring.app.springng.exception.NotFoundException;
import com.spring.app.springng.repository.JurusanRepository;
import com.spring.app.springng.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/mahasiswa/")
public class ControllerMahasiswa {

    @Autowired private JurusanRepository jurusanRepository;

    @Autowired private MahasiswaRepository mahasiswaRepository;

    @GetMapping
    public ResponseEntity<List<Mahasiswa>> list(){
        return Optional.ofNullable(mahasiswaRepository.findAll())
                .map(callbackJSON -> new ResponseEntity<>(callbackJSON, HttpStatus.OK))
                .orElse(new ResponseEntity<List<Mahasiswa>>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{idjurusan}/create")
    public Mahasiswa create(@PathVariable Long idjurusan,
                            @RequestBody Mahasiswa mahasiswa){
        return jurusanRepository.findById(idjurusan)
                .map(currentJurusan -> {
                    mahasiswa.setJurusan(currentJurusan);
                    return mahasiswaRepository.save(mahasiswa);
                }).orElseThrow(() -> new NotFoundException("kode jurusan "+idjurusan+" tidak ada"));
    }

    @PutMapping(value = "/{idjurusan}/update/{idmahasiswa}")
    public Mahasiswa update(@PathVariable Long idjurusan,
                            @PathVariable Long idmahasiswa,
                            @RequestBody Mahasiswa mahasiswa){

        return jurusanRepository.findById(idjurusan)
                .map(currentJurusan -> {
                    return mahasiswaRepository.findById(idmahasiswa)
                            .map(currentMahasiswa -> {
                                currentMahasiswa.setName(mahasiswa.getName());
                                currentMahasiswa.setJurusan(currentJurusan);
                                return mahasiswaRepository.save(currentMahasiswa);
                            }).orElseThrow(()-> new NotFoundException("kode mahasiswa "+idmahasiswa+"tidak ada"));
                }).orElseThrow(() -> new NotFoundException("kode jurusan "+idjurusan+" tidak ada"));
    }

    @DeleteMapping(value = "/{idjurusan}")
    public ResponseEntity<Void> delete(@PathVariable Long idjurusan){
        jurusanRepository.deleteById(idjurusan);
        return ResponseEntity.ok().build();
    }
}
