import { Component, OnInit } from '@angular/core';
import { DeckSummary } from '../../models/deck-summary';
import { DeckService } from '../../services/deck.service';

@Component({
  selector: 'app-deck-statistics',
  templateUrl: './deck-statistics.component.html',
  styleUrls: ['./deck-statistics.component.css']
})
export class DeckStatisticsComponent implements OnInit {
  private key: string = '';
  public deckSummaries: DeckSummary[] = [];
  public selectedSortOption = 'winrate';
  public selectedTimeFrame = 'year';
  public selectedWeek: string = "";
  public selectedMonth: string = "";
  public selectedYear = '2023';
  weeks = Array.from({length: 52}, (_, i) => i + 1);

  constructor(private deckService: DeckService) {}

 ngOnInit() {
    this.updateKeyAndFetchDecks();
  }

  onTimeFrameChange(): void {
    this.selectedWeek = this.selectedTimeFrame === 'week' ? this.selectedWeek : "";
    this.selectedMonth = this.selectedTimeFrame === 'month' ? this.selectedMonth : "";
    this.selectedYear = this.selectedTimeFrame === 'year' ? this.selectedYear : '2023';
    this.updateKeyAndFetchDecks();
  }

    onSortOptionChange(): void {
      this.updateKeyAndFetchDecks();
    }

  updateKeyAndFetchDecks() {
    if (this.selectedTimeFrame === 'week' && this.selectedWeek) {
      this.key = `${this.selectedWeek}_${this.selectedYear}`;
    } else if (this.selectedTimeFrame === 'month' && this.selectedMonth) {
      this.key = `${this.selectedMonth.toUpperCase()}_${this.selectedYear}`;
    } else if (this.selectedTimeFrame === 'year') {
      this.key = `${this.selectedYear}`;
    } else {
      console.error('Selection is incomplete or incorrect');
      return;
    }
  this.getDeckSummaries(this.key, this.selectedSortOption);  }

  private getDeckSummaries(key: string, sortOption: string): void {
    this.deckService.getDeckSummariesByKeyAndSort(key, sortOption).subscribe(
      (data) => {
        this.deckSummaries = data.reverse();
        console.log('Deck Summaries:', this.deckSummaries);
      },
      (error) => {
        console.error('Error fetching deck summaries:', error);
      }
    );
  }
}
