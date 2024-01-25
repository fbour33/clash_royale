import { Injectable } from '@angular/core';
import jsonData1 from '../../assets/ngrams1.json';
import jsonData2 from '../../assets/ngrams2.json';
import jsonData3 from '../../assets/ngrams3.json';


type NgramDetail = {
  _1: string; // Semaine
  _2: number; // WinRate
};

type NgramDataItem = {
  _1: string; // DeckId
  _2: NgramDetail[]; // Détails
};



@Injectable({
  providedIn: 'root'
})
export class NgramService {
  private ngramsData1: NgramDataItem[] = jsonData1 as unknown as NgramDataItem[];
  private ngramsData2: NgramDataItem[] = jsonData2 as unknown as NgramDataItem[];
  private ngramsData3: NgramDataItem[] = jsonData3 as unknown as NgramDataItem[];

  constructor() { }

  getChartData(deckId : string): { labels: string[], data: number[] } {

    const searchInData = (data: NgramDataItem[], deckId: string) => {
      const deck = data.find(item => item._1 === deckId);
      if (deck) {
        const labels = deck._2.map(item => item._1);
        const data = deck._2.map(item => item._2);
        return { labels, data, found: true };
      }
      return { labels: [], data: [], found: false };
    };

    let result = searchInData(this.ngramsData1, deckId);
    if (result.found) return result;

    result = searchInData(this.ngramsData2, deckId);
    if (result.found) return result;

    return searchInData(this.ngramsData3, deckId);
  }
}