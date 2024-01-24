import { Injectable } from '@angular/core';
import jsonData from '../../assets/ngrams.json';

type NgramDetail = {
  _1: string; // Semaine
  _2: number; // TotalWins
};

type NgramDataItem = {
  _1: string; // DeckId
  _2: NgramDetail[]; // Détails
};



@Injectable({
  providedIn: 'root'
})
export class NgramService {
  private ngramsData: NgramDataItem[] = jsonData as unknown as NgramDataItem[];

  constructor() { }


  getChartData(deckId : string): { labels: string[], data: number[] } {
    const deck = this.ngramsData.find(item => item._1 === deckId);
          if (deck) {
            const labels = deck._2.map(item => item._1);
            const data = deck._2.map(item => item._2);
            return { labels, data };
          } else {
            return { labels: [], data: [] };
          }
    }

}
