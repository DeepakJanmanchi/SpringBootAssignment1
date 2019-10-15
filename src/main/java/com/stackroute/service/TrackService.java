package com.stackroute.service;

import com.stackroute.domain.Track;

import java.util.List;
import java.util.Optional;

public interface TrackService {
    public Track saveTrack(Track track);
    public List<Track> getAllTracks();
    public Track updateComments(Track track);
    public boolean deleteTrack(int trackId);
    public Optional<Track> getTrackById(int id);

}
