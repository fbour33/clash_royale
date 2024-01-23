import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DeckSummary } from '../../models/deck-summary';
import { DeckService } from '../../services/deck.service';

@Component({
  selector: 'app-deck-statistics',
  templateUrl: './deck-statistics.component.html',
  styleUrls: ['./deck-statistics.component.css']
})
export class DeckStatisticsComponent implements OnInit {
  private apiUrl = '../../assets/data.json';
  private key: string = '';
  public deckSummaries: DeckSummary[] = [];
  public selectedTimeFrame = '';
  public selectedWeek: string ="";
  public selectedMonth: string ="";
  public selectedYear = '2023';
  weeks = Array.from({length: 52}, (_, i) => i + 1);

  constructor(private deckService: DeckService) {}

  ngOnInit() {  }

  onTimeFrameChange(): void {
    if (this.selectedTimeFrame !== 'week') {
      this.selectedWeek = "";
    }
    if (this.selectedTimeFrame !== 'month') {
      this.selectedMonth = "";
    }
  }

  submitSelection() {
    if (this.selectedTimeFrame === 'week' && this.selectedWeek) {
      this.key = `${this.selectedWeek}_${this.selectedYear}`;
    } else if (this.selectedTimeFrame === 'month' && this.selectedMonth) {
      this.key = `${this.selectedMonth.toUpperCase()}_${this.selectedYear}`;
    }
    this.getDeckSummaries(this.key);
  }

    private getDeckSummaries(key: string): void {
     this.deckService.getDeckSummariesByKey(key).subscribe(
           (data) => {
             this.deckSummaries = data;
             console.log('Deck Summaries:', this.deckSummaries);
           },
           (error) => {
             console.error('Error fetching deck summaries:', error);
           }
         );
    }
}
