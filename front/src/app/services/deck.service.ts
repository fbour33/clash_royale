import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { DeckSummary } from '../models/deck-summary';
import winrateData from '../../assets/winRate.json';
import highestClanLevelData from '../../assets/highestClan.json';
import { Deck } from '../../assets/deck-manager';


type DeckDetails = {
  deckId: string;
  totalWins: number;
  totalUses: number;
  uniquePlayers: number;
  highestClanLevel: number;
  avgDeckStrength: number;
  winRate: number;
};

type DeckData = {
  [key: string]: DeckDetails[];
};

@Injectable({
  providedIn: 'root',
})
export class DeckService {

  constructor() {}

    public getDeckSummariesByKeyAndSort(key: string, sortOption: string): Observable<DeckSummary[]> {
       const dataset = sortOption === 'winrate' ? winrateData : highestClanLevelData;
       const deckSummariesData: DeckData[] = dataset as unknown as DeckData[];
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
         winRate: item.winRate || 0,
         cards: new Deck(item.deckId).cards(),
       }));
     }
}
