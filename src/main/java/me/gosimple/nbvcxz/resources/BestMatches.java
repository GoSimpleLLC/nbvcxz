package me.gosimple.nbvcxz.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.gosimple.nbvcxz.matching.match.Match;

/**
 * Wrapper for the best matches list and length to support recursive methods
 * 
 * @author Robin Hermans
 */
public class BestMatches {
  
  private int best_match_length;
  private List<Match> best_matches;

  public BestMatches() {
    this.best_match_length = 0;
    this.best_matches = new ArrayList<>();
  }

  public void setMatchLength(int best_match_length) {
    this.best_match_length = best_match_length;
  }

  public int getMatchLength() {
    return this.best_match_length;
  }

  public void setBestMatches(List<Match> best_matches) {
    this.best_matches = best_matches;
  }

  public List<Match> getBestMatches() {
    return this.best_matches;
  }

  public void sortMatches(Comparator comparator) {
    Collections.sort(this.best_matches, comparator);
  }
}