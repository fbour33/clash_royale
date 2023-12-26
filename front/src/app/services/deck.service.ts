import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { DeckSummary } from '../models/deck-summary';
import deckSummariesData from '../../assets/summary_small.json';
import { Deck } from '../../assets/deck-manager';

@Injectable({
  providedIn: 'root',
})
export class DeckService {
  public deckSummaries: DeckSummary[] = this.processData(deckSummariesData);

  constructor() {}

  /**
   * Méthode permettant de récupérer et traiter les données du fichier JSON
   */
  public getDeckSummaries(): Observable<DeckSummary[]> {
    return of(this.deckSummaries);
  }

  /**
   * Méthode permettant de traiter les données du fichier JSON
   * @param data
   * @private
   */
  private processData(data: any[]): DeckSummary[] {
    return data.map((item: any) => ({
      deck: item.deck,
      victories: item.victories || 0,
      uses: item.uses || 0,
      uniquePlayers: item.uniquePlayers || 0,
      highestClanLevel: item.highestClanLevel || 0,
      sumDiffForce: item.sumDiffForce || 0,
      averageDiffForce: item.averageDiffForce,
      cards: new Deck(item.deck).cards(),
    }));
  }
}
