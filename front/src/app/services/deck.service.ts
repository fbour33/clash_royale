import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { DeckSummary } from '../models/deck-summary';
import rawData from '../../assets/example.json';
import { Deck } from '../../assets/deck-manager';


type DeckDetails = {
  deckId: string;
  totalWins: number;
  totalUses: number;
  uniquePlayers: number;
  highestClanLevel: number;
  avgDeckStrength: number;
};

type DeckData = {
  [key: string]: DeckDetails[];
};

const deckSummariesData: DeckData[] = rawData as unknown as DeckData[];

@Injectable({
  providedIn: 'root',
})
export class DeckService {

  constructor() {}

    public getDeckSummariesByKey(key: string): Observable<DeckSummary[]> {
       const dataItem = deckSummariesData.find(item => item[key] !== undefined);
       if (!dataItem || !dataItem[key]) {
         return of([]);
       }
       const processedData = this.processData(dataItem[key]);
       return of(processedData);
     }

     private processData(data: DeckDetails[]): DeckSummary[] {
       return data.map((item: DeckDetails) => ({
         deck: item.deckId,
         victories: item.totalWins || 0,
         uses: item.totalUses || 0,
         uniquePlayers: item.uniquePlayers || 0,
         highestClanLevel: item.highestClanLevel || 0,
         averageDiffForce: item.avgDeckStrength || 0,
         cards: new Deck(item.deckId).cards(),
       }));
     }
}
