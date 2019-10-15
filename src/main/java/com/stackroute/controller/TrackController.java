package com.stackroute.controller;

import com.stackroute.domain.Track;
import com.stackroute.exceptions.TrackAlreadyExistsException;
import com.stackroute.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="api/v1")
public class TrackController {
    TrackService trackService;
    @Autowired
    public TrackController(TrackService trackService){
        this.trackService = trackService;
    }
    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track){
    ResponseEntity responseEntity;
    try{
        trackService.saveTrack(track);
        responseEntity = new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);

    }catch (TrackAlreadyExistsException ex){
        responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    return responseEntity;
    }

    @GetMapping("track")
    public ResponseEntity<?> getAllTracks(){
        ResponseEntity responseEntity;
        try{
            responseEntity = new ResponseEntity(trackService.getAllTracks(), HttpStatus.CREATED);
        }catch (Exception ex) {
            responseEntity = new ResponseEntity(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("track/{id}")
    public ResponseEntity<?> getTrackById(int id){
        ResponseEntity responseEntity;
        try{
            responseEntity = new ResponseEntity(trackService.getTrackById(id).toString(), HttpStatus.FOUND);
        }catch (Exception ex) {
            responseEntity = new ResponseEntity(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @DeleteMapping("track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try{
            trackService.deleteTrack(id);
            responseEntity = new ResponseEntity(trackService.getAllTracks(), HttpStatus.CREATED);
        }catch (Exception ex) {
            responseEntity = new ResponseEntity(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;

    }
    @PutMapping("track/{id}")
    public  ResponseEntity<?> updateComments(@PathVariable(value = "id") int id,@Valid @RequestBody Track track){
        ResponseEntity responseEntity;
        Optional<Track> track1 = trackService.getTrackById(id);
        try{
            if(!track1.isPresent()){
                throw new Exception("id-"+id);
            }
            track.setTrackId(id);
            trackService.saveTrack(track);
            responseEntity = new ResponseEntity(trackService.getAllTracks(), HttpStatus.CREATED);
        }catch (Exception ex) {
            responseEntity = new ResponseEntity(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }






}
